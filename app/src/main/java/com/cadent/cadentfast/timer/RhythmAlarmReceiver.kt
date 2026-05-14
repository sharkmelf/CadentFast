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
 * Fires at the exact next phase-boundary moment, even if the app is backgrounded
 * or killed. On fast→feast (break-fast), posts the high-importance audible
 * notification with the locked dish's name read from the latest persisted
 * rhythm (so any in-flight swap has the last word). On feast→fast, posts a
 * quieter "new fast begins" reminder.
 *
 * Either way, asks the repository to re-arm forward and to emit a boundary
 * event so the UI can present the right state.
 */
class RhythmAlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != RhythmRepository.ACTION_PHASE_BOUNDARY) return

        val app = context.applicationContext as? CadentFastApp ?: return
        val rhythm = app.rhythmRepo.rhythm.value ?: return
        if (!rhythm.isActive()) return

        val now = System.currentTimeMillis()
        val enteringPhase = rhythm.phase(now)
        when (enteringPhase) {
            Phase.Feast -> {
                // Break-fast moment. Loud notification with the current dish name.
                val dishName = rhythm.lockedDishId
                    ?.let { Catalog.byId(it)?.name }
                    ?: "your reward"
                postBreakFastNotification(context, dishName)
            }
            Phase.Fast -> {
                // Feast just ended; the next fast begins now. Quieter
                // confirmation so the user knows their feast window closed.
                postNextFastNotification(context, rhythm.lockedDishId?.let { Catalog.byId(it)?.name })
            }
        }
        app.rhythmRepo.onAlarmFired()
    }

    private fun postBreakFastNotification(context: Context, dishName: String) {
        val nm = context.getSystemService(NotificationManager::class.java) ?: return
        val notification = NotificationCompat.Builder(context, BREAK_FAST_CHANNEL_ID)
            .setContentTitle(context.getString(R.string.break_fast_notification_title))
            .setContentText(context.getString(R.string.break_fast_notification_body, dishName))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setDefaults(NotificationCompat.DEFAULT_SOUND or NotificationCompat.DEFAULT_VIBRATE)
            .setAutoCancel(true)
            .setContentIntent(launchIntent(context))
            .build()
        nm.notify(BREAK_FAST_NOTIF_ID, notification)
    }

    private fun postNextFastNotification(context: Context, lockedDishName: String?) {
        val nm = context.getSystemService(NotificationManager::class.java) ?: return
        val body = if (lockedDishName != null) {
            context.getString(R.string.next_fast_notification_body_with_dish, lockedDishName)
        } else {
            context.getString(R.string.next_fast_notification_body_without_dish)
        }
        val notification = NotificationCompat.Builder(context, NEXT_FAST_CHANNEL_ID)
            .setContentTitle(context.getString(R.string.next_fast_notification_title))
            .setContentText(body)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setAutoCancel(true)
            .setContentIntent(launchIntent(context))
            .build()
        nm.notify(NEXT_FAST_NOTIF_ID, notification)
    }

    private fun launchIntent(context: Context): android.app.PendingIntent {
        val intent = Intent(context, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        return android.app.PendingIntent.getActivity(
            context, 0, intent,
            android.app.PendingIntent.FLAG_UPDATE_CURRENT or android.app.PendingIntent.FLAG_IMMUTABLE,
        )
    }

    companion object {
        const val BREAK_FAST_CHANNEL_ID = "rhythm_break_fast"
        const val NEXT_FAST_CHANNEL_ID = "rhythm_next_fast"
        const val BREAK_FAST_NOTIF_ID = 2
        const val NEXT_FAST_NOTIF_ID = 3
    }
}
