package ru.lemonapes.dungler.domain_models

data class SkillsEquipment(
    val skillOne: SkillData?,
    val skillTwo: SkillData?,
) {
    companion object {
        val EMPTY
            get() = SkillsEquipment(null, null)
        val MOCK
            get() = SkillsEquipment(
                SkillData(SkillId.WHIRLWIND, 0),
                SkillData(SkillId.HEROIC_STRIKE, 0),
            )
    }
}

data class SkillData(
    val skillId: SkillId,
    val cooldown: Int,
)