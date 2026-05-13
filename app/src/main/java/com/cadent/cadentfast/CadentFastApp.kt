package com.cadent.cadentfast

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import com.cadent.cadentfast.timer.FastingTimerRepository
import com.cadent.cadentfast.timer.FastingTimerService
import com.cadent.cadentfast.timer.FastingTimerStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class CadentFastApp : Application() {

    val appScope: CoroutineScope by lazy {
        CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }

    val timerStore: FastingTimerStore by lazy { FastingTimerStore(this) }

    val timerRepo: FastingTimerRepository by lazy {
        FastingTimerRepository(this, timerStore, appScope)
    }

    override fun onCreate() {
        super.onCreate()
        createTimerNotificationChannel()
        // Touch the repo so its persisted-session collector starts at process start.
        timerRepo
    }

    private fun createTimerNotificationChannel() {
        val nm = getSystemService(NotificationManager::class.java) ?: return
        val channel = NotificationChannel(
            FastingTimerService.NOTIF_CHANNEL_ID,
            getString(R.string.timer_channel_name),
            NotificationManager.IMPORTANCE_LOW,
        ).apply {
            description = getString(R.string.timer_channel_description)
            setShowBadge(false)
            enableVibration(false)
            setSound(null, null)
        }
        nm.createNotificationChannel(channel)
    }
}
