package ru.lemonapes.dungler.navigation.character.item_comparison_dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.lemonapes.dungler.domain_models.Food
import ru.lemonapes.dungler.navigation.character.equipment.EquipmentChangingDialogListener
import ru.lemonapes.dungler.ui.image_views.ImageWithCounter
import ru.lemonapes.dungler.ui.theme.DunglerTheme
import ru.lemonapes.dungler.ui.theme.LocalThemeColors

@Composable
fun DialogViewFoodListInventory(
    state: DialogEquipmentState.FoodInventory,
    listener: EquipmentChangingDialogListener,
) {
    DialogViewInventory(
        state = state,
        inventoryList = state.inventoryList,
        listener = listener.stateListener,
        onItemClick = listener.foodListener.foodCompareClick,
        inventoryItem = { food ->
            DialogViewInventoryFood(food)
        }
    )
}


@Composable
private fun DialogViewInventoryFood(
    food: Food,
) {
    ImageWithCounter(
        modifier = Modifier
            .padding(2.dp)
            .background(LocalThemeColors.current.imageBackground),
        painter = painterResource(food.id.image),
        counter = food.count,
        contentDescription = stringResource(food.id.foodName),
    )
}

@Preview
@Composable
private fun InventoryDialogPreview() {
    DunglerTheme(darkTheme = true) {
        DialogViewFoodListInventory(
            listener = EquipmentChangingDialogListener.MOCK,
            state = DialogEquipmentState.FOOD_INVENTORY_MOCK_SMALL
        )
    }
}

