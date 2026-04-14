package com.androidfocusmode.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
        OutlinedTextField(
            value = locationName,
            onValueChange = onLocationNameChange,
            label = { Text("Location Name (e.g., Home, Office)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Latitude",
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = latitude.ifEmpty { "Not set" },
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Longitude",
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = longitude.ifEmpty { "Not set" },
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

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

        OutlinedTextField(
            value = radius,
            onValueChange = onRadiusChange,
            label = { Text("Radius (meters)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

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
    val selectedLat = remember { mutableDoubleStateOf(currentLat) }
    val selectedLng = remember { mutableDoubleStateOf(currentLng) }

    AlertDialog(
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
                    text = "Note: Map integration requires Google Maps API key.\n" +
                        "For now, enter coordinates manually or use the preset buttons below.",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = "Quick Locations:",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Button(
                    onClick = {
                        selectedLat.doubleValue = 45.4642
                        selectedLng.doubleValue = 9.1900
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Milan Center")
                }

                OutlinedTextField(
                    value = selectedLat.doubleValue.toString(),
                    onValueChange = {
                        selectedLat.doubleValue = it.toDoubleOrNull() ?: selectedLat.doubleValue
                    },
                    label = { Text("Latitude") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = selectedLng.doubleValue.toString(),
                    onValueChange = {
                        selectedLng.doubleValue = it.toDoubleOrNull() ?: selectedLng.doubleValue
                    },
                    label = { Text("Longitude") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onLocationSelected(selectedLat.doubleValue, selectedLng.doubleValue) }
            ) { Text("OK") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
``
