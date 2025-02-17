package ru.lemonapes.dungler.ui.item_comparison_dialog

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
import ru.lemonapes.dungler.navigation.character.EquipmentChangingDialogListener
import ru.lemonapes.dungler.ui.theme.DunglerTheme

@Composable
fun EquipmentChangingDialog(
    dialogEquipmentState: DialogEquipmentState,
    listener: EquipmentChangingDialogListener,
) {
    Dialog(
        onDismissRequest = listener.onGearDescriptionDialogDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(Modifier.fillMaxWidth()) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                when (dialogEquipmentState.status) {
                    DialogEquipmentStateStatus.EQUIPPED -> {
                        dialogEquipmentState.equippedGear?.let { gear ->
                            ItemDescriptionDialogView(gear, listener)
                        }
                    }

                    DialogEquipmentStateStatus.INVENTORY -> {
                        InventoryDialogView(state = dialogEquipmentState, listener = listener)
                    }

                    DialogEquipmentStateStatus.COMPARISON -> {
                        dialogEquipmentState.gearToCompare?.let { gearToCompare ->
                            DialogViewItemComparison(
                                gearToCompare = gearToCompare,
                                equippedGear = dialogEquipmentState.equippedGear,
                                listener = listener
                            )
                        }
                    }
                }
            }
            if (dialogEquipmentState.status == DialogEquipmentStateStatus.COMPARISON) {
                IconButton(
                    modifier = Modifier.align(Alignment.TopStart),
                    onClick = listener.backToInventoryClick
                ) {
                    Icon(
                        modifier = Modifier.size(64.dp),
                        painter = painterResource(R.drawable.ic_arrow_back),
                        tint = Color.Red,
                        contentDescription = stringResource(R.string.dismiss_dialog)
                    )
                }
            }
            IconButton(
                modifier = Modifier.align(Alignment.TopEnd),
                onClick = listener.onGearDescriptionDialogDismiss
            ) {
                Icon(
                    modifier = Modifier.size(64.dp),
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
private fun EquipmentChangingDialogDescriptionPreview() {
    DunglerTheme(darkTheme = true) {
        EquipmentChangingDialog(
            dialogEquipmentState = DialogEquipmentState.DESCRIPTION_MOCK,
            listener = EquipmentChangingDialogListener.EMPTY,
        )
    }
}

@Preview
@Composable
private fun EquipmentChangingDialogInventoryPreview() {
    DunglerTheme(darkTheme = true) {
        EquipmentChangingDialog(
            dialogEquipmentState = DialogEquipmentState.INVENTORY_MOCK_SMALL,
            listener = EquipmentChangingDialogListener.EMPTY,
        )
    }
}

@Preview
@Composable
private fun EquipmentChangingDialogComparisonPreview() {
    DunglerTheme(darkTheme = true) {
        EquipmentChangingDialog(
            dialogEquipmentState = DialogEquipmentState.COMPARISON_MOCK,
            listener = EquipmentChangingDialogListener.EMPTY,
        )
    }
}
