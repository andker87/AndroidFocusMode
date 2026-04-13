package com.androidfocusmode.app.service.scheduler

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.androidfocusmode.app.data.model.FocusMode
import com.androidfocusmode.app.worker.TimeBasedFocusModeWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimeBasedScheduler @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val workManager = WorkManager.getInstance(context)

    fun scheduleTimeBasedMode(focusMode: FocusMode) {
        if (focusMode.trigger.name != "TIME_BASED") return
        if (focusMode.startTime == null || focusMode.endTime == null) return

        val startTimeData = Data.Builder()
            .putLong("mode_id", focusMode.id)
            .putBoolean("activate", true)
            .build()

        val startWorkRequest = PeriodicWorkRequestBuilder<TimeBasedFocusModeWorker>(
            15, TimeUnit.MINUTES // Check every 15 minutes
        )
            .setInputData(startTimeData)
            .addTag("time_based_${focusMode.id}")
            .build()

        workManager.enqueueUniquePeriodicWork(
            "time_based_start_${focusMode.id}",
            ExistingPeriodicWorkPolicy.KEEP,
            startWorkRequest
        )
    }

    fun cancelTimeBasedMode(modeId: Long) {
        workManager.cancelAllWorkByTag("time_based_${modeId}")
    }

    /**
     * Calculate minutes until next occurrence of target time
     * @param targetTime HH:mm format (e.g., "22:00")
     * @return Minutes until next occurrence
     */
    fun minutesUntilTime(targetTime: String): Long {
        return try {
            val (hours, minutes) = targetTime.split(":").let { 
                it[0].toInt() to it[1].toInt() 
            }
            val target = LocalTime.of(hours, minutes)
            val now = LocalTime.now()
            
            val duration = if (target > now) {
                ChronoUnit.MINUTES.between(now, target)
            } else {
                // Tomorrow
                ChronoUnit.MINUTES.between(now, target.plusHours(24))
            }
            duration
        } catch (e: Exception) {
            e.printStackTrace()
            0L
        }
    }

    /**
     * Check if time-based mode should be active now
     */
    fun isTimeBasedModeActive(startTime: String, endTime: String): Boolean {
        return try {
            val (startHours, startMinutes) = startTime.split(":").let {
                it[0].toInt() to it[1].toInt()
            }
            val (endHours, endMinutes) = endTime.split(":").let {
                it[0].toInt() to it[1].toInt()
            }
            
            val now = LocalTime.now()
            val start = LocalTime.of(startHours, startMinutes)
            val end = LocalTime.of(endHours, endMinutes)
            
            when {
                start < end -> now >= start && now < end // Normal range (e.g., 9:00-17:00)
                start > end -> now >= start || now < end // Overnight (e.g., 22:00-08:00)
                else -> true // start == end (always active)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}