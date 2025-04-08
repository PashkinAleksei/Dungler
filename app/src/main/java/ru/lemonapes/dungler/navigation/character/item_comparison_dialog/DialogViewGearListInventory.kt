package ru.lemonapes.dungler.navigation.character.item_comparison_dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.lemonapes.dungler.domain_models.Gear
import ru.lemonapes.dungler.navigation.character.EquipmentChangingDialogListener
import ru.lemonapes.dungler.ui.image_views.ImageWithCounter
import ru.lemonapes.dungler.ui.theme.DunglerTheme
import ru.lemonapes.dungler.ui.theme.LocalThemeColors

@Composable
fun DialogViewGearListInventory(
    state: DialogEquipmentState.GearInventory,
    listener: EquipmentChangingDialogListener,
) {
    DialogViewInventory(
        state = state,
        inventoryList = state.inventoryList,
        listener = listener.stateListener,
        onItemClick = listener.gearListener.gearCompareClick,
        inventoryItem = { gear ->
            DialogViewInventoryGear(gear)
        }
    )
}


@Composable
private fun DialogViewInventoryGear(
    gear: Gear,
) {
    ImageWithCounter(
        modifier = Modifier
            .padding(2.dp)
            .background(LocalThemeColors.current.imageBackground),
        painter = painterResource(gear.image),
        counter = gear.level,
        contentDescription = stringResource(gear.gearId.gearName),
    )
}

@Preview
@Composable
private fun InventoryDialogPreview() {
    DunglerTheme(darkTheme = true) {
        DialogViewGearListInventory(
            listener = EquipmentChangingDialogListener.MOCK,
            state = DialogEquipmentState.GEAR_INVENTORY_MOCK_SMALL
        )
    }
}

