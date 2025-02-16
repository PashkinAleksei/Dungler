package ru.lemonapes.dungler.ui.item_comparing_dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.window.DialogProperties
import ru.lemonapes.dungler.R
import ru.lemonapes.dungler.domain_models.GearType
import ru.lemonapes.dungler.ui.theme.DunglerTheme

@Composable
fun ItemComparingDialog(
    gearDescriptionDialogState: GearDescriptionDialogState,
    gearChooseClick: (gearType: GearType) -> Unit,
    onDismiss: () -> Unit,
) {
    Dialog(onDismissRequest = onDismiss, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        Box(Modifier.fillMaxWidth()) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                when {
                    gearDescriptionDialogState.status == GearDescriptionDialogStatus.EQUIPPED
                            && gearDescriptionDialogState.gear != null -> {
                        ItemDescriptionDialogView(gearDescriptionDialogState.gear, gearChooseClick)
                    }

                    gearDescriptionDialogState.status == GearDescriptionDialogStatus.INVENTORY -> {
                        InventoryDialogView(gearDescriptionDialogState.inventoryList)
                    }
                }
            }
            IconButton(modifier = Modifier.align(Alignment.TopEnd), onClick = onDismiss) {
                Icon(
                    modifier = Modifier.size(164.dp),
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
        ItemComparingDialog(
            gearDescriptionDialogState = GearDescriptionDialogState.DESCRIPTION_MOCK,
            {}, {})
    }
}

@Preview
@Composable
private fun InventoryDialogPreview() {
    DunglerTheme(darkTheme = true) {
        ItemComparingDialog(
            gearDescriptionDialogState = GearDescriptionDialogState.INVENTORY_MOCK_SMALL,
            {}, {})
    }
}
