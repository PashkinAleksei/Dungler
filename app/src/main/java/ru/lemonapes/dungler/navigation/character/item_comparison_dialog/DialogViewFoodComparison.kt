package ru.lemonapes.dungler.navigation.character.item_comparison_dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.lemonapes.dungler.R
import ru.lemonapes.dungler.domain_models.Food
import ru.lemonapes.dungler.navigation.character.equipment.FoodChangingDialogListener
import ru.lemonapes.dungler.ui.StatItem
import ru.lemonapes.dungler.ui.StatItemWithComparison
import ru.lemonapes.dungler.ui.UIText
import ru.lemonapes.dungler.ui.theme.DunglerTheme
import ru.lemonapes.dungler.ui.theme.LocalThemeColors
import ru.lemonapes.dungler.ui.theme.typographies.LocalThemeTypographies

@Composable
fun DialogViewFoodComparison(
    foodToCompare: Food,
    equippedFood: Food? = null,
    listener: FoodChangingDialogListener,
) {
    Column {
        FoodToCompareDescription(
            foodToCompare = foodToCompare,
            equippedFood = equippedFood,
            listener = listener
        )
        equippedFood?.let {
            EquippedFoodDescription(
                equippedFood = it,
            )
        }
    }
}

@Composable
private fun FoodToCompareDescription(
    foodToCompare: Food,
    equippedFood: Food?,
    listener: FoodChangingDialogListener,
) {
    Column(
        modifier = Modifier.padding(16.dp),
    ) {
        Row {
            Image(
                modifier = Modifier
                    .size(86.dp)
                    .background(
                        LocalThemeColors.current.imageBackground,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .border(
                        2.dp, LocalThemeColors.current.bordersColor,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(4.dp),
                painter = painterResource(foodToCompare.id.image),
                contentDescription = stringResource(id = R.string.reagent_icon_description),
            )
            UIText(
                modifier = Modifier.padding(start = 12.dp, top = 4.dp),
                text = stringResource(foodToCompare.id.foodName),
                textStyle = LocalThemeTypographies.current.regular18
            )
        }
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 16.dp),
            thickness = 2.dp,
            color = LocalThemeColors.current.secondaryTextColor,
        )
        StatItemWithComparison(
            R.string.food_health_regen_amount,
            foodToCompare.healthRegenAmount,
            equippedFood?.healthRegenAmount ?: 0
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 6.dp, top = 12.dp),
            onClick = { listener.foodEquipClick(foodToCompare) }) {
            UIText(
                textStyle = LocalThemeTypographies.current.regular20,
                text = stringResource(R.string.equip_item)
            )
        }
    }
}

@Composable
private fun EquippedFoodDescription(
    equippedFood: Food,
) {
    Column(
        modifier = Modifier.padding(16.dp),
    ) {
        Row {
            Image(
                modifier = Modifier
                    .size(86.dp)
                    .background(
                        LocalThemeColors.current.imageBackground,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .border(
                        2.dp, LocalThemeColors.current.bordersColor,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(4.dp),
                painter = painterResource(equippedFood.id.image),
                contentDescription = stringResource(id = R.string.reagent_icon_description),
            )
            UIText(
                modifier = Modifier.padding(start = 12.dp, top = 4.dp),
                text = stringResource(equippedFood.id.foodName),
                textStyle = LocalThemeTypographies.current.regular18
            )
        }
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 16.dp),
            thickness = 2.dp,
            color = LocalThemeColors.current.secondaryTextColor,
        )
        StatItem(R.string.food_health_regen_amount, equippedFood.healthRegenAmount.toString())
    }
}

@Preview
@Composable
private fun ItemDescriptionDialogViewPreview() {
    DunglerTheme(darkTheme = true) {
        DialogViewFoodComparison(
            foodToCompare = Food.MOCK_2,
            equippedFood = Food.MOCK_1,
            listener = FoodChangingDialogListener.MOCK
        )
    }
}