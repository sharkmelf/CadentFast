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
 * Keeps the app's process resident while a fast is in progress and shows a
 * quiet ongoing notification with the time remaining. The actual countdown
 * is computed from the persisted FastSession; this service is the keep-alive,
 * not the source of truth.
 */
class FastingTimerService : Service() {

    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private var tickJob: Job? = null
    private var initialDishName: String = "Your reward"

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        initialDishName = intent?.getStringExtra(EXTRA_DISH_NAME) ?: initialDishName
        startForegroundSafely(buildNotification(initialDishName, remainingLabel = "starting"))
        observeSession()
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

    private fun observeSession() {
        if (tickJob?.isActive == true) return
        val repo = (application as CadentFastApp).timerRepo
        tickJob = scope.launch {
            repo.session.collectLatest { session ->
                if (session == null) {
                    stopSelf()
                    return@collectLatest
                }
                while (true) {
                    val now = System.currentTimeMillis()
                    if (session.isComplete(now)) {
                        stopSelf()
                        break
                    }
                    val remainingLabel = formatRemainingForNotification(session.remainingMs(now))
                    updateNotification(session.dishId, remainingLabel)
                    kotlinx.coroutines.delay(30_000L)
                }
            }
        }
    }

    private fun updateNotification(dishId: String, remainingLabel: String) {
        val name = com.cadent.cadentfast.catalog.Catalog.byId(dishId)?.name ?: initialDishName
        val nm = getSystemService(android.app.NotificationManager::class.java) ?: return
        nm.notify(NOTIF_ID, buildNotification(name, remainingLabel))
    }

    private fun buildNotification(dishName: String, remainingLabel: String): Notification {
        val openIntent = PendingIntent.getActivity(
            this, 0,
            Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
        return NotificationCompat.Builder(this, NOTIF_CHANNEL_ID)
            .setContentTitle(getString(R.string.timer_notification_title))
            .setContentText(getString(R.string.timer_notification_template, dishName, remainingLabel))
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
        const val NOTIF_CHANNEL_ID = "fasting_timer"
        const val NOTIF_ID = 1
        const val EXTRA_DISH_NAME = "dish_name"

        fun start(context: Context, dishName: String) {
            val intent = Intent(context, FastingTimerService::class.java)
                .putExtra(EXTRA_DISH_NAME, dishName)
            context.startForegroundService(intent)
        }

        fun stop(context: Context) {
            context.stopService(Intent(context, FastingTimerService::class.java))
        }
    }
}

private fun formatRemainingForNotification(remainingMs: Long): String {
    val totalMinutes = (remainingMs + 59_999) / 60_000
    if (totalMinutes <= 0) return "any moment now"
    val hours = totalMinutes / 60
    val minutes = totalMinutes % 60
    return when {
        hours <= 0 -> "${minutes}m"
        minutes == 0L -> "${hours}h"
        else -> "${hours}h ${minutes}m"
    }
}
