package ru.lemonapes.dungler.mappers

import ru.lemonapes.dungler.domain_models.Skill
import ru.lemonapes.dungler.network.models.SkillDto

object SkillsMapper : (SkillDto) -> Skill {
    override fun invoke(skillDto: SkillDto) = Skill(skillDto.skillId, skillDto.cooldown)
}
