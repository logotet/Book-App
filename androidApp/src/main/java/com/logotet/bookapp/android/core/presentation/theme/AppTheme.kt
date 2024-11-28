package com.logotet.bookapp.android.core.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val lightColors = lightColorScheme(
    primary = DarkBlue,
    primaryContainer = DesertWhite,
    secondaryContainer = Gevser,
    secondary = LightBlue,
    tertiary = SandYellow,
    background = DesertWhite,
    surface = Color.White,
    error = CadiumPurple,
)

@Composable
fun AppTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = lightColors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}