package ru.lemonapes.dungler.domain_models

data class SkillsEquipment(
    val skillOne: SelectedSkillData?,
    val skillTwo: SelectedSkillData?,
) {
    companion object {
        val EMPTY
            get() = SkillsEquipment(null, null)
        val MOCK
            get() = SkillsEquipment(
                SelectedSkillData(SkillId.WHIRLWIND, 0, true),
                SelectedSkillData(SkillId.HEROIC_STRIKE, 0, false),
            )
    }
}

data class SelectedSkillData(
    val skillId: SkillId,
    val cooldown: Int,
    val isActive: Boolean,
)