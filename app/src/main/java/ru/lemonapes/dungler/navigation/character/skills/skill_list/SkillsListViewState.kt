package ru.lemonapes.dungler.navigation.character.skills.skill_list

import ru.lemonapes.dungler.domain_models.Skill
import ru.lemonapes.dungler.parent_view_model.State

data class SkillsListViewState(
    val skillList: List<Skill> = emptyList(),
    override val error: Throwable? = null,
    override val isLoading: Boolean = true,
) : State {
    companion object {
        val EMPTY get() = SkillsListViewState()
        val MOCK
            get() = SkillsListViewState(
                listOf(Skill.MOCK_WHIRLWIND, Skill.MOCK_HEROIC_STRIKE),
                isLoading = false
            )
    }
}
