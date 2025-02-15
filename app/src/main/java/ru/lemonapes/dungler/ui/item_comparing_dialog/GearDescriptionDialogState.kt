package ru.lemonapes.dungler.ui.item_comparing_dialog

import ru.lemonapes.dungler.domain_models.Gear

data class GearDescriptionDialogState(
    val gear: Gear?,
    val status: GearDescriptionDialogStatus,
    val isLoading: Boolean = true,
    val error: Throwable? = null,
) {
    companion object {
        val MOCK
            get() = GearDescriptionDialogState(
                gear = Gear.MOCK,
                status = GearDescriptionDialogStatus.EQUIPPED,
            )
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