package ru.lemonapes.dungler.navigation.craft

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.lemonapes.dungler.R
import ru.lemonapes.dungler.navigation.domain_models.DomainCraftItem
import ru.lemonapes.dungler.navigation.domain_models.DomainCreateItem
import ru.lemonapes.dungler.network.IMAGES_REAGENTS_PATH
import ru.lemonapes.dungler.ui.ImageView
import ru.lemonapes.dungler.ui.SegmentedSwitch
import ru.lemonapes.dungler.ui.SwitchSegment
import ru.lemonapes.dungler.ui.theme.DunglerTheme
import ru.lemonapes.dungler.ui.theme.LocalThemeColors

@Composable
fun CraftView(craftState: CraftViewState, craftListener: CraftListener) {
    //craftState.HandleError(viewEvent)
    Column {
        SegmentedSwitch(
            modifier = Modifier.padding(16.dp),
            leftText = stringResource(id = R.string.craft_switch_create_button_text),
            rightText = stringResource(id = R.string.craft_switch_upgrade_button_text),
            selectedSegment = if (craftState.switchState == CraftViewState.CraftSwitchState.CREATE) {
                SwitchSegment.LEFT
            } else {
                SwitchSegment.RIGHT
            },
            onSelectionChanged = {
                when (it) {
                    SwitchSegment.LEFT -> craftListener.switchClick(CraftViewState.CraftSwitchState.CREATE)
                    SwitchSegment.RIGHT -> craftListener.switchClick(CraftViewState.CraftSwitchState.UPGRADE)
                }
            }
        )
        when (craftState.switchState) {
            CraftViewState.CraftSwitchState.CREATE -> CreateGearView(craftState)
            CraftViewState.CraftSwitchState.UPGRADE -> UpgradeGearView(craftState)
        }
    }
}

@Composable
fun CreateGearView(craftState: CraftViewState) {
    var selectedItemIndex by remember { mutableIntStateOf(0) }
    if (craftState.createItems.isNotEmpty()) {
        Column(
            Modifier.fillMaxHeight()
        ) {
            CraftList(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .weight(1f),
                selectedItemIndex = selectedItemIndex,
                selectCraftItem = { itemIndex -> selectedItemIndex = itemIndex },
                craftList = craftState.createItems,
            )
            CraftPanel(
                craftItem = craftState.createItems[selectedItemIndex],
                reagentMap = craftState.reagents,
                craftItemFun = {

                },
            )
        }
    }
}

@Composable
fun UpgradeGearView(craftState: CraftViewState) {
    var selectedItemIndex by remember { mutableIntStateOf(0) }
    if (craftState.createItems.isNotEmpty()) {
        Column(
            Modifier.fillMaxHeight()
        ) {
            CraftList(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .weight(1f),
                selectedItemIndex = selectedItemIndex,
                selectCraftItem = { itemIndex -> selectedItemIndex = itemIndex },
                craftList = craftState.upgradeItems,
            )
            CraftPanel(
                craftItem = craftState.upgradeItems[selectedItemIndex],
                reagentMap = craftState.reagents,
                craftItemFun = {

                },
            )
        }
    }
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
    craftItem: DomainCraftItem,
    reagentMap: Map<String, Int>,
    craftItemFun: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
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
        ImageView(
            modifier = Modifier
                .fillMaxWidth()
                .height(62.dp)
                .padding(3.dp),
            url = IMAGES_REAGENTS_PATH + reagentName,
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
private fun DomainCraftItem.CraftItemInfo() {
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
                modifier = Modifier
                    .border(2.dp, Color.Gray)
                    .border(3.dp, Color.LightGray)
                    .size(120.dp)
                    .background(Color.Black)
                    .padding(4.dp),
                painter = painterResource(gearData.image),
                contentDescription = stringResource(id = R.string.gear_icon_description),
            )
            //}
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            text = stringResource(gearData.name),
            fontSize = 22.sp,
            //color = primaryTextColor,
            maxLines = 3
        )
    }
}

@Composable
private fun ReagentList(item: DomainCraftItem, reagentMap: Map<String, Int>) {
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
    craftList: List<DomainCraftItem>,
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
private fun CraftCardView(item: DomainCraftItem, isSelected: Boolean, click: () -> Unit) {
    Text(
        text = stringResource(item.gearData.name),
        color = if (isSelected) LocalThemeColors.current.positiveTextColor else MaterialTheme.colorScheme.onPrimary,
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
    DunglerTheme(darkTheme = true) {
        CraftView(
            craftState = CraftViewState(
                createItems = listOf(DomainCreateItem.getMock()),
                reagents = mapOf("copper" to 20)
            ),
            craftListener = CraftListener.EMPTY
        )
    }
}
