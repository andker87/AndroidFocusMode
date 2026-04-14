package com.androidfocusmode.app.data.db

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.androidfocusmode.app.data.factory.PredefinedModesFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DatabaseCallback : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            val predefinedModes = PredefinedModesFactory.getAllPredefinedModes()

            val sql = """
                INSERT INTO focus_modes (
                    id, name, description, trigger, isActive,
                    startTime, endTime,
                    latitude, longitude, locationName, radiusMeters, minDwellTimeMinutes,
                    activateOnAndroidAuto,
                    allowNotifications, allowVibration, allowAllCalls, blockAllCalls, allowFavoritesOnly
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """.trimIndent()

            for (mode in predefinedModes) {
                db.execSQL(
                    sql,
                    arrayOf(
                        mode.id,
                        mode.name,
                        mode.description,
                        mode.trigger.name,
                        if (mode.isActive) 1 else 0,
                        mode.startTime,
                        mode.endTime,
                        mode.latitude,
                        mode.longitude,
                        mode.locationName,
                        mode.radiusMeters,
                        mode.minDwellTimeMinutes,
                        if (mode.activateOnAndroidAuto) 1 else 0,
                        if (mode.allowNotifications) 1 else 0,
                        if (mode.allowVibration) 1 else 0,
                        if (mode.allowAllCalls) 1 else 0,
                        if (mode.blockAllCalls) 1 else 0,
                        if (mode.allowFavoritesOnly) 1 else 0
                    )
                )
            }
        }
    }
}
``
