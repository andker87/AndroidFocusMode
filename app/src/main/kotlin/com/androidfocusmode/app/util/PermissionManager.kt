package com.androidfocusmode.app.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PermissionManager @Inject constructor(
    private val context: Context
) {
    private val baseRequiredPermissions = listOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.READ_CONTACTS
    )

    private val notificationPermission = Manifest.permission.POST_NOTIFICATIONS

    fun hasPermission(permission: String): Boolean {
        // POST_NOTIFICATIONS exists only on 33+. Below that, treat as granted.
        if (permission == notificationPermission && Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            return true
        }
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }

    fun allPermissionsGranted(): Boolean {
        return getRequiredPermissions().all { hasPermission(it) }
    }

    fun getPermissionsToRequest(): List<String> {
        return getRequiredPermissions().filter { !hasPermission(it) }
    }

    fun hasLocationPermission(): Boolean {
        return hasPermission(Manifest.permission.ACCESS_FINE_LOCATION) ||
            hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    fun hasNotificationPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            hasPermission(notificationPermission)
        } else {
            true
        }
    }

    fun hasPhoneStatePermission(): Boolean {
        return hasPermission(Manifest.permission.READ_PHONE_STATE)
    }

    fun hasContactsPermission(): Boolean {
        return hasPermission(Manifest.permission.READ_CONTACTS)
    }

    private fun getRequiredPermissions(): List<String> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            baseRequiredPermissions + notificationPermission
        } else {
            baseRequiredPermissions
        }
    }
}
``
