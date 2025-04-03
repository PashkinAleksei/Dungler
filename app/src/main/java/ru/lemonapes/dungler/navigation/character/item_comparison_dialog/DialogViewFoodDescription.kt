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
import ru.lemonapes.dungler.navigation.character.FoodChangingDialogListener
import ru.lemonapes.dungler.ui.StatItem
import ru.lemonapes.dungler.ui.UIText
import ru.lemonapes.dungler.ui.theme.DunglerTheme
import ru.lemonapes.dungler.ui.theme.LocalThemeColors
import ru.lemonapes.dungler.ui.theme.typographies.LocalThemeTypographies

@Composable
fun DialogViewFoodDescription(
    food: Food,
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
                painter = painterResource(food.id.image),
                contentDescription = stringResource(id = R.string.reagent_icon_description),
            )
            UIText(
                modifier = Modifier.padding(start = 12.dp, top = 4.dp),
                text = stringResource(food.id.foodName),
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
        StatItem(R.string.food_health_regen_amount, food.healthRegenAmount.toString())
        Row(
            modifier = Modifier
                .padding(top = 12.dp),
        ) {
            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 6.dp),
                onClick = { listener.foodDeEquipClick() }) {
                UIText(
                    textStyle = LocalThemeTypographies.current.regular20,
                    text = stringResource(R.string.de_equip_food)
                )
            }
            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 6.dp),
                onClick = { listener.foodShowInventoryClick() }) {
                UIText(
                    textStyle = LocalThemeTypographies.current.regular20,
                    text = stringResource(R.string.change_item)
                )
            }
        }
    }
}

@Preview
@Composable
private fun ItemDescriptionDialogViewPreview() {
    DunglerTheme(darkTheme = true) {
        DialogViewFoodDescription(
            food = Food.MOCK_1,
            listener = FoodChangingDialogListener.MOCK,
        )
    }
}