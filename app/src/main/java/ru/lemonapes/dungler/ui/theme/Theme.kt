package ru.lemonapes.dungler.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.limonapes.dungle.ui.theme.typographies.LimonApesTypographies
import com.limonapes.dungle.ui.theme.typographies.ThemeTypographies

@Composable
fun DunglerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) getDarkColorPalette() else getLightColorPalette()
    val colorScheme = if (darkTheme) getMaterialDarkColorPalette() else getMaterialLightColorPalette()

    CompositionLocalProvider(
        ThemeTypographies provides LimonApesTypographies(),
        LocalThemeColors provides colors
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}