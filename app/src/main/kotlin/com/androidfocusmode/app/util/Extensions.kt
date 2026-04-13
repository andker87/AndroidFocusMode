package com.androidfocusmode.app.util

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Show short toast message
 */
fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

/**
 * Show long toast message
 */
fun Context.showLongToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

/**
 * Format time in milliseconds to readable string
 */
fun Long.formatTime(): String {
    val date = Date(this)
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return format.format(date)
}

/**
 * Convert time string HH:mm to minutes
 */
fun String.timeToMinutes(): Int {
    return try {
        val (hours, minutes) = split(":").let { it[0].toInt() to it[1].toInt() }
        hours * 60 + minutes
    } catch (e: Exception) {
        0
    }
}

/**
 * Convert minutes to HH:mm format
 */
fun Int.minutesToTime(): String {
    val hours = this / 60
    val minutes = this % 60
    return String.format("%02d:%02d", hours, minutes)
}

/**
 * Check if time string is in valid HH:mm format
 */
fun String.isValidTimeFormat(): Boolean {
    if (!contains(":")) return false
    val parts = split(":")
    if (parts.size != 2) return false
    
    return try {
        val hours = parts[0].toInt()
        val minutes = parts[1].toInt()
        hours in 0..23 && minutes in 0..59
    } catch (e: Exception) {
        false
    }
}

/**
 * Compose utility to show toast
 */
@Composable
fun ShowToast(message: String) {
    val context = LocalContext.current
    context.showToast(message)
}

/**
 * Safe string to double conversion
 */
fun String.toDoubleOrZero(): Double {
    return toDoubleOrNull() ?: 0.0
}

/**
 * Safe string to int conversion
 */
fun String.toIntOrZero(): Int {
    return toIntOrNull() ?: 0
}

/**
 * Format distance in meters to readable string
 */
fun Int.formatDistance(): String {
    return when {
        this < 1000 -> "$this m"
        else -> String.format("%.1f km", this / 1000.0)
    }
}

/**
 * Check if two time ranges overlap
 */
fun timeRangesOverlap(
    start1: String, end1: String,
    start2: String, end2: String
): Boolean {
    return try {
        val s1 = start1.timeToMinutes()
        val e1 = end1.timeToMinutes()
        val s2 = start2.timeToMinutes()
        val e2 = end2.timeToMinutes()

        // Handle overnight times
        when {
            e1 < s1 && e2 < s2 -> {
                // Both overnight - always overlap
                true
            }
            e1 < s1 -> {
                // First is overnight, second is normal
                s2 < e1 || s1 < e2
            }
            e2 < s2 -> {
                // Second is overnight, first is normal
                s1 < e2 || s2 < e1
            }
            else -> {
                // Both normal times
                s1 < e2 && s2 < e1
            }
        }
    } catch (e: Exception) {
        false
    }
}

/**
 * Get distance between two coordinates (Haversine formula)
 */
fun getDistanceMeters(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val R = 6371000 // Earth radius in meters
    val dLat = Math.toRadians(lat2 - lat1)
    val dLon = Math.toRadians(lon2 - lon1)
    val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
            Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
            Math.sin(dLon / 2) * Math.sin(dLon / 2)
    val c = 2 * Math.asin(Math.sqrt(a))
    return R * c
}
