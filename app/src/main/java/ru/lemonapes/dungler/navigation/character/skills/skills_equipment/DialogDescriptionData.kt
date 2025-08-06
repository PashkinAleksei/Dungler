package ru.lemonapes.dungler.navigation.character.skills.skills_equipment

import ru.lemonapes.dungler.domain_models.SkillId
import ru.lemonapes.dungler.navigation.SkillSlotNum

data class DialogDescriptionData(
    val skillSlot: SkillSlotNum,
    val skill: SkillId,
) {
    companion object {
        val MOCK get() = DialogDescriptionData(SkillSlotNum.SLOT_ONE, SkillId.WHIRLWIND)
    }
}