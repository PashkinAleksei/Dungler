package ru.lemonapes.dungler.ui.item_comparing_dialog

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
import ru.lemonapes.dungler.domain_models.Gear
import ru.lemonapes.dungler.navigation.character.CharacterListener
import ru.lemonapes.dungler.ui.StatItem
import ru.lemonapes.dungler.ui.StatItemWithComparing
import ru.lemonapes.dungler.ui.UIText
import ru.lemonapes.dungler.ui.theme.DunglerTheme
import ru.lemonapes.dungler.ui.theme.LocalThemeColors
import ru.lemonapes.dungler.ui.theme.typographies.LocalThemeTypographies

@Composable
fun ItemComparingDialogView(
    gearToCompare: Gear,
    equippedGear: Gear? = null,
    listener: CharacterListener,
) {
    Column {
        GearToCompareDescription(
            gearToCompare = gearToCompare,
            equippedGear = equippedGear,
            listener = listener
        )
        equippedGear?.let {
            EquippedGearDescription(
                equippedGear = it,
            )
        }
    }
}

@Composable
fun GearToCompareDescription(
    gearToCompare: Gear,
    equippedGear: Gear?,
    listener: CharacterListener,
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
                painter = painterResource(gearToCompare.image),
                contentDescription = stringResource(id = R.string.reagent_icon_description),
            )
            UIText(
                modifier = Modifier.padding(start = 12.dp, top = 4.dp),
                text = stringResource(gearToCompare.gearId.gearName) + stringResource(
                    R.string.gear_name_level,
                    gearToCompare.level
                ),
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
        gearToCompare.stats.forEach { (stat, count) ->
            StatItemWithComparing(stat.statName, count, equippedGear?.stats?.get(stat) ?: 0)
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 6.dp, top = 12.dp),
            onClick = { listener.gearEquipClick(gearToCompare) }) {
            UIText(
                textStyle = LocalThemeTypographies.current.regular20,
                text = stringResource(R.string.equip_item)
            )
        }
    }
}

@Composable
fun EquippedGearDescription(
    equippedGear: Gear,
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
                painter = painterResource(equippedGear.image),
                contentDescription = stringResource(id = R.string.reagent_icon_description),
            )
            UIText(
                modifier = Modifier.padding(start = 12.dp, top = 4.dp),
                text = stringResource(equippedGear.gearId.gearName) + stringResource(
                    R.string.gear_name_level,
                    equippedGear.level
                ),
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
        equippedGear.stats.forEach { (stat, count) ->
            StatItem(stat.statName, count.toString())
        }
    }
}

@Preview
@Composable
private fun ItemDescriptionDialogViewPreview() {
    DunglerTheme(darkTheme = true) {
        ItemComparingDialogView(
            gearToCompare = Gear.MOCK_2,
            equippedGear = Gear.MOCK_1,
            listener = CharacterListener.EMPTY
        )
    }
}