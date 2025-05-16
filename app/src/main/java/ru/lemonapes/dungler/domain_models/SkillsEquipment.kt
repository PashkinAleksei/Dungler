package ru.lemonapes.dungler.domain_models

import kotlinx.serialization.Serializable

@Serializable
data class SkillsEquipment(
    val skillOne: SkillId?,
    val skillTwo: SkillId?,
) {
    companion object {
        val EMPTY
            get() = SkillsEquipment(null, null)
        val MOCK
            get() = SkillsEquipment(SkillId.WHIRLWIND, SkillId.HEROIC_STRIKE)
    }
}