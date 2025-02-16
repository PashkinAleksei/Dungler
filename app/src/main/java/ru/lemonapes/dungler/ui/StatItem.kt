package ru.lemonapes.dungler.ui

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.lemonapes.dungler.R
import ru.lemonapes.dungler.ui.theme.DunglerTheme
import ru.lemonapes.dungler.ui.theme.typographies.LocalThemeTypographies

@Composable
fun StatItem(@StringRes statNameRes: Int, count: String) {
    val statName = stringResource(statNameRes)
    UIText(text = "$statName: $count", textStyle = LocalThemeTypographies.current.regular16)
}

@Composable
fun StatItemWithComparison(@StringRes statNameRes: Int, count: Int, countComparable: Int) {
    val statName = stringResource(statNameRes)
    val diff = count - countComparable
    val positivitySign = if (diff > 0) "+" else ""
    UIText(
        text = "$statName: $count ($positivitySign${count - countComparable})",
        textStyle = LocalThemeTypographies.current.regular16
    )
}

@Preview
@Composable
fun StatItemWithComparisonPreview() {
    DunglerTheme(darkTheme = true) {
        StatItemWithComparison(R.string.stat_stamina, 12, 10)
    }
}