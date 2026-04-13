package com.androidfocusmode.app.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.androidfocusmode.app.data.model.FocusMode

@Database(
    entities = [FocusMode::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class FocusModeDatabase : RoomDatabase() {
    abstract fun focusModeDao(): FocusModeDao

    companion object {
        @Volatile
        private var INSTANCE: FocusModeDatabase? = null

        fun getDatabase(context: Context): FocusModeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FocusModeDatabase::class.java,
                    "focus_mode_database"
                ).addCallback(DatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
