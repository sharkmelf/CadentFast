package com.cadent.cadentfast.ui.timer

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.cadent.cadentfast.CadentFastApp
import com.cadent.cadentfast.catalog.Catalog
import com.cadent.cadentfast.catalog.Dish
import com.cadent.cadentfast.timer.FastSession
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

/**
 * Hero timer state. A single fast-in-progress session, ticked once a second,
 * combined with the repository's session flow so persistence survives kill.
 */
sealed interface HeroTimerState {
    data object Idle : HeroTimerState
    data class Running(val dish: Dish, val session: FastSession, val nowMs: Long) : HeroTimerState
    data class Complete(val dish: Dish, val session: FastSession) : HeroTimerState
}

class HeroTimerViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = (app as CadentFastApp).timerRepo

    private val tickFlow = flow {
        while (true) {
            emit(System.currentTimeMillis())
            delay(1_000L)
        }
    }

    val state: StateFlow<HeroTimerState> = combine(repo.session, tickFlow) { session, now ->
        if (session == null) return@combine HeroTimerState.Idle
        val dish = Catalog.byId(session.dishId) ?: Catalog.default
        if (session.isComplete(now)) HeroTimerState.Complete(dish, session)
        else HeroTimerState.Running(dish, session, now)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), HeroTimerState.Idle)

    fun startDefault15Minutes() {
        repo.startFast(dishId = Catalog.default.id, durationMs = 15 * 60_000L)
    }

    fun startFast(dishId: String, durationMs: Long) {
        repo.startFast(dishId = dishId, durationMs = durationMs)
    }

    fun endFast() = repo.endFast()
}
