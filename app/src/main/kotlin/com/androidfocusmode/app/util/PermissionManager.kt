package com.androidfocusmode.app.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PermissionManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    // List of critical permissions for the app
    private val requiredPermissions = listOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.POST_NOTIFICATIONS,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.READ_CONTACTS
    )

    private val backgroundPermissions = listOf(
        Manifest.permission.ACCESS_BACKGROUND_LOCATION,
        Manifest.permission.SCHEDULE_EXACT_ALARM
    )

    /**
     * Get all required permissions
     */
    fun getRequiredPermissions(): List<String> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            requiredPermissions + backgroundPermissions
        } else {
            requiredPermissions
        }
    }

    /**
     * Check if all required permissions are granted
     */
    fun hasAllPermissions(): Boolean {
        return getRequiredPermissions().all { 
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    /**
     * Check if specific permission is granted
     */
    fun hasPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Get missing permissions
     */
    fun getMissingPermissions(): List<String> {
        return getRequiredPermissions().filter { 
            !hasPermission(it)
        }
    }

    /**
     * Request permissions (must be called from Activity)
     */
    fun requestPermissions(activity: Activity, requestCode: Int = PERMISSION_REQUEST_CODE) {
        val missing = getMissingPermissions()
        if (missing.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                activity,
                missing.toTypedArray(),
                requestCode
            )
        }
    }

    /**
     * Check if permission should show rationale (Android 6+)
     */
    fun shouldShowRationale(activity: Activity, permission: String): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
        } else {
            false
        }
    }

    /**
     * Handle permission result
     */
    fun handlePermissionResult(
        requestCode: Int,
        grantResults: IntArray,
        onAllGranted: () -> Unit,
        onDenied: (List<String>) -> Unit
    ) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                onAllGranted()
            } else {
                // Get which permissions were denied
                // Note: You need to pass the permissions array along with results
                onDenied(emptyList())
            }
        }
    }

    companion object {
        const val PERMISSION_REQUEST_CODE = 100

        // Individual permission codes for fine-grained handling
        const val LOCATION_PERMISSION_CODE = 101
        const val NOTIFICATION_PERMISSION_CODE = 102
        const val CONTACTS_PERMISSION_CODE = 103
        const val PHONE_STATE_PERMISSION_CODE = 104
    }
}