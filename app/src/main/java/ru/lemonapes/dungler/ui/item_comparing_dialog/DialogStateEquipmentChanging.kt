package ru.lemonapes.dungler.ui.item_comparing_dialog

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import ru.lemonapes.dungler.domain_models.Gear

data class DialogEquipmentState(
    val status: DialogEquipmentStateStatus,
    val equippedGear: Gear? = null,
    val gearToCompare: Gear? = null,
    val inventoryList: ImmutableList<Gear> = persistentListOf(),
    val isLoading: Boolean = true,
    val error: Throwable? = null,
) {
    companion object {
        val DESCRIPTION_MOCK
            get() = DialogEquipmentState(
                equippedGear = Gear.MOCK_1,
                status = DialogEquipmentStateStatus.EQUIPPED,
            )
        val INVENTORY_MOCK_SMALL
            get() = DialogEquipmentState(
                inventoryList = persistentListOf(Gear.MOCK_1),
                status = DialogEquipmentStateStatus.INVENTORY,
            )
        val INVENTORY_MOCK_BIG: DialogEquipmentState
            get() {
                val gearList = arrayListOf<Gear>()
                for (i in 0..18) {
                    gearList.add(Gear.MOCK_1)
                }
                return DialogEquipmentState(
                    inventoryList = gearList.toPersistentList(),
                    status = DialogEquipmentStateStatus.INVENTORY,
                )
            }
        val COMPARING_MOCK: DialogEquipmentState
            get() {
                return DialogEquipmentState(
                    equippedGear = Gear.MOCK_1,
                    gearToCompare = Gear.MOCK_2,
                    status = DialogEquipmentStateStatus.COMPARING,
                )
            }
    }
}

enum class DialogEquipmentStateStatus() {
    EQUIPPED, INVENTORY, COMPARING
}