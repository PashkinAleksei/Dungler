package ru.lemonapes.dungler.domain_models

class Skill(
    val skillId: SkillId,
    val cooldown: Int,
) {
    companion object {
        val MOCK_WHIRLWIND
            get() = Skill(SkillId.WHIRLWIND, 5)
        val MOCK_HEROIC_STRIKE
            get() = Skill(SkillId.HEROIC_STRIKE, 3)
    }
}