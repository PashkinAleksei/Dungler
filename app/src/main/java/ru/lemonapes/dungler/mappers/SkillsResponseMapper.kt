package ru.lemonapes.dungler.mappers

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import ru.lemonapes.dungler.domain_models.Skill
import ru.lemonapes.dungler.hero_state.HeroState
import ru.lemonapes.dungler.network.models.responses.SkillsResponse

object SkillsResponseMapper : (SkillsResponse) -> SkillsMapperResponse {
    override fun invoke(response: SkillsResponse) =
        SkillsMapperResponse(
            skills = response.skills.map(SkillsMapper).toPersistentList(),
            heroState = HeroStateMapper(response.serverHeroState)
        )
}

data class SkillsMapperResponse(
    val skills: ImmutableList<Skill>,
    val heroState: HeroState,
)
