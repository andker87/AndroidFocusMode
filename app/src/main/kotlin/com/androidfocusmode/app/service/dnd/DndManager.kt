package com.androidfocusmode.app.service.dnd

import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationManagerCompat
import com.androidfocusmode.app.data.model.FocusMode
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DndManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private val notificationManagerCompat = NotificationManagerCompat.from(context)

    fun applyFocusMode(focusMode: FocusMode) {
        // Check permission first
        if (!canAccessNotificationPolicy()) {
            return
        }

        // Set DND policy based on focusMode
        val policy = buildInterruptionFilter(focusMode)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            notificationManager.setInterruptionFilter(policy)
        }

        // Handle whitelist
        handleAppNotifications(focusMode)
    }

    fun disableFocusMode() {
        if (!canAccessNotificationPolicy()) return

        // Reset to normal
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL)
        }
    }

    private fun buildInterruptionFilter(focusMode: FocusMode): Int {
        return when {
            focusMode.blockAllCalls -> NotificationManager.INTERRUPTION_FILTER_NONE
            focusMode.allowNotifications -> NotificationManager.INTERRUPTION_FILTER_ALL
            else -> NotificationManager.INTERRUPTION_FILTER_PRIORITY
        }
    }

    private fun handleAppNotifications(focusMode: FocusMode) {
        // TODO: Implement per-app notification blocking
        // This requires iterating through apps and disabling notifications
        // Will use PackageManager to get list of installed apps
    }

    private fun canAccessNotificationPolicy(): Boolean {
        return notificationManagerCompat.areNotificationsEnabled()
    }

    fun setScreenTimeout(seconds: Int) {
        if (seconds <= 0) return
        // TODO: Set screen timeout via Settings.System
        // This requires WRITE_SETTINGS permission which is system-level
    }

    fun setBrightness(brightness: Int) {
        if (brightness < 0 || brightness > 255) return
        // TODO: Set brightness via Settings.System
    }
}
