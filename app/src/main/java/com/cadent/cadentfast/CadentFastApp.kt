package com.cadent.cadentfast

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import com.cadent.cadentfast.timer.RhythmAlarmReceiver
import com.cadent.cadentfast.timer.RhythmRepository
import com.cadent.cadentfast.timer.RhythmService
import com.cadent.cadentfast.timer.RhythmStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class CadentFastApp : Application() {

    val appScope: CoroutineScope by lazy {
        CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }

    val rhythmStore: RhythmStore by lazy { RhythmStore(this) }

    val rhythmRepo: RhythmRepository by lazy {
        RhythmRepository(this, rhythmStore, appScope)
    }

    override fun onCreate() {
        super.onCreate()
        createOngoingChannel()
        createBreakFastChannel()
        createNextFastChannel()
        // Touch the repo so its persisted-rhythm collector starts at process start
        // and reconcile() catches any boundary that was crossed while we were dead.
        rhythmRepo.reconcile()
    }

    /** Quiet ongoing notification channel for the foreground service. */
    private fun createOngoingChannel() {
        val nm = getSystemService(NotificationManager::class.java) ?: return
        val channel = NotificationChannel(
            RhythmService.NOTIF_CHANNEL_ID,
            getString(R.string.ongoing_channel_name),
            NotificationManager.IMPORTANCE_LOW,
        ).apply {
            description = getString(R.string.ongoing_channel_description)
            setShowBadge(false)
            enableVibration(false)
            setSound(null, null)
        }
        nm.createNotificationChannel(channel)
    }

    /** High-importance break-fast channel: the only sustained-attention moment. */
    private fun createBreakFastChannel() {
        val nm = getSystemService(NotificationManager::class.java) ?: return
        val channel = NotificationChannel(
            RhythmAlarmReceiver.BREAK_FAST_CHANNEL_ID,
            getString(R.string.break_fast_channel_name),
            NotificationManager.IMPORTANCE_HIGH,
        ).apply {
            description = getString(R.string.break_fast_channel_description)
            enableVibration(true)
            setShowBadge(true)
            lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        }
        nm.createNotificationChannel(channel)
    }

    /** Default-importance "next fast begins" channel for the feast→fast transition. */
    private fun createNextFastChannel() {
        val nm = getSystemService(NotificationManager::class.java) ?: return
        val channel = NotificationChannel(
            RhythmAlarmReceiver.NEXT_FAST_CHANNEL_ID,
            getString(R.string.next_fast_channel_name),
            NotificationManager.IMPORTANCE_DEFAULT,
        ).apply {
            description = getString(R.string.next_fast_channel_description)
            setShowBadge(false)
            lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        }
        nm.createNotificationChannel(channel)
    }
}
