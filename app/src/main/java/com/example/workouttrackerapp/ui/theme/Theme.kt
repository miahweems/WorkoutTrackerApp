package com.example.workouttrackerapp.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = SoftRed,
    secondary = LightGray,
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black
)

private val DarkColors = darkColorScheme(
    primary = SoftRed, // Buttons & accents
    secondary = LightGray,
    background = DarkGray, // Background color
    surface = DarkGray,
    onPrimary = Color.White,
    onSecondary = DarkGray,
    onBackground = LightGray, // Text color
    onSurface = WhiteText
)

@Composable
fun WorkoutTrackerAppTheme(
    darkTheme: Boolean = true, // Always use dark theme
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = Typography,
        content = content
    )
}

