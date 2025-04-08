package ru.lemonapes.dungler.navigation.character

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.lemonapes.dungler.R
import ru.lemonapes.dungler.domain_models.Food
import ru.lemonapes.dungler.domain_models.Gear
import ru.lemonapes.dungler.domain_models.GearType
import ru.lemonapes.dungler.domain_models.StatId
import ru.lemonapes.dungler.navigation.character.item_comparison_dialog.DialogEquipmentChanging
import ru.lemonapes.dungler.ui.StatItem
import ru.lemonapes.dungler.ui.StateCheck
import ru.lemonapes.dungler.ui.image_views.ImageWithCounter
import ru.lemonapes.dungler.ui.theme.DunglerTheme
import ru.lemonapes.dungler.ui.theme.LocalThemeColors

@Composable
fun CharacterView(
    modifier: Modifier = Modifier,
    state: CharacterViewState,
    listener: CharacterListener,
) {
    StateCheck(
        modifier = modifier,
        state = state,
        listener = listener.stateListener
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
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
                foodItem(state.food, listener.onFoodClick)

                spacerItem()
                bootsItem(state.gears[GearType.BOOTS], listener.onGearClick)
            }
            Column(
                Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
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
                DialogEquipmentChanging(
                    dialogEquipmentState = dialogState,
                    listener = listener.equipmentChangingDialogListener
                )
            }
        }
    }
}

private fun LazyGridScope.helmItem(
    helm: Gear?,
    onGearClick: (gearType: GearType, gear: Gear?) -> Unit,
) {
    gearItem(
        image = helm?.image ?: R.drawable.helm_disabled,
        gearName = helm?.gearId?.gearName ?: R.string.empty_slot_helm
    ) {
        onGearClick(GearType.HELM, helm)
    }
}

private fun LazyGridScope.shouldersItem(
    shoulders: Gear?,
    onGearClick: (gearType: GearType, gear: Gear?) -> Unit,
) {
    gearItem(
        image = shoulders?.image ?: R.drawable.shoulders_disabled,
        gearName = shoulders?.gearId?.gearName ?: R.string.empty_slot_shoulders
    ) {
        onGearClick(GearType.SHOULDERS, shoulders)
    }
}

private fun LazyGridScope.chestItem(
    chest: Gear?,
    onGearClick: (gearType: GearType, gear: Gear?) -> Unit,
) {
    gearItem(
        image = chest?.image ?: R.drawable.chest_disabled,
        gearName = chest?.gearId?.gearName ?: R.string.empty_slot_chest
    ) {
        onGearClick(GearType.CHEST, chest)
    }
}

private fun LazyGridScope.glovesItem(
    gloves: Gear?,
    onGearClick: (gearType: GearType, gear: Gear?) -> Unit,
) {
    gearItem(
        image = gloves?.image ?: R.drawable.gloves_disabled,
        gearName = gloves?.gearId?.gearName ?: R.string.empty_slot_gloves
    ) {
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
    gearItem(
        image = weapon?.image ?: R.drawable.sword_disabled,
        gearName = weapon?.gearId?.gearName ?: R.string.empty_slot_weapon
    ) {
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
    gearItem(
        image = legs?.image ?: R.drawable.pants_disabled,
        gearName = legs?.gearId?.gearName ?: R.string.empty_slot_legs
    ) {
        onGearClick(GearType.LEGS, legs)
    }
}

private fun LazyGridScope.bootsItem(
    boots: Gear?,
    onGearClick: (gearType: GearType, gear: Gear?) -> Unit,
) {
    gearItem(
        image = boots?.image ?: R.drawable.boots_disabled,
        gearName = boots?.gearId?.gearName ?: R.string.empty_slot_boots
    ) {
        onGearClick(GearType.BOOTS, boots)
    }
}

private fun LazyGridScope.foodItem(
    food: Food?,
    onFoodClick: () -> Unit,
) {
    item {
        val image = food?.id?.image ?: R.drawable.food_disabled
        ImageWithCounter(
            modifier = Modifier
                .padding(22.dp)
                .background(LocalThemeColors.current.imageBackground)
                .border(2.dp, LocalThemeColors.current.bordersColor)
                .padding(4.dp)
                .clickable(onClick = onFoodClick),
            painter = painterResource(image),
            counter = food?.count ?: 0,
            contentDescription = stringResource(food?.id?.foodName ?: R.string.empty_slot_food),
        )
    }
}

private fun LazyGridScope.spacerItem() {
    item {}
}

private fun LazyGridScope.gearItem(
    modifier: Modifier = Modifier,
    @DrawableRes image: Int,
    @StringRes gearName: Int,
    onClick: () -> Unit,
) {
    item {
        Image(
            modifier = modifier
                .padding(6.dp)
                .background(LocalThemeColors.current.imageBackground)
                .border(2.dp, LocalThemeColors.current.bordersColor)
                .padding(4.dp)
                .clickable { onClick() },
            painter = painterResource(image),
            contentDescription = stringResource(gearName),
        )
    }
}

@Preview
@Composable
private fun CharacterViewPreview() {
    DunglerTheme(darkTheme = true) {
        CharacterView(
            state = CharacterViewState.MOCK,
            listener = CharacterListener.MOCK,
        )
    }
}