package ru.lemonapes.dungler.ui

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.lemonapes.dungler.ui.theme.typographies.LocalThemeTypographies

@Composable
fun StatItem(@StringRes statNameRes: Int, count: String) {
    val statName = stringResource(statNameRes)
    UIText(text = "$statName: $count", textStyle = LocalThemeTypographies.current.regular16)
}

@Composable
fun StatItemWithComparing(@StringRes statNameRes: Int, count: Int, countComparable: Int) {
    val statName = stringResource(statNameRes)
    UIText(
        text = "$statName: $count (${count - countComparable})",
        textStyle = LocalThemeTypographies.current.regular16
    )
}