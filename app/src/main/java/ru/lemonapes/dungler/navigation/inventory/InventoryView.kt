package ru.lemonapes.dungler.navigation.inventory

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import ru.lemonapes.dungler.domain_models.Food
import ru.lemonapes.dungler.domain_models.Gear
import ru.lemonapes.dungler.domain_models.ReagentId
import ru.lemonapes.dungler.ui.StateCheck
import ru.lemonapes.dungler.ui.image_views.ImageWithCounter
import ru.lemonapes.dungler.ui.theme.DunglerTheme
import ru.lemonapes.dungler.ui.theme.LocalThemeColors

@Composable
fun InventoryView(
    modifier: Modifier = Modifier,
    state: InventoryState,
    listener: InventoryListener,
) {
    StateCheck(
        modifier = modifier,
        state = state,
        listener = listener.stateListener
    ) {
        LazyVerticalGrid(
            modifier = modifier
                .padding(16.dp),
            columns = GridCells.Fixed(5)
        ) {
            items(state.gears) { gear ->
                /*val modifier = Modifier.clickable {
                    if (item.equipped) {
                        viewListener?.deEquipItemEvent(item.reference)
                    } else {
                        viewListener?.equipItemEvent(item.reference)п
                    }
                }*/

                Box(
                    Modifier
                        .aspectRatio(1f)
                        .padding(horizontal = 1.dp, vertical = 1.dp)
                ) {
                    val border = if (gear.isEquipped) BorderStroke(3.dp, Color.Yellow) else null
                    Surface(border = border) {
                        ImageWithCounter(
                            modifier = Modifier
                                .padding(2.dp)
                                .background(LocalThemeColors.current.imageBackground),
                            painter = painterResource(gear.image),
                            counter = gear.level
                        )
                    }
                }
            }

            items(state.reagents.toList()) { (reagentId, count) ->
                Box(
                    Modifier
                        .aspectRatio(1f)
                        .padding(horizontal = 1.dp, vertical = 1.dp)
                ) {
                    Surface {
                        ImageWithCounter(
                            modifier = Modifier
                                .padding(2.dp)
                                .background(LocalThemeColors.current.imageBackground),
                            painter = painterResource(reagentId.image),
                            counter = count
                        )
                    }
                }
            }

            items(state.food) { food ->
                Box(
                    Modifier
                        .aspectRatio(1f)
                        .padding(horizontal = 1.dp, vertical = 1.dp)
                ) {
                    Surface {
                        ImageWithCounter(
                            modifier = Modifier
                                .padding(2.dp)
                                .background(LocalThemeColors.current.imageBackground),
                            painter = painterResource(food.id.image),
                            counter = food.count
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun BagViewPreview() {
    DunglerTheme(darkTheme = true) {
        InventoryView(
            state = InventoryState(
                gears = persistentListOf(Gear.MOCK_1),
                food = persistentListOf(Food.MOCK_1),
                reagents = persistentMapOf(ReagentId.LINEN_CLOTH to 99),
                isLoading = false,
            ),
            listener = InventoryListener.EMPTY
        )
    }
}