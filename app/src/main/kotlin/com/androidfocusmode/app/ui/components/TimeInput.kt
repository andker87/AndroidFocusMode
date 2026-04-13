package com.androidfocusmode.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TimeInput(
    label: String,
    time: String?, // HH:mm format
    onTimeChange: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    val timeState = remember(time) { mutableStateOf(time ?: "") }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            if (timeState.value.isNotEmpty()) {
                IconButton(
                    onClick = {
                        timeState.value = ""
                        onTimeChange(null)
                    },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        OutlinedTextField(
            value = timeState.value,
            onValueChange = { newValue ->
                // Validate HH:mm format
                if (newValue.isEmpty() || isValidTimeFormat(newValue)) {
                    timeState.value = newValue
                    onTimeChange(if (newValue.isEmpty()) null else newValue)
                }
            },
            label = { Text("HH:mm") },
            placeholder = { Text("e.g., 22:00") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            singleLine = true
        )

        Text(
            text = "24-hour format",
            fontSize = 10.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

private fun isValidTimeFormat(time: String): Boolean {
    if (time.length > 5) return false
    if (time.contains(":") && time.split(":").size == 2) {
        val (hours, minutes) = time.split(":")
        return try {
            hours.toInt() in 0..23 && minutes.toInt() in 0..59
        } catch (e: Exception) {
            false
        }
    }
    return time.all { it.isDigit() || it == ':' }
}
