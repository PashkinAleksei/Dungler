package ru.lemonapes.dungler.mappers

import ru.lemonapes.dungler.domain_models.SelectedSkillData
import ru.lemonapes.dungler.domain_models.SkillsEquipment
import ru.lemonapes.dungler.network.models.SkillsEquipmentDto

object SkillsEquipmentMapper : (SkillsEquipmentDto?) -> SkillsEquipment {
    override fun invoke(dto: SkillsEquipmentDto?): SkillsEquipment {
        return dto?.let {
            val skillOne =
                it.skillOne?.let { skill -> SelectedSkillData(skill.skillId, skill.cooldown, skill.isActive) }
            val skillTwo =
                it.skillTwo?.let { skill -> SelectedSkillData(skill.skillId, skill.cooldown, skill.isActive) }
            SkillsEquipment(skillOne, skillTwo)
        } ?: SkillsEquipment.EMPTY
    }
}

