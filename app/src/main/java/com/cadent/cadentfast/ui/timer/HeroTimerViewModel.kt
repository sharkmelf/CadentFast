package com.cadent.cadentfast.ui.timer

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.cadent.cadentfast.CadentFastApp
import com.cadent.cadentfast.catalog.Catalog
import com.cadent.cadentfast.catalog.Dish
import com.cadent.cadentfast.timer.FastSession
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

/**
 * Hero timer state. A running fast (from the repo's persisted session) wins
 * over any in-progress lock flow. When no fast is active, the UI sub-state
 * decides whether the user is at the welcome screen, picking a dish, or
 * picking a duration.
 */
sealed interface HeroTimerState {
    data object Welcome : HeroTimerState
    data object ChoosingDish : HeroTimerState
    data class ChoosingDuration(val dish: Dish) : HeroTimerState
    data class Running(val dish: Dish, val session: FastSession, val nowMs: Long) : HeroTimerState
    data class Complete(val dish: Dish, val session: FastSession) : HeroTimerState
}

/**
 * Internal UI sub-state for the lock flow. Lives only in memory -- a process
 * kill mid-selection sends the user back to the welcome screen, which is fine;
 * nothing has been locked yet.
 */
private sealed interface LockStep {
    data object Welcome : LockStep
    data object ChoosingDish : LockStep
    data class ChoosingDuration(val dish: Dish) : LockStep
}

class HeroTimerViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = (app as CadentFastApp).timerRepo

    private val tickFlow = flow {
        while (true) {
            emit(System.currentTimeMillis())
            delay(1_000L)
        }
    }

    private val lockStep = MutableStateFlow<LockStep>(LockStep.Welcome)

    val state: StateFlow<HeroTimerState> =
        combine(repo.session, tickFlow, lockStep) { session, now, step ->
            if (session != null) {
                val dish = Catalog.byId(session.dishId) ?: Catalog.default
                return@combine if (session.isComplete(now)) HeroTimerState.Complete(dish, session)
                else HeroTimerState.Running(dish, session, now)
            }
            when (step) {
                LockStep.Welcome -> HeroTimerState.Welcome
                LockStep.ChoosingDish -> HeroTimerState.ChoosingDish
                is LockStep.ChoosingDuration -> HeroTimerState.ChoosingDuration(step.dish)
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), HeroTimerState.Welcome)

    fun onLockAReward() {
        lockStep.value = LockStep.ChoosingDish
    }

    fun onDishChosen(dish: Dish) {
        lockStep.value = LockStep.ChoosingDuration(dish)
    }

    fun onDurationChosen(durationMs: Long) {
        val dish = (lockStep.value as? LockStep.ChoosingDuration)?.dish ?: return
        repo.startFast(dishId = dish.id, durationMs = durationMs)
        lockStep.value = LockStep.Welcome
    }

    fun onBack() {
        lockStep.value = when (lockStep.value) {
            LockStep.Welcome -> LockStep.Welcome
            LockStep.ChoosingDish -> LockStep.Welcome
            is LockStep.ChoosingDuration -> LockStep.ChoosingDish
        }
    }

    fun endFast() {
        repo.endFast()
        lockStep.value = LockStep.Welcome
    }
}
