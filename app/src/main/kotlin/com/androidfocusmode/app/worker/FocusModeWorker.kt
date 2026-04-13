package com.androidfocusmode.app.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.androidfocusmode.app.data.repository.FocusModeRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class TimeBasedFocusModeWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val repository: FocusModeRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val modeId = inputData.getLong("mode_id", -1L)
            val shouldActivate = inputData.getBoolean("activate", true)

            if (modeId == -1L) {
                return Result.failure()
            }

            if (shouldActivate) {
                repository.activateMode(modeId)
            } else {
                repository.deactivateMode(modeId)
            }

            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }
}

@HiltWorker
class LocationCheckWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val repository: FocusModeRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            // TODO: Check current location and activate/deactivate modes
            // This will be called periodically to ensure location-based modes
            // are properly activated/deactivated

            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }
}
