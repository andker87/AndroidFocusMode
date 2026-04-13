package com.androidfocusmode.app.data.factory

import com.androidfocusmode.app.data.model.ActivationTrigger
import com.androidfocusmode.app.data.model.FocusMode
import com.androidfocusmode.app.data.model.PredefinedMode

object PredefinedModesFactory {
    
    fun createNormalMode(): FocusMode {
        return FocusMode(
            name = "Normal",
            description = "All apps and notifications enabled",
            isPredefined = true,
            isActive = false,
            trigger = ActivationTrigger.MANUAL,
            allowNotifications = true,
            allowVibration = true,
            allowAllCalls = true,
            blockAllCalls = false,
            whitelistedApps = emptyList()
        )
    }

    fun createNightMode(): FocusMode {
        return FocusMode(
            name = "Night",
            description = "Sleep mode - only favorites and emergency calls",
            isPredefined = true,
            isActive = false,
            trigger = ActivationTrigger.TIME_BASED,
            startTime = "22:00", // 10 PM
            endTime = "08:00",   // 8 AM
            daysOfWeek = (0..6).toList(), // All days
            allowNotifications = false,
            allowVibration = false,
            allowFavoritesOnly = true,
            blockAllCalls = false,
            whitelistedApps = listOf(
                "com.android.phone", // Phone app
                "com.android.contacts" // Contacts
            ),
            screenTimeout = 15,
            brightness = 10
        )
    }

    fun createDrivingMode(): FocusMode {
        return FocusMode(
            name = "Driving",
            description = "Manual activation - silences all notifications except calls",
            isPredefined = true,
            isActive = false,
            trigger = ActivationTrigger.MANUAL,
            activateOnAndroidAuto = true,
            allowNotifications = false,
            allowVibration = false,
            allowFavoritesOnly = true,
            blockAllCalls = false,
            whitelistedApps = listOf(
                "com.android.phone",
                "com.google.android.apps.maps", // Google Maps
                "com.waze" // Waze
            )
        )
    }

    fun createGymMode(): FocusMode {
        return FocusMode(
            name = "Gym",
            description = "Auto-activate by location - no notifications during workout",
            isPredefined = true,
            isActive = false,
            trigger = ActivationTrigger.LOCATION_BASED,
            latitude = null, // User must set this
            longitude = null,
            locationName = "Gym",
            radiusMeters = 100,
            minDwellTimeMinutes = 5,
            allowNotifications = false,
            allowVibration = false,
            blockAllCalls = true,
            whitelistedApps = listOf(
                "com.google.android.apps.fitness", // Google Fit
                "com.strava", // Strava
                "com.spotify.music" // Spotify for music
            )
        )
    }

    fun createWorkMode(): FocusMode {
        return FocusMode(
            name = "Work",
            description = "Office mode - limit notifications, only work contacts",
            isPredefined = true,
            isActive = false,
            trigger = ActivationTrigger.LOCATION_BASED,
            latitude = null, // User must set this
            longitude = null,
            locationName = "Work",
            radiusMeters = 100,
            minDwellTimeMinutes = 5,
            allowNotifications = true,
            allowVibration = false,
            allowFavoritesOnly = true,
            blockAllCalls = false,
            whitelistedApps = listOf(
                "com.google.android.apps.docs", // Google Docs
                "com.google.android.apps.sheets", // Google Sheets
                "com.microsoft.office.outlook", // Outlook
                "com.microsoft.teams", // Teams
                "com.slack", // Slack
                "com.android.phone"
            ),
            screenTimeout = 300, // 5 minutes
            brightness = 150
        )
    }

    fun getAllPredefinedModes(): List<FocusMode> {
        return listOf(
            createNormalMode(),
            createNightMode(),
            createDrivingMode(),
            createGymMode(),
            createWorkMode()
        )
    }
}
