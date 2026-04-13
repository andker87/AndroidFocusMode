package com.androidfocusmode.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@HiltAndroidApp
class AndroidFocusModeApp : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        initializePredefinedModes()
    }

    private fun initializePredefinedModes() {
        applicationScope.launch {
            // TODO: Initialize predefined modes on first run
            // Check SharedPreferences to see if this is first run
            // If yes, create NORMAL, NIGHT, DRIVING, GYM, WORK modes
        }
    }
}
