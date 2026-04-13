package com.androidfocusmode.app.service.geofence

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.ActivityCompat
import com.androidfocusmode.app.receiver.GeofenceReceiver
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeofenceManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val geofencingClient: GeofencingClient = LocationServices.getGeofencingClient(context)
    private val pendingIntent: PendingIntent by lazy {
        val intent = Intent(context, GeofenceReceiver::class.java)
        PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    fun addGeofence(
        geofenceId: String,
        latitude: Double,
        longitude: Double,
        radiusMeters: Float = 100f,
        dwell: Int = 5
    ) {
        val geofence = Geofence.Builder()
            .setRequestId(geofenceId)
            .setCircularRegion(latitude, longitude, radiusMeters)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .setTransitionTypes(
                Geofence.GEOFENCE_TRANSITION_ENTER or 
                Geofence.GEOFENCE_TRANSITION_EXIT or
                Geofence.GEOFENCE_TRANSITION_DWELL
            )
            .setLoiteringDelay(dwell * 60 * 1000) // Convert to milliseconds
            .build()

        val request = GeofencingRequest.Builder()
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .addGeofence(geofence)
            .build()

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        geofencingClient.addGeofences(request, pendingIntent)
            .addOnSuccessListener {
                // Geofence added
            }
            .addOnFailureListener { e ->
                // Handle failure
                e.printStackTrace()
            }
    }

    fun removeGeofence(geofenceId: String) {
        geofencingClient.removeGeofences(listOf(geofenceId))
            .addOnSuccessListener {
                // Geofence removed
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }

    fun removeAllGeofences() {
        geofencingClient.removeGeofences(pendingIntent)
            .addOnSuccessListener {
                // All geofences removed
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }
}
