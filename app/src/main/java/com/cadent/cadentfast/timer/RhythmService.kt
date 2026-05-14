package com.cadent.cadentfast.timer

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.cadent.cadentfast.CadentFastApp
import com.cadent.cadentfast.MainActivity
import com.cadent.cadentfast.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Always-on foreground service that keeps the app's process alive for the full
 * rhythm lifetime. Carries an ongoing low-importance notification that displays
 * the current phase remaining ("3h 12m until your table" during fast, "2h 41m
 * of feast remaining" during feast). The break-fast moment lives on a separate
 * high-importance notification fired by [RhythmAlarmReceiver].
 *
 * The service also periodically reconciles with [RhythmRepository.reconcile] so
 * missed alarms get caught on the next tick.
 *
 * PR D' may convert this to a windowed FGS posture per the spec (active in the
 * final hour of each phase + on-demand) to reduce battery cost. For PR A',
 * always-on is the simpler correct shape.
 */
class RhythmService : Service() {

    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private var tickJob: Job? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForegroundSafely(buildNotification("starting"))
        observe()
        return START_STICKY
    }

    private fun startForegroundSafely(notification: Notification) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            startForeground(
                NOTIF_ID, notification,
                ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE,
            )
        } else {
            startForeground(NOTIF_ID, notification)
        }
    }

    private fun observe() {
        if (tickJob?.isActive == true) return
        val repo = (application as CadentFastApp).rhythmRepo
        tickJob = scope.launch {
            repo.rhythm.collectLatest { rhythm ->
                if (rhythm == null || !rhythm.isActive()) {
                    stopSelf()
                    return@collectLatest
                }
                while (true) {
                    val now = System.currentTimeMillis()
                    val label = phaseSubLine(rhythm, now)
                    updateNotification(label)
                    // Reconcile cheaply each tick. Missed-boundary recovery is
                    // centralized in the repo; this just calls into it.
                    repo.reconcile()
                    kotlinx.coroutines.delay(30_000L)
                }
            }
        }
    }

    private fun phaseSubLine(rhythm: Rhythm, now: Long): String {
        val remaining = rhythm.remainingInPhaseMs(now)
        val text = formatRemainingForNotification(remaining)
        return when (rhythm.phase(now)) {
            Phase.Fast -> getString(R.string.ongoing_fast_template, text)
            Phase.Feast -> getString(R.string.ongoing_feast_template, text)
        }
    }

    private fun updateNotification(remainingLabel: String) {
        val nm = getSystemService(android.app.NotificationManager::class.java) ?: return
        nm.notify(NOTIF_ID, buildNotification(remainingLabel))
    }

    private fun buildNotification(remainingLabel: String): Notification {
        val openIntent = PendingIntent.getActivity(
            this, 0,
            Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
        return NotificationCompat.Builder(this, NOTIF_CHANNEL_ID)
            .setContentTitle(getString(R.string.ongoing_title))
            .setContentText(remainingLabel)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)
            .setSilent(true)
            .setShowWhen(false)
            .setOnlyAlertOnce(true)
            .setContentIntent(openIntent)
            .setCategory(NotificationCompat.CATEGORY_PROGRESS)
            .build()
    }

    override fun onDestroy() {
        tickJob?.cancel()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    companion object {
        const val NOTIF_CHANNEL_ID = "rhythm_ongoing"
        const val NOTIF_ID = 1

        fun start(context: Context) {
            context.startForegroundService(Intent(context, RhythmService::class.java))
        }

        fun stop(context: Context) {
            context.stopService(Intent(context, RhythmService::class.java))
        }
    }
}

private fun formatRemainingForNotification(remainingMs: Long): String {
    val totalSeconds = (remainingMs + 999) / 1000
    if (totalSeconds < 60) return "${totalSeconds}s"
    val totalMinutes = totalSeconds / 60
    val hours = totalMinutes / 60
    val minutes = totalMinutes % 60
    return when {
        hours <= 0 -> "${minutes}m"
        minutes == 0L -> "${hours}h"
        else -> "${hours}h ${minutes}m"
    }
}
