package ru.lemonapes.dungler.navigation.character.spell_equipment

import ru.lemonapes.dungler.parent_view_model.State

data class SpellEquipmentViewState(
    override val error: Throwable? = null,
    override val isLoading: Boolean = true,
) : State {
    companion object {
        val EMPTY get() = SpellEquipmentViewState()
        val MOCK
            get() = SpellEquipmentViewState(
                isLoading = false
            )
    }
}
