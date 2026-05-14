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
 * Single coordinator for the locked fast: persistence, the foreground-service
 * keep-alive, and the exact end-of-fast alarm. Held as a singleton on the
 * Application so every screen and the service observe the same StateFlow.
 */
class FastingTimerRepository(
    private val context: Context,
    private val store: FastingTimerStore,
    private val scope: CoroutineScope,
) {
    val session: StateFlow<FastSession?> = store.session
        .stateIn(scope, SharingStarted.Eagerly, initialValue = null)

    private val _justCompleted = MutableStateFlow<FastSession?>(null)
    val justCompleted: StateFlow<FastSession?> = _justCompleted

    /** Lock a dish and begin the fast. Idempotent: starting a second fast replaces the first. */
    fun startFast(dishId: String, durationMs: Long) {
        scope.launch {
            val now = System.currentTimeMillis()
            val session = FastSession(dishId = dishId, startEpochMs = now, durationMs = durationMs)
            store.save(session)
            cancelEndAlarm()
            scheduleEndAlarm(session)
            FastingTimerService.start(context, dishDisplayName(dishId))
        }
    }

    /** End any in-progress fast. Safe to call when nothing is running. */
    fun endFast() {
        scope.launch {
            store.clear()
            cancelEndAlarm()
            FastingTimerService.stop(context)
        }
    }

    /** Called from the alarm receiver when the fast completes naturally. */
    fun onAlarmFired() {
        scope.launch {
            val current = session.value ?: return@launch
            _justCompleted.value = current
            FastingTimerService.stop(context)
        }
    }

    fun acknowledgeCompletion() {
        _justCompleted.value = null
        endFast()
    }

    private fun scheduleEndAlarm(session: FastSession) {
        val am = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager ?: return
        val pi = endAlarmPendingIntent(create = true) ?: return
        am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, session.endEpochMs, pi)
    }

    private fun cancelEndAlarm() {
        val existing = endAlarmPendingIntent(create = false) ?: return
        val am = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager ?: return
        am.cancel(existing)
        existing.cancel()
    }

    private fun endAlarmPendingIntent(create: Boolean): PendingIntent? {
        val intent = Intent(context, FastingAlarmReceiver::class.java).apply {
            action = ACTION_FAST_COMPLETE
        }
        val flags = (if (create) PendingIntent.FLAG_UPDATE_CURRENT else PendingIntent.FLAG_NO_CREATE) or
                PendingIntent.FLAG_IMMUTABLE
        return PendingIntent.getBroadcast(context, REQ_END_ALARM, intent, flags)
    }

    private fun dishDisplayName(dishId: String): String =
        Catalog.byId(dishId)?.name ?: "Your reward"

    companion object {
        const val ACTION_FAST_COMPLETE = "com.cadent.cadentfast.action.FAST_COMPLETE"
        private const val REQ_END_ALARM = 1001
    }
}
