package ru.lemonapes.dungler.navigation.inventory

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.lemonapes.dungler.R
import ru.lemonapes.dungler.domain_models.Gear
import ru.lemonapes.dungler.domain_models.ReagentId
import ru.lemonapes.dungler.ui.image_views.ImageWithCounter
import ru.lemonapes.dungler.ui.theme.DunglerTheme

@Composable
fun InventoryView(
    state: InventoryState,
    listener: InventoryListener,
) {
    Scaffold { paddingValues ->
        LazyVerticalGrid(
            modifier = Modifier
                .padding(paddingValues)
                .padding(4.dp),
            columns = GridCells.Fixed(5)
        ) {
            items(state.gears) { gear ->
                /*val modifier = Modifier.clickable {
                    if (item.equipped) {
                        viewListener?.deEquipItemEvent(item.reference)
                    } else {
                        viewListener?.equipItemEvent(item.reference)
                    }
                }*/

                Box(Modifier.aspectRatio(1f)) {
                    val border = if (gear.isEquipped) BorderStroke(3.dp, Color.Yellow) else null
                    Surface(border = border) {
                        ImageWithCounter(
                            modifier = Modifier.padding(2.dp),
                            painter = painterResource(R.drawable.helm),
                            counter = gear.level
                        )
                    }
                }
            }
            items(state.reagents.toList()) { (reagentId, count) ->
                Box(Modifier.aspectRatio(1f)) {
                    Surface {
                        ImageWithCounter(
                            modifier = Modifier.padding(2.dp),
                            painter = painterResource(R.drawable.copper),
                            counter = count
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
            InventoryState(
                gears = listOf(Gear.EMPTY),
                reagents = mapOf(ReagentId.LINEN_CLOTH to 99),
            ),
            listener = InventoryListener.EMPTY
        )
    }
}