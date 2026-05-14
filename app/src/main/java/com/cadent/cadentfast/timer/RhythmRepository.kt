package com.cadent.cadentfast.timer

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.cadent.cadentfast.catalog.Catalog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * Single coordinator for the active rhythm. Owns persistence (via [RhythmStore]),
 * the always-on foreground-service keep-alive (via [RhythmService]), and the
 * exact phase-boundary alarm (via [AlarmManager] + [RhythmAlarmReceiver]).
 *
 * Held as a singleton on the Application so every screen and the service observe
 * the same [StateFlow].
 *
 * AlarmManager strategy: one alarm at a time, fired at the next phase boundary.
 * When the alarm fires, the receiver re-arms forward to the *next-next* boundary.
 * Missed-alarm recovery is centralized in [reconcile], called on app launch,
 * service tick, and the lifecycle / system-clock broadcasts.
 */
class RhythmRepository(
    private val context: Context,
    private val store: RhythmStore,
    private val scope: CoroutineScope,
) {
    val rhythm: StateFlow<Rhythm?> = store.rhythm
        .stateIn(scope, SharingStarted.Eagerly, initialValue = null)

    /**
     * Fires once each time a phase boundary is crossed. Consumers (ViewModel) use
     * it to trigger the break-fast reveal. Carries the phase that was just
     * *entered*; e.g. on fast→feast transition this emits Phase.Feast.
     */
    private val _boundaryCrossed = MutableStateFlow<BoundaryEvent?>(null)
    val boundaryCrossed: StateFlow<BoundaryEvent?> = _boundaryCrossed

    data class BoundaryEvent(
        val enteringPhase: Phase,
        val cycleIndex: Int,
        val dishId: String?,
        val atEpochMs: Long,
    )

    fun acknowledgeBoundaryCrossed() {
        _boundaryCrossed.value = null
    }

    /**
     * Begin a new rhythm. Replaces any existing rhythm.
     *
     * [startEpochMs] supports backdating ("I last ate at 7pm yesterday") so the
     * rhythm can meet the user mid-cycle instead of starting from zero.
     */
    fun beginRhythm(
        cadence: Cadence,
        dishId: String?,
        startEpochMs: Long = System.currentTimeMillis(),
    ) {
        scope.launch {
            val rhythm = Rhythm(
                startEpochMs = startEpochMs,
                cadence = cadence,
                lockedDishId = dishId,
                endedEpochMs = null,
            )
            store.save(rhythm)
            cancelBoundaryAlarm()
            scheduleBoundaryAlarm(rhythm)
            RhythmService.start(context)
        }
    }

    /** Swap the locked dish mid-rhythm. Timing is untouched. */
    fun swapLockedDish(dishId: String) {
        scope.launch { store.setLockedDishId(dishId) }
    }

    /** Change the cadence. Re-anchors so the user stays at the same phase position. */
    fun changeCadence(newCadence: Cadence) {
        scope.launch {
            val current = rhythm.value ?: return@launch
            val rebased = current.withCadence(newCadence)
            store.save(rebased)
            cancelBoundaryAlarm()
            scheduleBoundaryAlarm(rebased)
        }
    }

    /** End the rhythm explicitly. */
    fun endRhythm() {
        scope.launch {
            store.end()
            cancelBoundaryAlarm()
            RhythmService.stop(context)
        }
    }

    /**
     * Called from [RhythmAlarmReceiver] on every fire. Reads the latest rhythm
     * from DataStore (so any pending swap has already taken effect), emits the
     * boundary event, and re-arms forward.
     */
    fun onAlarmFired() {
        scope.launch {
            val current = rhythm.value ?: return@launch
            if (!current.isActive()) return@launch
            val now = System.currentTimeMillis()
            val enteringPhase = current.phase(now)
            _boundaryCrossed.value = BoundaryEvent(
                enteringPhase = enteringPhase,
                cycleIndex = current.cycleIndex(now),
                dishId = current.lockedDishId,
                atEpochMs = now,
            )
            cancelBoundaryAlarm()
            scheduleBoundaryAlarm(current)
        }
    }

    /**
     * Missed-alarm reconciliation. Called on app launch, on RhythmService tick,
     * and on system clock / timezone broadcasts. Recomputes the current phase
     * from `now` and re-arms forward. If a boundary was crossed without firing,
     * surfaces it as a (late) [boundaryCrossed] emission so the UI can react
     * honestly.
     */
    fun reconcile() {
        scope.launch {
            val current = rhythm.value ?: return@launch
            if (!current.isActive()) return@launch
            // If we have no alarm scheduled (or its time is in the past), the
            // user crossed a boundary while we weren't looking. Surface it.
            val expected = current.nextBoundaryEpochMs()
            val now = System.currentTimeMillis()
            if (expected <= now) {
                _boundaryCrossed.value = BoundaryEvent(
                    enteringPhase = current.phase(now),
                    cycleIndex = current.cycleIndex(now),
                    dishId = current.lockedDishId,
                    atEpochMs = now,
                )
            }
            cancelBoundaryAlarm()
            scheduleBoundaryAlarm(current)
        }
    }

    /**
     * Public entry-point for [BootReceiver] in PR E'. For now, simply invokes
     * [reconcile] which is enough to re-arm the alarm post-boot.
     */
    fun rearmIfActive() {
        reconcile()
    }

    /** Read-back helper for UI / service to display dish name. */
    fun dishDisplayName(): String {
        val r = rhythm.value ?: return "Your reward"
        val id = r.lockedDishId ?: return "Your reward"
        return Catalog.byId(id)?.name ?: "Your reward"
    }

    private fun scheduleBoundaryAlarm(rhythm: Rhythm) {
        val am = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager ?: return
        val pi = boundaryAlarmPendingIntent(create = true) ?: return
        val triggerAt = rhythm.nextBoundaryEpochMs()
        // Use setExactAndAllowWhileIdle so doze / app-standby don't push the
        // alarm past the boundary. Falls back implicitly to inexact if exact
        // is denied at runtime; PR E' adds the explicit degraded path.
        try {
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAt, pi)
        } catch (_: SecurityException) {
            // Exact-alarm denied on Android 14+. Degrade to inexact; reconcile()
            // will catch the missed boundary on the next service tick.
            am.set(AlarmManager.RTC_WAKEUP, triggerAt, pi)
        }
    }

    private fun cancelBoundaryAlarm() {
        val existing = boundaryAlarmPendingIntent(create = false) ?: return
        val am = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager ?: return
        am.cancel(existing)
        existing.cancel()
    }

    private fun boundaryAlarmPendingIntent(create: Boolean): PendingIntent? {
        val intent = Intent(context, RhythmAlarmReceiver::class.java).apply {
            action = ACTION_PHASE_BOUNDARY
        }
        val flags = (if (create) PendingIntent.FLAG_UPDATE_CURRENT else PendingIntent.FLAG_NO_CREATE) or
                PendingIntent.FLAG_IMMUTABLE
        return PendingIntent.getBroadcast(context, REQ_BOUNDARY_ALARM, intent, flags)
    }

    companion object {
        const val ACTION_PHASE_BOUNDARY = "com.cadent.cadentfast.action.PHASE_BOUNDARY"
        private const val REQ_BOUNDARY_ALARM = 2001
    }
}
