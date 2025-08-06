package ru.lemonapes.dungler.mappers

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import ru.lemonapes.dungler.domain_models.Skill
import ru.lemonapes.dungler.domain_models.actions.Action
import ru.lemonapes.dungler.hero_state.HeroState
import ru.lemonapes.dungler.network.models.responses.SkillsResponseDto

object SkillsResponseMapper : (SkillsResponseDto, Action?) -> Pair<ImmutableList<Skill>, HeroState> {
    override fun invoke(response: SkillsResponseDto, lastExecutedAction: Action?) =
        Pair(
            response.skills.toPersistentList(),
            HeroStateMapper(response.serverHeroState, lastExecutedAction)
        )
}