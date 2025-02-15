package ru.lemonapes.dungler.ui.item_comparing_dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
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
import ru.lemonapes.dungler.R
import ru.lemonapes.dungler.domain_models.Gear
import ru.lemonapes.dungler.ui.StatItem
import ru.lemonapes.dungler.ui.UIText
import ru.lemonapes.dungler.ui.theme.DunglerTheme
import ru.lemonapes.dungler.ui.theme.LocalThemeColors
import ru.lemonapes.dungler.ui.theme.typographies.LocalThemeTypographies

@Composable
fun ItemDescriptionDialog(gear: Gear, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Box(Modifier.fillMaxWidth()) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
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
                            painter = painterResource(gear.image),
                            contentDescription = stringResource(id = R.string.reagent_icon_description),
                        )
                        UIText(
                            modifier = Modifier.padding(start = 12.dp, top = 4.dp),
                            text = stringResource(gear.gearId.gearName) + stringResource(
                                R.string.gear_name_level,
                                gear.level
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
                    gear.stats.forEach { (stat, count) ->
                        StatItem(stat.statName, count.toString())
                    }
                }
            }
            IconButton(modifier = Modifier.align(Alignment.TopEnd), onClick = onDismiss) {
                Icon(
                    modifier = Modifier.size(36.dp),
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
private fun ItemDescriptionDialogPreview() {
    DunglerTheme(darkTheme = true) {
        ItemDescriptionDialog(
            gear = Gear.MOCK
        ) {}
    }
}
