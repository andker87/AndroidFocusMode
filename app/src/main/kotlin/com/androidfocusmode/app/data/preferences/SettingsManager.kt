package com.androidfocusmode.app.data.preferences

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private val prefs: SharedPreferences = 
        context.getSharedPreferences("focus_mode_settings", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_FIRST_RUN = "is_first_run"
        private const val KEY_LAST_ACTIVE_MODE = "last_active_mode"
        private const val KEY_PREDEFINED_MODES_CREATED = "predefined_modes_created"
    }

    var isFirstRun: Boolean
        get() = prefs.getBoolean(KEY_FIRST_RUN, true)
        set(value) = prefs.edit().putBoolean(KEY_FIRST_RUN, value).apply()

    var lastActiveMode: Long
        get() = prefs.getLong(KEY_LAST_ACTIVE_MODE, -1L)
        set(value) = prefs.edit().putLong(KEY_LAST_ACTIVE_MODE, value).apply()

    var predefinedModesCreated: Boolean
        get() = prefs.getBoolean(KEY_PREDEFINED_MODES_CREATED, false)
        set(value) = prefs.edit().putBoolean(KEY_PREDEFINED_MODES_CREATED, value).apply()
}
