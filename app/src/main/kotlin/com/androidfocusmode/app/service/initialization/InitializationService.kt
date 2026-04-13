package com.androidfocusmode.app.service.initialization

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.androidfocusmode.app.data.repository.FocusModeRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class InitializationService : Service() {
    @Inject
    lateinit var repository: FocusModeRepository

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        scope.launch {
            // TODO: Restore active focus modes and their schedulers
            // This runs on boot to ensure focus modes are re-initialized
            stopSelf(startId)
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
