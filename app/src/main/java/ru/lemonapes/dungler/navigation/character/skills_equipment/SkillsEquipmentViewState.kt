package ru.lemonapes.dungler.navigation.character.skills_equipment

import ru.lemonapes.dungler.domain_models.SkillsEquipment
import ru.lemonapes.dungler.parent_view_model.State

data class SkillsEquipmentViewState(
    val skillsEquipment: SkillsEquipment? = null,
    override val error: Throwable? = null,
    override val isLoading: Boolean = true,
) : State {
    companion object {
        val EMPTY get() = SkillsEquipmentViewState()
        val MOCK
            get() = SkillsEquipmentViewState(
                SkillsEquipment.MOCK,
                isLoading = false
            )
    }
}
