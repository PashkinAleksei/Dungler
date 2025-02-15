package ru.lemonapes.dungler.navigation.craft

import androidx.annotation.StringRes
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
import kotlinx.collections.immutable.persistentMapOf
import ru.lemonapes.dungler.R
import ru.lemonapes.dungler.domain_models.CraftGear
import ru.lemonapes.dungler.domain_models.CreateGear
import ru.lemonapes.dungler.domain_models.ReagentId
import ru.lemonapes.dungler.domain_models.UpgradeGear
import ru.lemonapes.dungler.navigation.craft.CraftViewState.CraftSwitchState.CREATE
import ru.lemonapes.dungler.navigation.craft.CraftViewState.CraftSwitchState.UPGRADE
import ru.lemonapes.dungler.ui.SwitchButton
import ru.lemonapes.dungler.ui.SwitchState
import ru.lemonapes.dungler.ui.UIText
import ru.lemonapes.dungler.ui.theme.DunglerTheme
import ru.lemonapes.dungler.ui.theme.LocalThemeColors
import ru.lemonapes.dungler.ui.theme.typographies.LocalThemeTypographies

@Composable
fun CraftView(craftState: CraftViewState, craftListener: CraftListener) {
    //craftState.HandleError(viewEvent)
    Column {
        SwitchButton(
            modifier = Modifier.padding(16.dp),
            leftText = stringResource(id = R.string.craft_switch_create_button_text),
            rightText = stringResource(id = R.string.craft_switch_upgrade_button_text),
            selectedSegment = if (craftState.switchState == CREATE) {
                SwitchState.LEFT
            } else {
                SwitchState.RIGHT
            },
            onSelectionChanged = {
                when (it) {
                    SwitchState.LEFT -> craftListener.switchClick(CREATE)
                    SwitchState.RIGHT -> craftListener.switchClick(UPGRADE)
                }
            }
        )
        CraftGearView(craftState, craftListener)
    }
}

@Composable
fun CraftGearView(craftState: CraftViewState, craftListener: CraftListener) {
    val (craftItems, buttonText) = when (craftState.switchState) {
        CREATE -> Pair(craftState.createItems, R.string.craft_create_button_text)
        UPGRADE -> Pair(craftState.upgradeItems, R.string.craft_upgrade_button_text)
    }
    var selectedItemIndex by remember(craftState.switchState) { mutableIntStateOf(0) }
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
                craftList = craftItems,
            )
            CraftPanel(
                craftItem = craftItems[selectedItemIndex],
                reagentMap = craftState.reagents,
                craftItemFun = {
                    craftListener.craftItem(craftItems[selectedItemIndex])
                },
                buttonText = buttonText
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
    craftItem: CraftGear,
    reagentMap: Map<ReagentId, Int>,
    craftItemFun: () -> Unit,
    @StringRes buttonText: Int,
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
                UIText(
                    text = stringResource(buttonText),
                    textStyle = LocalThemeTypographies.current.regular18
                )
            }
        }
    }
}

@Composable
private fun ReagentItem(reagentId: ReagentId, countRequired: Int, countInBag: Int) {
    Column(modifier = Modifier.width(65.dp)) {
        Box(Modifier.fillMaxWidth()) {
            Image(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(62.dp)
                    .padding(3.dp),
                painter = painterResource(reagentId.image),
                contentDescription = stringResource(id = R.string.reagent_icon_description),
            )
        }
        UIText(
            modifier = Modifier.fillMaxWidth(),
            text = "$countInBag/${countRequired}",
            textAlign = TextAlign.Center,
            textStyle = LocalThemeTypographies.current.regular18,
            //color = itemBagCountTextColor,
        )
    }
}

@Composable
private fun CraftGear.CraftItemInfo() {
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
            Image(
                modifier = Modifier
                    .border(2.dp, Color.Gray)
                    .border(3.dp, Color.LightGray)
                    .size(120.dp)
                    .background(Color.Black)
                    .padding(4.dp),
                painter = painterResource(image),
                contentDescription = stringResource(id = R.string.gear_icon_description),
            )
        }
        val text = stringResource(gearId.gearName).run {
            if (this@CraftItemInfo is UpgradeGear) {
                this + stringResource(R.string.craft_panel_title_level, level + 1)
            } else {
                this
            }
        }
        UIText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            text = text,
            textStyle = LocalThemeTypographies.current.regular20,
            maxLines = 3
        )
    }
}

@Composable
private fun ReagentList(item: CraftGear, reagentMap: Map<ReagentId, Int>) {
    LazyVerticalGrid(
        GridCells.Fixed(3),
        Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        items(item.reagents.toList()) { item ->
            ReagentItem(
                reagentId = item.first,
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
    craftList: List<CraftGear>,
) {
    LazyColumn(modifier.fillMaxWidth()) {
        itemsIndexed(craftList) { index, item ->
            CraftListItemView(item, selectedItemIndex == index) {
                selectCraftItem(index)
            }
        }
    }
}

@Composable
private fun CraftListItemView(item: CraftGear, isSelected: Boolean, click: () -> Unit) {
    UIText(
        text = stringResource(item.gearId.gearName),
        color = if (isSelected) LocalThemeColors.current.positiveTextColor else LocalThemeColors.current.primaryTextColor,
        textStyle = LocalThemeTypographies.current.regular24,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { click() }
            .padding(horizontal = 8.dp)
            .padding(vertical = 4.dp)
    )
}

@Preview
@Composable
private fun CreateViewPreview() {
    DunglerTheme(darkTheme = true) {
        CraftView(
            craftState = CraftViewState(
                createItems = listOf(CreateGear.getMock()),
                upgradeItems = listOf(UpgradeGear.getMock()),
                reagents = persistentMapOf(ReagentId.COPPER to 20)
            ),
            craftListener = CraftListener.EMPTY
        )
    }
}

@Preview
@Composable
private fun UpgradeViewPreview() {
    DunglerTheme(darkTheme = true) {
        CraftView(
            craftState = CraftViewState(
                createItems = listOf(CreateGear.getMock()),
                upgradeItems = listOf(UpgradeGear.getMock()),
                switchState = UPGRADE,
                reagents = persistentMapOf(ReagentId.COPPER to 20)
            ),
            craftListener = CraftListener.EMPTY
        )
    }
}
