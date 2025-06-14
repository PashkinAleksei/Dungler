package ru.lemonapes.dungler.navigation.character.skills.skills_equipment

import ru.lemonapes.dungler.domain_models.SkillId
import ru.lemonapes.dungler.navigation.SkillSlot

data class DialogDescriptionData(
    val skillSlot: SkillSlot,
    val skill: SkillId,
) {
    companion object {
        val MOCK get() = DialogDescriptionData(SkillSlot.SKILL_SLOT_ONE, SkillId.WHIRLWIND)
    }
}