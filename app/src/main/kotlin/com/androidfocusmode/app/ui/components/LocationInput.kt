package com.androidfocusmode.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LocationInput(
    locationName: String,
    latitude: String,
    longitude: String,
    radius: String,
    onLocationNameChange: (String) -> Unit,
    onLatitudeChange: (String) -> Unit,
    onLongitudeChange: (String) -> Unit,
    onRadiusChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val showMapPicker = remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Location Name
        OutlinedTextField(
            value = locationName,
            onValueChange = onLocationNameChange,
            label = { Text("Location Name (e.g., Home, Office)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Map Button
        Button(
            onClick = { showMapPicker.value = true },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Pick location",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text("Pick Location on Map")
        }

        // Coordinates Display
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Latitude", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(
                    text = latitude.ifEmpty { "Not set" },
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text("Longitude", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(
                    text = longitude.ifEmpty { "Not set" },
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        // Manual Coordinate Input
        OutlinedTextField(
            value = latitude,
            onValueChange = onLatitudeChange,
            label = { Text("Latitude (manual)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = longitude,
            onValueChange = onLongitudeChange,
            label = { Text("Longitude (manual)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Radius
        OutlinedTextField(
            value = radius,
            onValueChange = onRadiusChange,
            label = { Text("Radius (meters)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Map Picker Dialog
        if (showMapPicker.value) {
            SimpleMapPicker(
                currentLat = latitude.toDoubleOrNull() ?: 45.4642,
                currentLng = longitude.toDoubleOrNull() ?: 9.1900,
                onLocationSelected = { lat, lng ->
                    onLatitudeChange(lat.toString())
                    onLongitudeChange(lng.toString())
                    showMapPicker.value = false
                },
                onDismiss = { showMapPicker.value = false }
            )
        }
    }
}

@Composable
fun SimpleMapPicker(
    currentLat: Double,
    currentLng: Double,
    onLocationSelected: (Double, Double) -> Unit,
    onDismiss: () -> Unit
) {
    val selectedLat = remember { mutableStateOf(currentLat) }
    val selectedLng = remember { mutableStateOf(currentLng) }

    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Location") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    "Note: Map integration requires Google Maps API key.\n" +
                    "For now, enter coordinates manually or use the preset buttons below.",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                // Quick presets
                Text("Quick Locations:", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)

                // Milan preset
                Button(
                    onClick = {
                        selectedLat.value = 45.4642
                        selectedLng.value = 9.1900
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Milan Center")
                }

                // Coordinate input
                OutlinedTextField(
                    value = selectedLat.value.toString(),
                    onValueChange = { selectedLat.value = it.toDoubleOrNull() ?: selectedLat.value },
                    label = { Text("Latitude") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = selectedLng.value.toString(),
                    onValueChange = { selectedLng.value = it.toDoubleOrNull() ?: selectedLng.value },
                    label = { Text("Longitude") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            androidx.compose.material3.TextButton(onClick = {
                onLocationSelected(selectedLat.value, selectedLng.value)
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            androidx.compose.material3.TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
EOF
cat /home/claude/AndroidFocusMode/app/src/main/kotlin/com/androidfocusmode/app/ui/components/LocationInput.kt
Output

package com.androidfocusmode.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LocationInput(
    locationName: String,
    latitude: String,
    longitude: String,
    radius: String,
    onLocationNameChange: (String) -> Unit,
    onLatitudeChange: (String) -> Unit,
    onLongitudeChange: (String) -> Unit,
    onRadiusChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val showMapPicker = remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Location Name
        OutlinedTextField(
            value = locationName,
            onValueChange = onLocationNameChange,
            label = { Text("Location Name (e.g., Home, Office)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Map Button
        Button(
            onClick = { showMapPicker.value = true },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Pick location",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text("Pick Location on Map")
        }

        // Coordinates Display
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Latitude", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(
                    text = latitude.ifEmpty { "Not set" },
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text("Longitude", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(
                    text = longitude.ifEmpty { "Not set" },
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        // Manual Coordinate Input
        OutlinedTextField(
            value = latitude,
            onValueChange = onLatitudeChange,
            label = { Text("Latitude (manual)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = longitude,
            onValueChange = onLongitudeChange,
            label = { Text("Longitude (manual)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Radius
        OutlinedTextField(
            value = radius,
            onValueChange = onRadiusChange,
            label = { Text("Radius (meters)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Map Picker Dialog
        if (showMapPicker.value) {
            SimpleMapPicker(
                currentLat = latitude.toDoubleOrNull() ?: 45.4642,
                currentLng = longitude.toDoubleOrNull() ?: 9.1900,
                onLocationSelected = { lat, lng ->
                    onLatitudeChange(lat.toString())
                    onLongitudeChange(lng.toString())
                    showMapPicker.value = false
                },
                onDismiss = { showMapPicker.value = false }
            )
        }
    }
}

@Composable
fun SimpleMapPicker(
    currentLat: Double,
    currentLng: Double,
    onLocationSelected: (Double, Double) -> Unit,
    onDismiss: () -> Unit
) {
    val selectedLat = remember { mutableStateOf(currentLat) }
    val selectedLng = remember { mutableStateOf(currentLng) }

    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Location") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    "Note: Map integration requires Google Maps API key.\n" +
                    "For now, enter coordinates manually or use the preset buttons below.",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                // Quick presets
                Text("Quick Locations:", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)

                // Milan preset
                Button(
                    onClick = {
                        selectedLat.value = 45.4642
                        selectedLng.value = 9.1900
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Milan Center")
                }

                // Coordinate input
                OutlinedTextField(
                    value = selectedLat.value.toString(),
                    onValueChange = { selectedLat.value = it.toDoubleOrNull() ?: selectedLat.value },
                    label = { Text("Latitude") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = selectedLng.value.toString(),
                    onValueChange = { selectedLng.value = it.toDoubleOrNull() ?: selectedLng.value },
                    label = { Text("Longitude") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            androidx.compose.material3.TextButton(onClick = {
                onLocationSelected(selectedLat.value, selectedLng.value)
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            androidx.compose.material3.TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
