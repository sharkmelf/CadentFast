package com.cadent.cadentfast.ui.timer

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.cadent.cadentfast.CadentFastApp
import com.cadent.cadentfast.catalog.Catalog
import com.cadent.cadentfast.catalog.DietaryTag
import com.cadent.cadentfast.catalog.Dish
import com.cadent.cadentfast.timer.Cadences
import com.cadent.cadentfast.timer.Phase
import com.cadent.cadentfast.timer.Rhythm
import java.util.Calendar
import java.util.TimeZone
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

/**
 * Hero timer state. The rhythm is the source of truth; "Welcome" is the only
 * state without one. The lock flow (picker) is a UI sub-state held in-memory.
 *
 * - [Welcome] — no rhythm yet. The user has just opened the app.
 * - [ChoosingDish] — the menu picker is open. The reason ([context]) controls
 *   what happens when the user taps a dish.
 * - [FastRunning] / [FeastRunning] — the rhythm is active; the screen renders
 *   the ring + dish + sub-line.
 * - [BreakFastReveal] — a phase boundary just crossed into feast. The reveal
 *   screen shows full-grade dish + "Your table." until the user acknowledges.
 */
sealed interface HeroTimerState {
    data object Welcome : HeroTimerState
    data class ChoosingDish(
        val context: ChooseContext,
        val initialDietaryFilters: Set<DietaryTag>,
    ) : HeroTimerState
    data class FastRunning(
        val rhythm: Rhythm,
        val dish: Dish,
        val nowMs: Long,
    ) : HeroTimerState
    data class FeastRunning(
        val rhythm: Rhythm,
        val dish: Dish,
        val nowMs: Long,
    ) : HeroTimerState
    data class BreakFastReveal(
        val rhythm: Rhythm,
        val dish: Dish,
        val nowMs: Long,
    ) : HeroTimerState
}

sealed interface ChooseContext {
    /** Welcome → picker → begin rhythm with the chosen dish. */
    data object BeginRhythm : ChooseContext
    /** Mid-fast swap. Choosing returns to the running screen with the new dish locked. */
    data object SwapMidRhythm : ChooseContext
}

class HeroTimerViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = (app as CadentFastApp).rhythmRepo

    private val tickFlow = flow {
        while (true) {
            emit(System.currentTimeMillis())
            delay(1_000L)
        }
    }

    /**
     * Lock-flow sub-state. Lives only in memory. Process kill mid-picker sends
     * the user back to Welcome (if no rhythm) or to the running screen (if a
     * rhythm exists); the in-progress picker is forgotten, which is fine.
     */
    private val chooseContext = MutableStateFlow<ChooseContext?>(null)

    init {
        // Catch any boundary that crossed while the process was dead.
        repo.reconcile()
    }

    val state: StateFlow<HeroTimerState> =
        combine(repo.rhythm, tickFlow, repo.boundaryCrossed, chooseContext) {
                rhythm, now, boundary, context ->
            if (rhythm == null || !rhythm.isActive(now)) {
                // No rhythm yet. Either Welcome, or BeginRhythm picker open.
                return@combine when (context) {
                    ChooseContext.BeginRhythm -> HeroTimerState.ChoosingDish(
                        context = context,
                        initialDietaryFilters = smartDefaultFiltersForBegin(),
                    )
                    else -> HeroTimerState.Welcome
                }
            }
            // Active rhythm. If the user opened the picker for a swap, show it.
            if (context == ChooseContext.SwapMidRhythm) {
                return@combine HeroTimerState.ChoosingDish(
                    context = context,
                    initialDietaryFilters = smartDefaultFiltersForSwap(rhythm, now),
                )
            }
            val dish = rhythm.lockedDishId?.let { Catalog.byId(it) } ?: Catalog.default
            if (boundary != null && boundary.enteringPhase == Phase.Feast && rhythm.phase(now) == Phase.Feast) {
                return@combine HeroTimerState.BreakFastReveal(rhythm, dish, now)
            }
            when (rhythm.phase(now)) {
                Phase.Fast -> HeroTimerState.FastRunning(rhythm, dish, now)
                Phase.Feast -> HeroTimerState.FeastRunning(rhythm, dish, now)
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), HeroTimerState.Welcome)

    /** Welcome → open the dish picker to begin the rhythm. */
    fun onShowMeToMyTable() {
        chooseContext.value = ChooseContext.BeginRhythm
    }

    /** Running screen → open the dish picker to swap the locked dish. */
    fun onReconsider() {
        chooseContext.value = ChooseContext.SwapMidRhythm
    }

    /** Picker → user picked a dish. Either begins the rhythm or swaps. */
    fun onDishChosen(dish: Dish) {
        when (chooseContext.value) {
            ChooseContext.BeginRhythm -> {
                repo.beginRhythm(
                    cadence = Cadences.default,
                    dishId = dish.id,
                    startEpochMs = System.currentTimeMillis(),
                )
            }
            ChooseContext.SwapMidRhythm -> {
                repo.swapLockedDish(dish.id)
            }
            null -> { /* picker closed; ignore */ }
        }
        chooseContext.value = null
    }

    /** Picker → system back. Returns to Welcome or to the running screen. */
    fun onBackFromPicker() {
        chooseContext.value = null
    }

    /** Dismiss the break-fast reveal; the screen slides into feast register. */
    fun acknowledgeBreakFastReveal() {
        repo.acknowledgeBoundaryCrossed()
    }

    /** End the rhythm. Returns to Welcome. */
    fun endRhythm() {
        repo.endRhythm()
        repo.acknowledgeBoundaryCrossed()
        chooseContext.value = null
    }

    /**
     * Smart-default filter pre-activation for the begin flow. We pick filters
     * based on the *hour the upcoming break-fast falls at* (default cadence's
     * fast length from now). Mornings get Vegetarian + Mediterranean
     * pre-activated for the lighter break-fast register; afternoons and
     * evenings get nothing pre-activated so the user sees the full catalog.
     *
     * The behavior is invisible to the user — they just see the right dishes
     * for their context and can clear with one tap.
     */
    private fun smartDefaultFiltersForBegin(): Set<DietaryTag> {
        val breakFastEpochMs = System.currentTimeMillis() + Cadences.default.fastMs
        return smartDefaultFiltersForBreakFastAt(breakFastEpochMs)
    }

    private fun smartDefaultFiltersForSwap(rhythm: Rhythm, nowMs: Long): Set<DietaryTag> {
        // If currently in fast, the upcoming break-fast is the next boundary.
        // If currently in feast, the upcoming break-fast is two boundaries away.
        val breakFastEpochMs = when (rhythm.phase(nowMs)) {
            Phase.Fast -> rhythm.nextBoundaryEpochMs(nowMs)
            Phase.Feast -> rhythm.nextBoundaryEpochMs(nowMs) + rhythm.cadence.fastMs
        }
        return smartDefaultFiltersForBreakFastAt(breakFastEpochMs)
    }

    private fun smartDefaultFiltersForBreakFastAt(epochMs: Long): Set<DietaryTag> {
        val cal = Calendar.getInstance(TimeZone.getDefault())
        cal.timeInMillis = epochMs
        val hour = cal.get(Calendar.HOUR_OF_DAY)
        return when (hour) {
            in 5..10 -> setOf(DietaryTag.Vegetarian, DietaryTag.Mediterranean)
            else -> emptySet()
        }
    }
}
