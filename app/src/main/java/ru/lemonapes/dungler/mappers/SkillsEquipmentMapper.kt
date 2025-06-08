package ru.lemonapes.dungler.mappers

import ru.lemonapes.dungler.domain_models.SkillData
import ru.lemonapes.dungler.domain_models.SkillsEquipment
import ru.lemonapes.dungler.network.models.SkillsEquipmentDto

object SkillsEquipmentMapper : (SkillsEquipmentDto?) -> SkillsEquipment {
    override fun invoke(dto: SkillsEquipmentDto?): SkillsEquipment {
        return dto?.let {
            val skillOne = it.skillOne?.let { skill -> SkillData(skill.skillId, skill.cooldown) }
            val skillTwo = it.skillTwo?.let { skill -> SkillData(skill.skillId, skill.cooldown) }
            SkillsEquipment(skillOne, skillTwo)
        } ?: SkillsEquipment.EMPTY
    }
}

