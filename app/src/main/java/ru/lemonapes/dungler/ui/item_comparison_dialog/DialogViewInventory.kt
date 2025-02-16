package ru.lemonapes.dungler.ui.item_comparison_dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import ru.lemonapes.dungler.domain_models.Gear
import ru.lemonapes.dungler.navigation.character.CharacterListener
import ru.lemonapes.dungler.ui.image_views.ImageWithCounter
import ru.lemonapes.dungler.ui.theme.DunglerTheme
import ru.lemonapes.dungler.ui.theme.LocalThemeColors

@Composable
fun InventoryDialogView(
    gearList: ImmutableList<Gear>,
    listener: CharacterListener,
) {
    val columns = 4
    val rows = 4

    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Вычисляем ширину каждой ячейки (с учетом отступов между колонками)
        val cellWidth = (maxWidth / columns)
        // Высота грида равна высоте 4 строк, при условии, что ячейка квадратная
        val gridHeight = cellWidth * rows

        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .height(gridHeight)
                .padding(4.dp),
            columns = GridCells.Fixed(columns)
        ) {
            items(if (gearList.size > 16) gearList.size else 16) { index ->
                if (index < gearList.size) {
                    val gear = gearList[index]
                    Box(
                        Modifier
                            .aspectRatio(1f)
                            .background(LocalThemeColors.current.secondaryTextColor)
                            .padding(horizontal = 1.dp, vertical = 1.dp)
                    ) {
                        val border = if (gear.isEquipped) BorderStroke(3.dp, Color.Yellow) else null
                        Surface(modifier = Modifier.clickable { listener.gearCompareClick(gear) }, border = border) {
                            ImageWithCounter(
                                modifier = Modifier
                                    .padding(2.dp)
                                    .background(LocalThemeColors.current.imageBackground),
                                painter = painterResource(gear.image),
                                counter = gear.level
                            )
                        }
                    }
                } else {
                    Box(
                        Modifier
                            .aspectRatio(1f)
                            .background(LocalThemeColors.current.secondaryTextColor)
                            .padding(horizontal = 1.dp, vertical = 1.dp)
                    ) {
                        Surface(modifier = Modifier.fillMaxSize()) {}
                    }
                }
            }
        }
    }
}


@Preview
@Composable
private fun InventoryDialogPreview() {
    DunglerTheme(darkTheme = true) {
        InventoryDialogView(
            gearList = DialogEquipmentState.INVENTORY_MOCK_SMALL.inventoryList,
            listener = CharacterListener.EMPTY
        )
    }
}

