package com.androidfocusmode.app.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GeofenceReceiver : BroadcastReceiver() {

    @Inject
    lateinit var geofenceHandler: GeofenceEventHandler

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return

        try {
            val geofencingEvent = GeofencingEvent.fromIntent(intent) ?: return

            if (geofencingEvent.hasError()) {
                Log.e("GeofenceReceiver", "Geofence error: ${geofencingEvent.errorCode}")
                return
            }

            val triggerGeofences = geofencingEvent.triggeringGeofences ?: return

            for (geofence in triggerGeofences) {
                when (geofencingEvent.geofenceTransition) {
                    Geofence.GEOFENCE_TRANSITION_ENTER -> {
                        geofenceHandler.handleGeofenceEnter(geofence.requestId)
                    }
                    Geofence.GEOFENCE_TRANSITION_EXIT -> {
                        geofenceHandler.handleGeofenceExit(geofence.requestId)
                    }
                    Geofence.GEOFENCE_TRANSITION_DWELL -> {
                        geofenceHandler.handleGeofenceDwell(geofence.requestId)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("GeofenceReceiver", "Error handling geofence", e)
        }
    }
}
