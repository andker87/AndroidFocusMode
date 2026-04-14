package com.androidfocusmode.app.data.factory

import com.androidfocusmode.app.data.model.ActivationTrigger
import com.androidfocusmode.app.data.model.FocusMode

object PredefinedModesFactory {

    fun getAllPredefinedModes(): List<FocusMode> {
        return listOf(
            // NORMAL - Manual activation
            FocusMode(
                id = 1,
                name = "Normal",
                description = "Standard mode - no restrictions",
                trigger = ActivationTrigger.MANUAL,
                isActive = true,
                allowNotifications = true,
                allowVibration = true,
                allowAllCalls = true,
                blockAllCalls = false,
                allowFavoritesOnly = false
            ),
            // NIGHT - Time-based (22:00 - 08:00)
            FocusMode(
                id = 2,
                name = "Night",
                description = "Activate at 22:00, deactivate at 08:00",
                trigger = ActivationTrigger.TIME_BASED,
                startTime = "22:00",
                endTime = "08:00",
                allowNotifications = false,
                allowVibration = false,
                allowAllCalls = false,
                blockAllCalls = true,
                allowFavoritesOnly = false
            ),
            // DRIVING - Manual with Android Auto
            FocusMode(
                id = 3,
                name = "Driving",
                description = "Activate when driving",
                trigger = ActivationTrigger.MANUAL,
                activateOnAndroidAuto = true,
                allowNotifications = false,
                allowVibration = false,
                allowAllCalls = false,
                blockAllCalls = true,
                allowFavoritesOnly = true
            ),
            // GYM - Location-based
            FocusMode(
                id = 4,
                name = "Gym",
                description = "Activate at your gym location",
                trigger = ActivationTrigger.LOCATION_BASED,
                locationName = "Gym",
                latitude = 45.4642,  // Default: Milan coordinates
                longitude = 9.1900,
                radiusMeters = 100,
                minDwellTimeMinutes = 5,
                allowNotifications = false,
                allowVibration = false,
                allowAllCalls = false,
                blockAllCalls = true,
                allowFavoritesOnly = false
            ),
            // WORK - Location-based
            FocusMode(
                id = 5,
                name = "Work",
                description = "Activate at your work location",
                trigger = ActivationTrigger.LOCATION_BASED,
                locationName = "Work",
                latitude = 45.4642,  // Default: Milan coordinates
                longitude = 9.1900,
                radiusMeters = 150,
                minDwellTimeMinutes = 2,
                allowNotifications = false,
                allowVibration = true,
                allowAllCalls = false,
                blockAllCalls = false,
                allowFavoritesOnly = true
            )
        )
    }
}
