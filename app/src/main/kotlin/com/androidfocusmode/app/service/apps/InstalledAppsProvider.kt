package com.androidfocusmode.app.service.apps

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InstalledAppsProvider @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val packageManager = context.packageManager

    /**
     * Get list of all user-installed apps (excluding system apps)
     */
    fun getUserInstalledApps(): List<AppInfo> {
        val apps = mutableListOf<AppInfo>()
        val installedPackages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

        for (app in installedPackages) {
            // Skip system apps
            if ((app.flags and ApplicationInfo.FLAG_SYSTEM) == 0 || 
                (app.flags and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
                val label = try {
                    packageManager.getApplicationLabel(app).toString()
                } catch (e: Exception) {
                    app.packageName
                }
                
                apps.add(
                    AppInfo(
                        packageName = app.packageName,
                        label = label
                    )
                )
            }
        }

        return apps.sortedBy { it.label }
    }

    /**
     * Get app label from package name
     */
    fun getAppLabel(packageName: String): String? {
        return try {
            val app = packageManager.getApplicationInfo(packageName, 0)
            packageManager.getApplicationLabel(app).toString()
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Check if package is installed
     */
    fun isAppInstalled(packageName: String): Boolean {
        return try {
            packageManager.getApplicationInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    /**
     * Get commonly used apps for default whitelists
     */
    fun getCommonAppsForMode(modeType: String): List<String> {
        return when (modeType) {
            "NIGHT" -> listOf(
                "com.android.phone",
                "com.android.contacts",
                "com.android.emergency"
            )
            "DRIVING" -> listOf(
                "com.android.phone",
                "com.google.android.apps.maps",
                "com.waze",
                "com.google.android.apps.messaging"
            )
            "GYM" -> listOf(
                "com.google.android.apps.fitness",
                "com.strava",
                "com.spotify.music",
                "com.runkeeper",
                "com.endomondo"
            )
            "WORK" -> listOf(
                "com.google.android.apps.docs",
                "com.google.android.apps.sheets",
                "com.microsoft.office.outlook",
                "com.microsoft.teams",
                "com.slack",
                "com.android.phone",
                "com.google.android.gm"
            )
            else -> emptyList()
        }
    }
}

data class AppInfo(
    val packageName: String,
    val label: String
)
