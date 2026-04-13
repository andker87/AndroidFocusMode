package com.androidfocusmode.app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.androidfocusmode.app.data.model.ActivationTrigger
import com.androidfocusmode.app.data.model.FocusMode
import com.androidfocusmode.app.ui.components.TimeInput
import com.androidfocusmode.app.ui.viewmodel.FocusModeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    modeId: Long,
    onBackClick: () -> Unit,
    viewModel: FocusModeViewModel = hiltViewModel()
) {
    var focusMode by remember { mutableStateOf<FocusMode?>(null) }
    var modeName by remember { mutableStateOf("") }
    var modeDescription by remember { mutableStateOf("") }
    var selectedTrigger by remember { mutableStateOf(ActivationTrigger.MANUAL) }
    var allowNotifications by remember { mutableStateOf(true) }
    var allowVibration by remember { mutableStateOf(false) }
    var allowAllCalls by remember { mutableStateOf(false) }
    var allowFavoritesOnly by remember { mutableStateOf(false) }
    var blockAllCalls by remember { mutableStateOf(false) }
    var activateOnAndroidAuto by remember { mutableStateOf(false) }
    
    // Time-based
    var startTime by remember { mutableStateOf<String?>(null) }
    var endTime by remember { mutableStateOf<String?>(null) }
    
    // Location-based
    var latitude by remember { mutableStateOf<String>("") }
    var longitude by remember { mutableStateOf<String>("") }
    var locationName by remember { mutableStateOf("") }
    var radiusMeters by remember { mutableStateOf("100") }
    var minDwellMinutes by remember { mutableStateOf("5") }

    // Load mode if editing
    LaunchedEffect(modeId) {
        if (modeId > 0) {
            val loadedMode = viewModel.focusModes.value.find { it.id == modeId }
            // TODO: Load full mode from repository
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = if (modeId < 0) "New Focus Mode" else "Edit Mode",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Mode Name
            OutlinedTextField(
                value = modeName,
                onValueChange = { modeName = it },
                label = { Text("Mode Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Mode Description
            OutlinedTextField(
                value = modeDescription,
                onValueChange = { modeDescription = it },
                label = { Text("Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                maxLines = 3
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Activation Trigger
            Text(
                text = "Activation Trigger",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            SingleChoiceSegmentedButtonRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                ActivationTrigger.values().forEachIndexed { index, trigger ->
                    SegmentedButton(
                        selected = selectedTrigger == trigger,
                        onClick = { selectedTrigger = trigger },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                    ) {
                        Text(
                            text = when (trigger) {
                                ActivationTrigger.MANUAL -> "Manual"
                                ActivationTrigger.TIME_BASED -> "Time"
                                ActivationTrigger.LOCATION_BASED -> "Location"
                            },
                            fontSize = 12.sp
                        )
                    }
                }
            }

            // Time-based configuration
            if (selectedTrigger == ActivationTrigger.TIME_BASED) {
                TimeInput(
                    label = "Start Time",
                    time = startTime,
                    onTimeChange = { startTime = it }
                )

                TimeInput(
                    label = "End Time",
                    time = endTime,
                    onTimeChange = { endTime = it }
                )
            }

            // Location-based configuration
            if (selectedTrigger == ActivationTrigger.LOCATION_BASED) {
                OutlinedTextField(
                    value = locationName,
                    onValueChange = { locationName = it },
                    label = { Text("Location Name (e.g., Gym, Work)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = latitude,
                    onValueChange = { latitude = it },
                    label = { Text("Latitude") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = longitude,
                    onValueChange = { longitude = it },
                    label = { Text("Longitude") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = radiusMeters,
                    onValueChange = { radiusMeters = it },
                    label = { Text("Radius (meters)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = minDwellMinutes,
                    onValueChange = { minDwellMinutes = it },
                    label = { Text("Min dwell time (minutes)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Notifications & Sound
            Text(
                text = "Notifications & Sound",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Allow Notifications", fontSize = 12.sp)
                Switch(
                    checked = allowNotifications,
                    onCheckedChange = { allowNotifications = it }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Allow Vibration", fontSize = 12.sp)
                Switch(
                    checked = allowVibration,
                    onCheckedChange = { allowVibration = it }
                )
            }

            // Calls & Favorites
            Text(
                text = "Call Settings",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Allow All Calls", fontSize = 12.sp)
                Switch(
                    checked = allowAllCalls,
                    onCheckedChange = { 
                        allowAllCalls = it
                        if (it) allowFavoritesOnly = false
                    }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Favorites Only", fontSize = 12.sp)
                Switch(
                    checked = allowFavoritesOnly,
                    onCheckedChange = { 
                        allowFavoritesOnly = it
                        if (it) allowAllCalls = false
                    }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Block All Calls", fontSize = 12.sp)
                Switch(
                    checked = blockAllCalls,
                    onCheckedChange = { blockAllCalls = it }
                )
            }

            // Android Auto
            if (selectedTrigger == ActivationTrigger.MANUAL) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Activate on Android Auto", fontSize = 12.sp)
                    Switch(
                        checked = activateOnAndroidAuto,
                        onCheckedChange = { activateOnAndroidAuto = it }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Save Button
            Button(
                onClick = {
                    val newMode = FocusMode(
                        id = modeId.takeIf { it > 0 } ?: 0,
                        name = modeName,
                        description = modeDescription,
                        trigger = selectedTrigger,
                        startTime = startTime,
                        endTime = endTime,
                        latitude = latitude.toDoubleOrNull(),
                        longitude = longitude.toDoubleOrNull(),
                        locationName = locationName,
                        radiusMeters = radiusMeters.toIntOrNull() ?: 100,
                        minDwellTimeMinutes = minDwellMinutes.toIntOrNull() ?: 5,
                        activateOnAndroidAuto = activateOnAndroidAuto,
                        allowNotifications = allowNotifications,
                        allowVibration = allowVibration,
                        allowAllCalls = allowAllCalls,
                        allowFavoritesOnly = allowFavoritesOnly,
                        blockAllCalls = blockAllCalls
                    )
                    viewModel.saveFocusMode(newMode)
                    onBackClick()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Save",
                    modifier = Modifier.size(20.dp)
                )
                Text(" Save Mode", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            }

            // Cancel Button
            OutlinedButton(
                onClick = onBackClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("Cancel", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
