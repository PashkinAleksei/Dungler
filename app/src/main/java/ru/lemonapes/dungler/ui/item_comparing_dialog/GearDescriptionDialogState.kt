package ru.lemonapes.dungler.ui.item_comparing_dialog

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import ru.lemonapes.dungler.domain_models.Gear

data class GearDescriptionDialogState(
    val gear: Gear?,
    val inventoryList: ImmutableList<Gear> = persistentListOf(),
    val status: GearDescriptionDialogStatus,
    val isLoading: Boolean = true,
    val error: Throwable? = null,
) {
    companion object {
        val DESCRIPTION_MOCK
            get() = GearDescriptionDialogState(
                gear = Gear.MOCK,
                status = GearDescriptionDialogStatus.EQUIPPED,
            )
        val INVENTORY_MOCK_SMALL
            get() = GearDescriptionDialogState(
                gear = null,
                inventoryList = persistentListOf(Gear.MOCK),
                status = GearDescriptionDialogStatus.INVENTORY,
            )
        val INVENTORY_MOCK_BIG: GearDescriptionDialogState
            get() {
                val gearList = arrayListOf<Gear>()
                for (i in 0..18) {
                    gearList.add(Gear.MOCK)
                }
                return GearDescriptionDialogState(
                    gear = null,
                    inventoryList = gearList.toPersistentList(),
                    status = GearDescriptionDialogStatus.INVENTORY,
                )
            }
        val CLOSED
            get() = GearDescriptionDialogState(
                gear = null,
                status = GearDescriptionDialogStatus.CLOSED,
            )
    }
}

enum class GearDescriptionDialogStatus() {
    CLOSED, EQUIPPED, INVENTORY, COMPARING
}