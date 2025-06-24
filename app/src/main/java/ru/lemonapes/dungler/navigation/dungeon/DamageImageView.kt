package ru.lemonapes.dungler.navigation.dungeon

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.lemonapes.dungler.R
import ru.lemonapes.dungler.domain_models.AttackResult
import ru.lemonapes.dungler.ui.UIText
import ru.lemonapes.dungler.ui.models.AttackVO
import ru.lemonapes.dungler.ui.models.HpChangeVO
import ru.lemonapes.dungler.ui.theme.LocalThemeColors
import ru.lemonapes.dungler.ui.theme.typographies.AppTextStyle

@Composable
fun DamageImageView(
    modifier: Modifier,
    hpChangeVO: HpChangeVO,
    textStyle: AppTextStyle,
) {
    val damageText: String
    @DrawableRes val image: Int
    @DrawableRes val color: Color

    if (hpChangeVO is AttackVO) {
        damageText = hpChangeVO.getDamageText()
        image = hpChangeVO.getDrawable()
        color = LocalThemeColors.current.damageColor
    } else {
        damageText = "+${hpChangeVO.hpValue}"
        image = R.drawable.bg_heal
        color = LocalThemeColors.current.healColor
    }
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

@Composable
private fun AttackVO.getDamageText() = when (attackResult) {
    AttackResult.MISS -> stringResource(R.string.damage_view_miss)
    AttackResult.DODGE -> stringResource(R.string.damage_view_dodge)
    AttackResult.PARRY -> stringResource(R.string.damage_view_parry)
    AttackResult.SUCCESSFULL -> "$hpValue"
}

private fun AttackVO.getDrawable() = when (attackResult) {
    AttackResult.MISS -> R.drawable.bg_miss
    AttackResult.DODGE -> R.drawable.bg_dodge
    AttackResult.PARRY -> R.drawable.bg_dodge
    AttackResult.SUCCESSFULL -> R.drawable.bg_damage
}