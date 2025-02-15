package ru.lemonapes.dungler.navigation.character

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.lemonapes.dungler.R
import ru.lemonapes.dungler.domain_models.GearType
import ru.lemonapes.dungler.ui.UIText
import ru.lemonapes.dungler.ui.theme.DunglerTheme
import ru.lemonapes.dungler.ui.theme.LocalThemeColors
import ru.lemonapes.dungler.ui.theme.typographies.LocalThemeTypographies

@Composable
fun CharacterView(state: CharacterViewState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        LazyVerticalGrid(
            GridCells.Fixed(3),
            Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            spacerItem()
            gearItem(state.gears[GearType.HELM]?.image ?: R.drawable.helm_disabled)
            spacerItem()

            gearItem(state.gears[GearType.SHOULDERS]?.image ?: R.drawable.shoulders_disabled)
            gearItem(state.gears[GearType.CHEST]?.image ?: R.drawable.chest_disabled)
            gearItem(state.gears[GearType.GLOVES]?.image ?: R.drawable.gloves_disabled)

            gearItem(state.gears[GearType.WEAPON]?.image ?: R.drawable.sword_disabled)
            gearItem(state.gears[GearType.LEGS]?.image ?: R.drawable.pants_disabled)
            spacerItem()

            spacerItem()
            gearItem(state.gears[GearType.BOOTS]?.image ?: R.drawable.boots_disabled)
        }
        Column(Modifier.scrollable(orientation = Orientation.Vertical, state = rememberScrollState())) {
            state.stats.forEach { (stat, count) ->
                UIText(text = "$stat: $count", textStyle = LocalThemeTypographies.current.regular16)
            }
        }
    }
}

private fun LazyGridScope.spacerItem() {
    item {}
}

private fun LazyGridScope.gearItem(@DrawableRes image: Int) {
    item {
        Image(
            modifier = Modifier
                .padding(6.dp)
                .border(2.dp, LocalThemeColors.current.bordersColor)
                .padding(4.dp),
            painter = painterResource(image),
            contentDescription = stringResource(id = R.string.reagent_icon_description),
        )
    }
}

@Preview
@Composable
private fun CharacterViewPreview() {
    DunglerTheme(darkTheme = true) {
        CharacterView(
            state = CharacterViewState.MOCK,
        )
    }
}