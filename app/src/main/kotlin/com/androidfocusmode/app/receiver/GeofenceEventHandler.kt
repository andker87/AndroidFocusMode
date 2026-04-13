package com.androidfocusmode.app.receiver

import com.androidfocusmode.app.data.model.ActivationTrigger
import com.androidfocusmode.app.data.repository.FocusModeRepository
import com.androidfocusmode.app.service.notification.AppNotificationManager
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeofenceEventHandler @Inject constructor(
    private val repository: FocusModeRepository,
    private val notificationManager: AppNotificationManager
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    fun handleGeofenceEnter(geofenceId: String) {
        scope.launch {
            // TODO: Find mode associated with this geofence
            // and activate it
        }
    }

    fun handleGeofenceExit(geofenceId: String) {
        scope.launch {
            // TODO: Deactivate mode associated with this geofence
        }
    }

    fun handleGeofenceDwell(geofenceId: String) {
        scope.launch {
            // TODO: Activate mode after dwell time
            // This is useful for "Gym" mode
        }
    }
}
