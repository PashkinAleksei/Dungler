package ru.lemonapes.dungler.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Локальное хранилище для кастомных цветов темы
internal val LocalThemeColors = staticCompositionLocalOf { getLightColorPalette() }

// Data class для нестандартной палитры (например, для игровых элементов)
data class DungeonColors(
    val primaryTextColor: Color,
    val secondaryTextColor: Color,
    val background: Color,
    val positiveTextColor: Color,
    val negativeTextColor: Color,
    val experienceColor: Color,
    val healthBarColor: Color,
    val manaBarColor: Color,
    val rageBarColor: Color,
    val surfaceColor: Color,
    val surfaceSemiTransparentColor: Color,
    val bordersColor: Color,
    val imageBackground: Color,
)

fun getDarkColorPalette() = DungeonColors(
    primaryTextColor = PRIMARY_TEXT_COLOR_DARK,
    secondaryTextColor = SECONDARY_TEXT_COLOR_DARK,
    background = BACKGROUND_DARK,
    positiveTextColor = POSITIVE_TEXT_COLOR_DARK,
    negativeTextColor = NEGATIVE_TEXT_COLOR_DARK,
    experienceColor = Purple400,
    healthBarColor = HEALTH,
    manaBarColor = MANA,
    rageBarColor = RAGE_COLOR,
    surfaceColor = SURFACE,
    surfaceSemiTransparentColor = SURFACE_SEMI_TRANSPARENT,
    bordersColor = BordersColorDark,
    imageBackground = ImageBackgroundDark,
)

fun getLightColorPalette() = DungeonColors(
    primaryTextColor = PRIMARY_TEXT_COLOR_LIGHT,
    secondaryTextColor = SECONDARY_TEXT_COLOR,
    background = BACKGROUND_LIGHT,
    positiveTextColor = POSITIVE_TEXT_COLOR_LIGHT,
    negativeTextColor = NEGATIVE_TEXT_COLOR_LIGHT,
    experienceColor = Purple400,
    healthBarColor = HEALTH,
    manaBarColor = MANA,
    rageBarColor = RAGE_COLOR,
    surfaceColor = Color.White,
    surfaceSemiTransparentColor = Color.White,
    bordersColor = BordersColor,
    imageBackground = ImageBackground,
)

// Константы цветов
private val BACKGROUND_DARK = Color(0xFF282828)
private val BACKGROUND_LIGHT = Color(0xB3282828)

private val PRIMARY_TEXT_COLOR_DARK = Color(0xFFFFEBBC)
private val SECONDARY_TEXT_COLOR_DARK = Color(0xFF7B6C4C)
private val POSITIVE_TEXT_COLOR_DARK = Color(0xFF00FF00)
private val NEGATIVE_TEXT_COLOR_DARK = Color(0xFFFF0000)

private val PRIMARY_TEXT_COLOR_LIGHT = Color(0xFF241F20)
private val SECONDARY_TEXT_COLOR = Color(0xFF3E3A3A)
private val POSITIVE_TEXT_COLOR_LIGHT = Color(0xFF00FF00)
private val NEGATIVE_TEXT_COLOR_LIGHT = Color(0xFFFF0000)

val Purple200 = Color(0xFFBB86FC)
val Purple400 = Color(0xFF872BF8)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val PRIMARY = Color(0xFF616161)
val SECONDARY = Color(0xFF282828)
val ACCENT = Color(0xFF00FF00)
val HEALTH = Color(0xFF449E2B)
val MANA = Color(0xFF3700B3)
val RAGE_COLOR = Color(0xFFC23127)
val SURFACE = Color(0xFF3C3B3B)
val SURFACE_SEMI_TRANSPARENT = Color(0xA63C3B3B)

val BordersColorDark = Color(0xFFCCCCCC)
val ImageBackground = Color(0xFF000000)
val ImageBackgroundDark = Color(0xFF000000)
val BordersColor = Color(0xFF636363)
val ItemBagCountText = Color(0xFFFFFFFF)
val PositiveText = Color(0xFF00FF00)
val CustomToast = Color(0xD9FFC107)
val CustomToastText = Color(0xFF292929)

val primaryDefault = Color(0xFFBB86FC)
val primaryVariantDefault = Color(0xFF3700B3)
val secondaryDefault = Color(0xFF03DAC6)
val secondaryVariantDefault = Color(0xFF121212)
val backgroundDefault = Color(0xFF121212)

// Функции для Material3-палитры
fun getMaterialDarkColorPalette(): ColorScheme = darkColorScheme(
    primary = PRIMARY,
    onPrimary = PRIMARY_TEXT_COLOR_DARK,
    secondary = SECONDARY,
    onSecondary = SECONDARY_TEXT_COLOR_DARK,
    background = BACKGROUND_DARK,
    surface = SURFACE
)

fun getMaterialLightColorPalette(): ColorScheme = lightColorScheme(
    primary = Purple500,
    onPrimary = PRIMARY_TEXT_COLOR_LIGHT,
    secondary = Teal200,
    background = BACKGROUND_LIGHT,
    surface = Color.White
)
