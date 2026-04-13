package com.androidfocusmode.app.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.androidfocusmode.app.service.initialization.InitializationService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BootCompleteReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            // Start initialization service to restore focus modes
            val startIntent = Intent(context, InitializationService::class.java)
            context?.startService(startIntent)
        }
    }
}
