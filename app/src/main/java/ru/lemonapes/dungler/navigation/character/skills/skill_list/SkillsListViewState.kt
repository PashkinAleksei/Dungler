package ru.lemonapes.dungler.navigation.character.skills.skill_list

import ru.lemonapes.dungler.domain_models.SkillId
import ru.lemonapes.dungler.parent_view_model.State

data class SkillsListViewState(
    val skillList: List<SkillId> = emptyList(),
    override val error: Throwable? = null,
    override val isLoading: Boolean = true,
) : State {
    companion object {
        val EMPTY get() = SkillsListViewState()
        val MOCK
            get() = SkillsListViewState(
                listOf(SkillId.WHIRLWIND, SkillId.HEROIC_STRIKE),
                isLoading = false
            )
    }
}
