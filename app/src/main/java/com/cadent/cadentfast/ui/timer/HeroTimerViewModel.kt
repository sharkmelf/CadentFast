package com.cadent.cadentfast.ui.timer

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.cadent.cadentfast.CadentFastApp
import com.cadent.cadentfast.catalog.Catalog
import com.cadent.cadentfast.catalog.Dish
import com.cadent.cadentfast.timer.Cadences
import com.cadent.cadentfast.timer.Phase
import com.cadent.cadentfast.timer.Rhythm
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

/**
 * Hero timer state — the view of the world the rhythm screen renders against.
 *
 * The rhythm is the source of truth (from [com.cadent.cadentfast.timer.RhythmRepository]).
 * "Welcome" is the only state where no rhythm exists. Once the rhythm begins,
 * the state machine derives Fast / Feast / BreakFastReveal from the rhythm's
 * computed phase plus the latest boundary event.
 *
 * In PR A' we hardcode [Catalog.default] as the locked dish. PR B' replaces
 * that with the dish-selection screen.
 */
sealed interface HeroTimerState {
    data object Welcome : HeroTimerState
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
    /**
     * A short-lived state immediately after a phase boundary, while the UI
     * presents the cinematic reveal (PR C' adds the animation; PR A' just shows
     * the static reveal). Acknowledging dismisses back to FeastRunning.
     */
    data class BreakFastReveal(
        val rhythm: Rhythm,
        val dish: Dish,
        val nowMs: Long,
    ) : HeroTimerState
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
     * Reconcile on every fresh subscription so a foreground return catches any
     * missed boundary.
     */
    init {
        repo.reconcile()
    }

    val state: StateFlow<HeroTimerState> =
        combine(repo.rhythm, tickFlow, repo.boundaryCrossed) { rhythm, now, boundary ->
            if (rhythm == null || !rhythm.isActive(now)) return@combine HeroTimerState.Welcome
            val dish = rhythm.lockedDishId?.let { Catalog.byId(it) } ?: Catalog.default
            // If the most recent boundary event is still un-acknowledged AND
            // we're now in feast, present the break-fast reveal.
            if (boundary != null && boundary.enteringPhase == Phase.Feast && rhythm.phase(now) == Phase.Feast) {
                return@combine HeroTimerState.BreakFastReveal(rhythm, dish, now)
            }
            when (rhythm.phase(now)) {
                Phase.Fast -> HeroTimerState.FastRunning(rhythm, dish, now)
                Phase.Feast -> HeroTimerState.FeastRunning(rhythm, dish, now)
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), HeroTimerState.Welcome)

    /**
     * Begin the rhythm with the dev default cadence and a hardcoded dish for
     * PR A'. PR B' replaces this with a real dish-selection flow.
     */
    fun beginRhythmDevDefault() {
        repo.beginRhythm(
            cadence = Cadences.default,
            dishId = Catalog.default.id,
            startEpochMs = System.currentTimeMillis(),
        )
    }

    /** Dismiss the break-fast reveal and slide into the feast register. */
    fun acknowledgeBreakFastReveal() {
        repo.acknowledgeBoundaryCrossed()
    }

    /** End the rhythm. Returns to Welcome. */
    fun endRhythm() {
        repo.endRhythm()
        repo.acknowledgeBoundaryCrossed()
    }
}
