package com.androidfocusmode.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TimeInput(
    label: String,
    time: String?,
    onTimeChange: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    val showTimePicker = remember { mutableStateOf(false) }
    val hours = remember { mutableIntStateOf(0) }
    val minutes = remember { mutableIntStateOf(0) }

    // Parse existing time only when time changes (avoid recomposition loops)
    LaunchedEffect(time) {
        if (!time.isNullOrBlank() && time.contains(":")) {
            val parts = time.split(":")
            if (parts.size == 2) {
                hours.intValue = parts[0].toIntOrNull() ?: 0
                minutes.intValue = parts[1].toIntOrNull() ?: 0
            }
        }
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surface)
                .clickable { showTimePicker.value = true }
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = time ?: "Not set",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

            if (time != null) {
                IconButton(
                    onClick = { onTimeChange(null) },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear time",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        if (showTimePicker.value) {
            TimePickerDialog(
                initialHours = hours.intValue,
                initialMinutes = minutes.intValue,
                onConfirm = { h, m ->
                    hours.intValue = h
                    minutes.intValue = m
                    val timeString = "${String.format("%02d", h)}:${String.format("%02d", m)}"
                    onTimeChange(timeString)
                    showTimePicker.value = false
                },
                onDismiss = { showTimePicker.value = false }
            )
        }
    }
}

@Composable
private fun TimePickerDialog(
    initialHours: Int,
    initialMinutes: Int,
    onConfirm: (hours: Int, minutes: Int) -> Unit,
    onDismiss: () -> Unit
) {
    val tmpHours = remember { mutableIntStateOf(initialHours.coerceIn(0, 23)) }
    val tmpMinutes = remember { mutableIntStateOf(initialMinutes.coerceIn(0, 59)) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Time") },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TimeNumberPicker(
                    label = "Hours",
                    value = tmpHours.intValue,
                    min = 0,
                    max = 23,
                    onChange = { tmpHours.intValue = it }
                )

                TimeNumberPicker(
                    label = "Minutes",
                    value = tmpMinutes.intValue,
                    min = 0,
                    max = 59,
                    onChange = { tmpMinutes.intValue = it }
                )

                Text(
                    text = "${String.format("%02d", tmpHours.intValue)}:${String.format("%02d", tmpMinutes.intValue)}",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(tmpHours.intValue, tmpMinutes.intValue) }
            ) { Text("OK") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
private fun TimeNumberPicker(
    label: String,
    value: Int,
    min: Int,
    max: Int,
    onChange: (Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(label, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = { if (value > min) onChange(value - 1) }) {
                Text("-")
            }

            Text(
                text = String.format("%02d", value),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            Button(onClick = { if (value < max) onChange(value + 1) }) {
                Text("+")
            }
        }
    }
}
