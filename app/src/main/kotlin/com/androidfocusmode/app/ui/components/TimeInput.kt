package com.androidfocusmode.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
fun TimeInput(
    label: String,
    time: String?,
    onTimeChange: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    val showTimePicker = remember { mutableStateOf(false) }
    val hours = remember { mutableStateOf(0) }
    val minutes = remember { mutableStateOf(0) }

    // Parse existing time
    if (time != null && time.contains(":")) {
        val parts = time.split(":")
        if (parts.size == 2) {
            hours.value = parts[0].toIntOrNull() ?: 0
            minutes.value = parts[1].toIntOrNull() ?: 0
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

        // Display selected time
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

        // Time Picker Dialog
        if (showTimePicker.value) {
            TimePickerDialog(
                hours = hours.value,
                minutes = minutes.value,
                onHoursChange = { hours.value = it },
                onMinutesChange = { minutes.value = it },
                onConfirm = {
                    val timeString = "${String.format("%02d", hours.value)}:${String.format("%02d", minutes.value)}"
                    onTimeChange(timeString)
                    showTimePicker.value = false
                },
                onDismiss = { showTimePicker.value = false }
            )
        }
    }
}

@Composable
fun TimePickerDialog(
    hours: Int,
    minutes: Int,
    onHoursChange: (Int) -> Unit,
    onMinutesChange: (Int) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    val tmpHours = remember { mutableStateOf(hours) }
    val tmpMinutes = remember { mutableStateOf(minutes) }

    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Time") },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Hour Selector
                TimeNumberPicker(
                    label = "Hours",
                    value = tmpHours.value,
                    min = 0,
                    max = 23,
                    onChange = { tmpHours.value = it }
                )

                // Minute Selector
                TimeNumberPicker(
                    label = "Minutes",
                    value = tmpMinutes.value,
                    min = 0,
                    max = 59,
                    onChange = { tmpMinutes.value = it }
                )

                // Time Display
                Text(
                    text = "${String.format("%02d", tmpHours.value)}:${String.format("%02d", tmpMinutes.value)}",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        confirmButton = {
            androidx.compose.material3.TextButton(onClick = {
                onHoursChange(tmpHours.value)
                onMinutesChange(tmpMinutes.value)
                onConfirm()
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

@Composable
fun TimeNumberPicker(
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
            androidx.compose.material3.Button(onClick = { if (value > min) onChange(value - 1) }) {
                Text("-")
            }

            Text(
                text = String.format("%02d", value),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            androidx.compose.material3.Button(onClick = { if (value < max) onChange(value + 1) }) {
                Text("+")
            }
        }
    }
}
EOF
cat /home/claude/AndroidFocusMode/app/src/main/kotlin/com/androidfocusmode/app/ui/components/TimeInput.kt
Output

package com.androidfocusmode.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
fun TimeInput(
    label: String,
    time: String?,
    onTimeChange: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    val showTimePicker = remember { mutableStateOf(false) }
    val hours = remember { mutableStateOf(0) }
    val minutes = remember { mutableStateOf(0) }

    // Parse existing time
    if (time != null && time.contains(":")) {
        val parts = time.split(":")
        if (parts.size == 2) {
            hours.value = parts[0].toIntOrNull() ?: 0
            minutes.value = parts[1].toIntOrNull() ?: 0
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

        // Display selected time
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

        // Time Picker Dialog
        if (showTimePicker.value) {
            TimePickerDialog(
                hours = hours.value,
                minutes = minutes.value,
                onHoursChange = { hours.value = it },
                onMinutesChange = { minutes.value = it },
                onConfirm = {
                    val timeString = "${String.format("%02d", hours.value)}:${String.format("%02d", minutes.value)}"
                    onTimeChange(timeString)
                    showTimePicker.value = false
                },
                onDismiss = { showTimePicker.value = false }
            )
        }
    }
}

@Composable
fun TimePickerDialog(
    hours: Int,
    minutes: Int,
    onHoursChange: (Int) -> Unit,
    onMinutesChange: (Int) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    val tmpHours = remember { mutableStateOf(hours) }
    val tmpMinutes = remember { mutableStateOf(minutes) }

    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Time") },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Hour Selector
                TimeNumberPicker(
                    label = "Hours",
                    value = tmpHours.value,
                    min = 0,
                    max = 23,
                    onChange = { tmpHours.value = it }
                )

                // Minute Selector
                TimeNumberPicker(
                    label = "Minutes",
                    value = tmpMinutes.value,
                    min = 0,
                    max = 59,
                    onChange = { tmpMinutes.value = it }
                )

                // Time Display
                Text(
                    text = "${String.format("%02d", tmpHours.value)}:${String.format("%02d", tmpMinutes.value)}",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        confirmButton = {
            androidx.compose.material3.TextButton(onClick = {
                onHoursChange(tmpHours.value)
                onMinutesChange(tmpMinutes.value)
                onConfirm()
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

@Composable
fun TimeNumberPicker(
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
            androidx.compose.material3.Button(onClick = { if (value > min) onChange(value - 1) }) {
                Text("-")
            }

            Text(
                text = String.format("%02d", value),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            androidx.compose.material3.Button(onClick = { if (value < max) onChange(value + 1) }) {
                Text("+")
            }
        }
    }
}
