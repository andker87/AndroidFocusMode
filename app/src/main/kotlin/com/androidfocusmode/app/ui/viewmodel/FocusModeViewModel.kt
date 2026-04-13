package com.androidfocusmode.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidfocusmode.app.data.model.ActivationTrigger
import com.androidfocusmode.app.data.model.FocusMode
import com.androidfocusmode.app.data.model.FocusModeUI
import com.androidfocusmode.app.data.repository.FocusModeRepository
import com.androidfocusmode.app.service.dnd.DndManager
import com.androidfocusmode.app.service.geofence.GeofenceManager
import com.androidfocusmode.app.service.notification.AppNotificationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FocusModeViewModel @Inject constructor(
    private val repository: FocusModeRepository,
    private val dndManager: DndManager,
    private val geofenceManager: GeofenceManager,
    private val notificationManager: AppNotificationManager
) : ViewModel() {

    private val _focusModes = MutableStateFlow<List<FocusModeUI>>(emptyList())
    val focusModes: StateFlow<List<FocusModeUI>> = _focusModes.asStateFlow()

    private val _activeFocusMode = MutableStateFlow<FocusMode?>(null)
    val activeFocusMode: StateFlow<FocusMode?> = _activeFocusMode.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadFocusModes()
    }

    private fun loadFocusModes() {
        viewModelScope.launch {
            repository.getAllModesFlow().collect { modes ->
                _focusModes.value = modes.map { focusMode ->
                    FocusModeUI(
                        id = focusMode.id,
                        name = focusMode.name,
                        description = focusMode.description,
                        isActive = focusMode.isActive,
                        isPredefined = focusMode.isPredefined,
                        trigger = focusMode.trigger
                    )
                }
                
                val active = modes.find { it.isActive }
                _activeFocusMode.value = active
            }
        }
    }

    fun activateFocusMode(modeId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val mode = repository.getModeById(modeId) ?: return@launch
                
                // Deactivate all others
                repository.activateMode(modeId)
                
                // Apply DND settings
                dndManager.applyFocusMode(mode)
                
                // Setup geofencing if needed
                if (mode.trigger == ActivationTrigger.LOCATION_BASED && 
                    mode.latitude != null && mode.longitude != null) {
                    geofenceManager.addGeofence(
                        geofenceId = "mode_${modeId}",
                        latitude = mode.latitude,
                        longitude = mode.longitude,
                        radiusMeters = mode.radiusMeters.toFloat(),
                        dwell = mode.minDwellTimeMinutes
                    )
                }
                
                // Show notification
                notificationManager.showFocusModeActive(mode.name)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deactivateFocusMode(modeId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.deactivateMode(modeId)
                dndManager.disableFocusMode()
                notificationManager.dismissFocusModeNotification()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun saveFocusMode(focusMode: FocusMode) {
        viewModelScope.launch {
            if (focusMode.id == 0L) {
                repository.insertMode(focusMode)
            } else {
                repository.updateMode(focusMode)
            }
        }
    }

    fun deleteFocusMode(modeId: Long) {
        viewModelScope.launch {
            val mode = repository.getModeById(modeId)
            if (mode != null) {
                repository.deleteMode(mode)
            }
        }
    }

    fun toggleFocusMode(modeId: Long, isActive: Boolean) {
        if (isActive) {
            activateFocusMode(modeId)
        } else {
            deactivateFocusMode(modeId)
        }
    }
}