package ru.lemonapes.dungler.navigation.character.item_comparison_dialog

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
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import ru.lemonapes.dungler.parent_view_model.State
import ru.lemonapes.dungler.ui.StateCheck
import ru.lemonapes.dungler.ui.StateListener
import ru.lemonapes.dungler.ui.theme.LocalThemeColors

@Composable
fun <T> DialogViewInventory(
    state: State,
    inventoryList: ImmutableList<T>,
    listener: StateListener,
    onItemClick: (T) -> Unit,
    inventoryItem: @Composable (T) -> Unit,
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(gridHeight)
        ) {
            StateCheck(
                state = state,
                listener = listener
            ) {
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp),
                    columns = GridCells.Fixed(columns)
                ) {
                    items(if (inventoryList.size > 16) inventoryList.size else 16) { index ->
                        if (index < inventoryList.size) {
                            val gear = inventoryList[index]
                            Box(
                                Modifier
                                    .aspectRatio(1f)
                                    .background(LocalThemeColors.current.secondaryTextColor)
                                    .padding(horizontal = 1.dp, vertical = 1.dp)
                            ) {
                                Surface(
                                    modifier = Modifier.clickable { onItemClick(gear) },
                                ) {
                                    inventoryItem(gear)
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
    }
}