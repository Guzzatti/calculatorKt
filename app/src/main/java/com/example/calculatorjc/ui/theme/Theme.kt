package com.example.calculatorjc.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColorScheme(
    primary = Color(0xFF1E88E5),
    primaryContainer = Color(0xFF1976D2),
    secondary = Color(0xFFFFA000)
)

private val LightColorPalette = lightColorScheme(
    primary = Color(0xFF2196F3),
    primaryContainer = Color(0xFF1976D2),
    secondary = Color(0xFFFFC107)
)

@Composable
fun CalculadoraTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes(),
        content = content
    )
}