package com.androidfocusmode.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.time.LocalTime

enum class ActivationTrigger {
    MANUAL,
    TIME_BASED,
    LOCATION_BASED
}

enum class PredefinedMode {
    NORMAL,
    NIGHT,
    DRIVING,
    GYM,
    WORK
}

@Entity(tableName = "focus_modes")
data class FocusMode(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String = "",
    val isPredefined: Boolean = false,
    val isActive: Boolean = false,
    
    // Trigger Configuration
    val trigger: ActivationTrigger = ActivationTrigger.MANUAL,
    
    // Time-based trigger
    val startTime: String? = null, // HH:mm format
    val endTime: String? = null,   // HH:mm format
    val daysOfWeek: List<Int>? = null, // 0=Sunday, 1=Monday, etc.
    
    // Location-based trigger
    val latitude: Double? = null,
    val longitude: Double? = null,
    val radiusMeters: Int = 100,
    val locationName: String? = null,
    val minDwellTimeMinutes: Int = 5, // For gym/work
    
    // Android Auto detection
    val activateOnAndroidAuto: Boolean = false,
    
    // Notification & Sound Settings
    val allowNotifications: Boolean = true,
    val allowVibration: Boolean = false,
    val screenTimeout: Int = 30, // seconds, 0 = no change
    val brightness: Int? = null, // 0-255, null = no change
    
    // App Whitelist (apps that can notify)
    val whitelistedApps: List<String> = emptyList(),
    
    // Call Settings
    val allowAllCalls: Boolean = false,
    val allowFavoritesOnly: Boolean = false,
    val blockAllCalls: Boolean = false,
    
    // Bluetooth & Wi-Fi
    val bluetoothEnabled: Boolean? = null, // null = no change
    val wifiEnabled: Boolean? = null, // null = no change
    
    // Timestamp
    val createdAt: Long = System.currentTimeMillis(),
    val modifiedAt: Long = System.currentTimeMillis()
)

// DTO for UI
data class FocusModeUI(
    val id: Long = 0,
    val name: String,
    val description: String = "",
    val isActive: Boolean = false,
    val isPredefined: Boolean = false,
    val trigger: ActivationTrigger = ActivationTrigger.MANUAL
)
