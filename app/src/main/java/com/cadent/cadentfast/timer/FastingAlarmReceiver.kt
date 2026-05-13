package com.cadent.cadentfast.timer

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.cadent.cadentfast.CadentFastApp
import com.cadent.cadentfast.MainActivity
import com.cadent.cadentfast.R
import com.cadent.cadentfast.catalog.Catalog

/**
 * Fires at the exact end-of-fast moment, even if the app is backgrounded or
 * killed. Posts the "kitchen is ready" notification and asks the repository to
 * raise its completion state so the UI can present the break-fast reveal.
 */
class FastingAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != FastingTimerRepository.ACTION_FAST_COMPLETE) return

        val app = context.applicationContext as? CadentFastApp ?: return
        val session = app.timerRepo.session.value
        val dishName = session?.let { Catalog.byId(it.dishId)?.name } ?: "your reward"

        postBreakFastNotification(context, dishName)
        app.timerRepo.onAlarmFired()
    }

    private fun postBreakFastNotification(context: Context, dishName: String) {
        val nm = context.getSystemService(NotificationManager::class.java) ?: return
        val openIntent = android.app.PendingIntent.getActivity(
            context, 0,
            Intent(context, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
            android.app.PendingIntent.FLAG_UPDATE_CURRENT or android.app.PendingIntent.FLAG_IMMUTABLE,
        )
        val notification = NotificationCompat.Builder(context, FastingTimerService.NOTIF_CHANNEL_ID)
            .setContentTitle(context.getString(R.string.break_fast_notification_title))
            .setContentText(context.getString(R.string.break_fast_notification_body, dishName))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setAutoCancel(true)
            .setContentIntent(openIntent)
            .build()
        nm.notify(BREAK_FAST_NOTIF_ID, notification)
    }

    private companion object {
        const val BREAK_FAST_NOTIF_ID = 2
    }
}
