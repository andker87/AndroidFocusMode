package com.androidfocusmode.app.data.db

import androidx.room.RoomDatabase
import com.androidfocusmode.app.data.factory.PredefinedModesFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DatabaseCallback : RoomDatabase.Callback() {
    override fun onCreate(db: RoomDatabase) {
        super.onCreate(db)
        
        // Initialize predefined modes on first run
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            val database = db as FocusModeDatabase
            val dao = database.focusModeDao()
            
            // Insert predefined modes
            val predefinedModes = PredefinedModesFactory.getAllPredefinedModes()
            for (mode in predefinedModes) {
                dao.insert(mode)
            }
        }
    }
}
