package ru.lemonapes.dungler.navigation.character.item_comparison_dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ru.lemonapes.dungler.R
import ru.lemonapes.dungler.navigation.character.EquipmentChangingDialogListener
import ru.lemonapes.dungler.ui.theme.DunglerTheme

@Composable
fun DialogEquipmentChanging(
    dialogEquipmentState: DialogEquipmentState,
    listener: EquipmentChangingDialogListener,
) {
    Dialog(
        onDismissRequest = listener.onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(Modifier.fillMaxWidth()) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                when (dialogEquipmentState) {
                    is DialogEquipmentState.GearShowEquipped ->
                        DialogViewGearDescription(dialogEquipmentState.equippedGear, listener.gearListener)

                    is DialogEquipmentState.GearInventory -> {
                        DialogViewGearListInventory(state = dialogEquipmentState, listener = listener)
                    }

                    is DialogEquipmentState.GearComparison -> {
                        DialogViewGearComparison(
                            gearToCompare = dialogEquipmentState.gearToCompare,
                            equippedGear = dialogEquipmentState.equippedGear,
                            listener = listener.gearListener
                        )
                    }

                    is DialogEquipmentState.FoodShowEquipped -> {
                        DialogViewFoodDescription(dialogEquipmentState.equippedFood, listener.foodListener)
                    }

                    is DialogEquipmentState.FoodInventory -> {
                        DialogViewFoodListInventory(state = dialogEquipmentState, listener = listener)
                    }

                    is DialogEquipmentState.FoodComparison -> {
                        DialogViewFoodComparison(
                            foodToCompare = dialogEquipmentState.foodToCompare,
                            equippedFood = dialogEquipmentState.equippedFood,
                            listener = listener.foodListener
                        )
                    }
                }
            }
            if (dialogEquipmentState is DialogEquipmentState.GearComparison ||
                (dialogEquipmentState is DialogEquipmentState.GearInventory &&
                        dialogEquipmentState.equippedGear != null) ||
                dialogEquipmentState is DialogEquipmentState.FoodComparison ||
                (dialogEquipmentState is DialogEquipmentState.FoodInventory &&
                        dialogEquipmentState.equippedFood != null)
            ) {
                IconButton(
                    modifier = Modifier.align(Alignment.TopStart),
                    onClick = listener.backToInventoryClick
                ) {
                    Icon(
                        modifier = Modifier.size(64.dp),
                        painter = painterResource(R.drawable.ic_arrow_back),
                        tint = Color.Red,
                        contentDescription = stringResource(R.string.dismiss_dialog)
                    )
                }
            }
            IconButton(
                modifier = Modifier.align(Alignment.TopEnd),
                onClick = listener.onDismiss
            ) {
                Icon(
                    modifier = Modifier.size(64.dp),
                    painter = painterResource(R.drawable.ic_cancel),
                    tint = Color.Red,
                    contentDescription = stringResource(R.string.dismiss_dialog)
                )
            }
        }
    }
}

@Preview
@Composable
private fun EquipmentChangingDialogDescriptionPreview() {
    DunglerTheme(darkTheme = true) {
        DialogEquipmentChanging(
            dialogEquipmentState = DialogEquipmentState.GEAR_DESCRIPTION_MOCK,
            listener = EquipmentChangingDialogListener.MOCK,
        )
    }
}

@Preview
@Composable
private fun EquipmentChangingDialogInventoryPreview() {
    DunglerTheme(darkTheme = true) {
        DialogEquipmentChanging(
            dialogEquipmentState = DialogEquipmentState.GEAR_INVENTORY_MOCK_SMALL,
            listener = EquipmentChangingDialogListener.MOCK,
        )
    }
}

@Preview
@Composable
private fun EquipmentChangingDialogComparisonPreview() {
    DunglerTheme(darkTheme = true) {
        DialogEquipmentChanging(
            dialogEquipmentState = DialogEquipmentState.GEAR_COMPARISON_MOCK,
            listener = EquipmentChangingDialogListener.MOCK,
        )
    }
}

@Preview
@Composable
private fun FoodDescriptionDialogPreview() {
    DunglerTheme(darkTheme = true) {
        DialogEquipmentChanging(
            dialogEquipmentState = DialogEquipmentState.FOOD_DESCRIPTION_MOCK,
            listener = EquipmentChangingDialogListener.MOCK,
        )
    }
}

@Preview
@Composable
private fun FoodInventoryDialogPreview() {
    DunglerTheme(darkTheme = true) {
        DialogEquipmentChanging(
            dialogEquipmentState = DialogEquipmentState.FOOD_INVENTORY_MOCK_SMALL,
            listener = EquipmentChangingDialogListener.MOCK,
        )
    }
}

@Preview
@Composable
private fun FoodComparisonDialogPreview() {
    DunglerTheme(darkTheme = true) {
        DialogEquipmentChanging(
            dialogEquipmentState = DialogEquipmentState.FOOD_COMPARISON_MOCK,
            listener = EquipmentChangingDialogListener.MOCK,
        )
    }
}
