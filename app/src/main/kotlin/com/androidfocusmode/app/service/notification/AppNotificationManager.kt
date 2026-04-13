package com.androidfocusmode.app.service.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.androidfocusmode.app.R
import com.androidfocusmode.app.ui.MainActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppNotificationManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    companion object {
        const val CHANNEL_FOCUS_MODE = "focus_mode_channel"
        const val NOTIFICATION_ID_FOCUS_MODE = 1
    }

    init {
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val focusModeChannel = NotificationChannel(
                CHANNEL_FOCUS_MODE,
                "Focus Mode Status",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Notifications about active focus modes"
                enableVibration(false)
            }
            notificationManager.createNotificationChannel(focusModeChannel)
        }
    }

    fun showFocusModeActive(modeName: String) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(context, CHANNEL_FOCUS_MODE)
            .setContentTitle("Focus Mode Active")
            .setContentText("$modeName is now active")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()

        notificationManager.notify(NOTIFICATION_ID_FOCUS_MODE, notification)
    }

    fun dismissFocusModeNotification() {
        notificationManager.cancel(NOTIFICATION_ID_FOCUS_MODE)
    }
}
