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
        
        // Initialize predefined modes on first run
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            // Note: We can't access FocusModeDatabase here directly
            // This callback will be called, but inserting predefined modes
            // should be done in the ViewModel or Repository on first app launch
            // For now, we'll skip this and handle it elsewhere
        }
    }
}