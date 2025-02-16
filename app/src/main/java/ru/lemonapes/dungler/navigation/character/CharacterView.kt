package ru.lemonapes.dungler.navigation.character

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import ru.lemonapes.dungler.domain_models.Gear
import ru.lemonapes.dungler.domain_models.GearType
import ru.lemonapes.dungler.domain_models.StatId
import ru.lemonapes.dungler.ui.StatItem
import ru.lemonapes.dungler.ui.item_comparing_dialog.EquipmentChangingDialog
import ru.lemonapes.dungler.ui.theme.DunglerTheme
import ru.lemonapes.dungler.ui.theme.LocalThemeColors

@Composable
fun CharacterView(state: CharacterViewState, listener: CharacterListener) {
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
            helmItem(state.gears[GearType.HELM], listener.onGearClick)
            spacerItem()

            shouldersItem(state.gears[GearType.SHOULDERS], listener.onGearClick)
            chestItem(state.gears[GearType.CHEST], listener.onGearClick)
            glovesItem(state.gears[GearType.GLOVES], listener.onGearClick)

            weaponItem(state.gears[GearType.WEAPON], listener.onGearClick)
            legsItem(state.gears[GearType.LEGS], listener.onGearClick)
            spacerItem()

            spacerItem()
            bootsItem(state.gears[GearType.BOOTS], listener.onGearClick)
        }
        Column(Modifier.scrollable(orientation = Orientation.Vertical, state = rememberScrollState())) {
            state.stats.forEach { (stat, count) ->
                val maxDamage = state.stats[StatId.DAMAGE_MAX]?.let { "-$it" }
                when (stat) {
                    StatId.DAMAGE_MIN -> {
                        StatItem(R.string.stat_damage, count.toString() + maxDamage)
                    }

                    StatId.DAMAGE_MAX -> {}

                    else -> {
                        StatItem(stat.statName, count.toString())
                    }
                }
            }
        }
        state.dialogEquipmentState?.let { dialogState ->
            EquipmentChangingDialog(
                gearDescriptionDialogState = dialogState,
                listener = listener
            )
        }
    }
}

private fun LazyGridScope.helmItem(
    helm: Gear?,
    onGearClick: (gearType: GearType, gear: Gear?) -> Unit,
) {
    gearItem(helm?.image ?: R.drawable.helm_disabled) {
        onGearClick(GearType.HELM, helm)
    }
}

private fun LazyGridScope.shouldersItem(
    shoulders: Gear?,
    onGearClick: (gearType: GearType, gear: Gear?) -> Unit,
) {
    gearItem(
        shoulders?.image ?: R.drawable.shoulders_disabled
    ) {
        onGearClick(GearType.SHOULDERS, shoulders)
    }
}

private fun LazyGridScope.chestItem(
    chest: Gear?,
    onGearClick: (gearType: GearType, gear: Gear?) -> Unit,
) {
    gearItem(
        chest?.image ?: R.drawable.chest_disabled
    ) {
        onGearClick(GearType.CHEST, chest)
    }
}

private fun LazyGridScope.glovesItem(
    gloves: Gear?,
    onGearClick: (gearType: GearType, gear: Gear?) -> Unit,
) {
    gearItem(gloves?.image ?: R.drawable.gloves_disabled) {
        onGearClick(
            GearType.GLOVES,
            gloves
        )
    }
}

private fun LazyGridScope.weaponItem(
    weapon: Gear?,
    onGearClick: (gearType: GearType, gear: Gear?) -> Unit,
) {
    gearItem(weapon?.image ?: R.drawable.sword_disabled) {
        onGearClick(
            GearType.WEAPON,
            weapon
        )
    }
}

private fun LazyGridScope.legsItem(
    legs: Gear?,
    onGearClick: (gearType: GearType, gear: Gear?) -> Unit,
) {
    gearItem(legs?.image ?: R.drawable.pants_disabled) {
        onGearClick(GearType.LEGS, legs)
    }
}

private fun LazyGridScope.bootsItem(
    boots: Gear?,
    onGearClick: (gearType: GearType, gear: Gear?) -> Unit,
) {
    gearItem(boots?.image ?: R.drawable.boots_disabled) {
        onGearClick(GearType.BOOTS, boots)
    }
}

private fun LazyGridScope.spacerItem() {
    item {}
}

private fun LazyGridScope.gearItem(@DrawableRes image: Int, onClick: () -> Unit) {
    item {
        Image(
            modifier = Modifier
                .padding(6.dp)
                .background(LocalThemeColors.current.imageBackground)
                .border(2.dp, LocalThemeColors.current.bordersColor)
                .padding(4.dp)
                .clickable { onClick() },
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
            listener = CharacterListener.EMPTY,
        )
    }
}