package ru.lemonapes.dungler.navigation.dungeon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.lemonapes.dungler.R
import ru.lemonapes.dungler.ui.UIText
import ru.lemonapes.dungler.ui.theme.LocalThemeColors
import ru.lemonapes.dungler.ui.theme.typographies.AppTextStyle

@Composable
fun DamageImageView(
    modifier: Modifier,
    hpChangeValue: Int,
    textStyle: AppTextStyle,
) {
    val damageText = if (hpChangeValue > 0) "+$hpChangeValue" else "$hpChangeValue"
    val image = if (hpChangeValue < 0) R.drawable.bg_damage else R.drawable.bg_heal
    val color = if (hpChangeValue < 0) LocalThemeColors.current.damageColor else LocalThemeColors.current.healColor
    Box(
        modifier = modifier
    ) {
        Image(
            modifier = modifier.size(100.dp),
            painter = painterResource(image),
            contentDescription = stringResource(R.string.damage_view_damage),
            contentScale = ContentScale.Fit,
        )
        UIText(
            modifier = Modifier
                .padding(top = 4.dp, end = 4.dp)
                .align(Alignment.Center),
            text = damageText,
            color = color,
            textStyle = textStyle,
            maxLines = 1
        )
    }
}