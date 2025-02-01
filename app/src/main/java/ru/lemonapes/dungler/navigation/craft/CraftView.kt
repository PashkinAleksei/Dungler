package ru.lemonapes.dungler.navigation.craft

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import ru.lemonapes.dungler.R
import ru.lemonapes.dungler.navigation.models.CraftItem

@Composable
fun CraftView(craftState: CraftViewState) {
    var selectedItemIndex by remember { mutableIntStateOf(0) }
    if (craftState.items.isNotEmpty()) {
        Column(
            Modifier.fillMaxHeight()
        ) {
            CraftList(
                modifier = Modifier.weight(1f),
                selectedItemIndex = selectedItemIndex,
                selectCraftItem = { itemIndex -> selectedItemIndex = itemIndex },
                craftList = craftState.items,
            )
            CraftPanel(
                craftItem = craftState.items[selectedItemIndex],
                reagentMap = craftState.reagents,
                craftItemFun = {
                    //viewEvent(CraftViewEvent.CraftItemEvent(selectedItem.reference))
                },
            )
        }
    }

    //craftState.HandleError(viewEvent)
}

/*@Composable
private fun CraftViewState.HandleError(viewEvent: (CraftViewEvent) -> Unit) {
    error?.let { error ->
        if (error is CraftException) {
            val dismissRequest = { viewEvent(CraftViewEvent.ClearError) }
            Alert(
                onDismiss = dismissRequest,
                title = stringResource(id = error.title),
                text = stringResource(id = error.text),
                buttonText = stringResource(id = R.string.error_close),
            )
        } else {
            val dismissRequest = { viewEvent(CraftViewEvent.ClearError) }
            Alert(
                onDismiss = dismissRequest,
                title = stringResource(id = R.string.error_common_title),
                text = stringResource(id = R.string.error_common_text),
                buttonText = stringResource(id = R.string.error_close),
            )
        }
    }
}*/

@Composable
private fun CraftPanel(
    craftItem: CraftItem,
    reagentMap: Map<String, Int>,
    craftItemFun: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        //color = secondaryBackgroundColor,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp)
        ) {
            craftItem.CraftItemInfo()
            ReagentList(craftItem, reagentMap)
            Button(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                onClick = { craftItemFun() }) {
                Text(text = stringResource(id = R.string.craft_create_button_text))
            }
        }
    }
}

@Composable
private fun ReagentItem(reagentName: String, countRequired: Int, countInBag: Int) {
    Column(modifier = Modifier.width(65.dp)) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(62.dp)
                .padding(3.dp),
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = stringResource(id = R.string.reagent_icon_description),
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "$countInBag/${countRequired}",
            textAlign = TextAlign.Center,
            //color = itemBagCountTextColor,
            fontSize = 20.sp

        )
    }
}

@Composable
private fun CraftItem.CraftItemInfo() {
    Row(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = 7.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier.wrapContentSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            /*if (countOrLevel > 0) {
                CraftImageWithCounter(modifier = Modifier.size(75.dp))
            } else {*/
            Image(
                modifier = Modifier.size(75.dp),
                painter = painterResource(R.drawable.shield_disabled),
                contentDescription = null
            )
            //}
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp), text = gearId,
            fontSize = 22.sp,
            //color = primaryTextColor,
            maxLines = 3
        )
    }
}

@Composable
private fun ReagentList(item: CraftItem, reagentMap: Map<String, Int>) {
    LazyVerticalGrid(
        GridCells.Fixed(3),
        Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        items(item.reagents.toList()) { item ->
            ReagentItem(
                reagentName = item.first,
                countRequired = item.second,
                countInBag = reagentMap[item.first] ?: 0
            )
        }
    }
}

@Composable
private fun CraftList(
    modifier: Modifier,
    selectedItemIndex: Int,
    selectCraftItem: (index: Int) -> Unit,
    craftList: List<CraftItem>,
) {
    LazyColumn(modifier.fillMaxWidth()) {
        itemsIndexed(craftList) { index, item ->
            CraftCardView(item, selectedItemIndex == index) {
                selectCraftItem(index)
            }
        }
    }
}

@Composable
private fun CraftCardView(item: CraftItem, isSelected: Boolean, click: () -> Unit) {
    Text(
        text = item.gearId,
        color = if (isSelected) Color.Red else Color.Green,//MaterialTheme.colors.secondary else MaterialTheme.colors.onPrimary,
        fontSize = 24.sp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { click() }
            .padding(horizontal = 8.dp)
            .padding(vertical = 4.dp)
    )
}

@Preview
@Composable
private fun CraftViewPreview() {
    CraftView(
        craftState = CraftViewState.getEmpty(),
    )
}
