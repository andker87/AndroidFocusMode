package com.androidfocusmode.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val PrimaryBlue = Color(0xFF1F77E8)
private val SecondaryBlue = Color(0xFF2A8FD4)
private val TertiaryBlue = Color(0xFF4A9FE8)
private val Background = Color(0xFFFAFAFA)
private val DarkBackground = Color(0xFF121212)

private val LightColors = lightColorScheme(
    primary = PrimaryBlue,
    secondary = SecondaryBlue,
    tertiary = TertiaryBlue,
    background = Background,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color(0xFF1C1C1C),
    onSurface = Color(0xFF1C1C1C)
)

private val DarkColors = darkColorScheme(
    primary = PrimaryBlue,
    secondary = SecondaryBlue,
    tertiary = TertiaryBlue,
    background = DarkBackground,
    surface = Color(0xFF1E1E1E),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun AndroidFocusModeTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = androidx.compose.material3.Typography(),
        content = content
    )
}
