package ru.lemonapes.dungler.domain_models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import javax.annotation.concurrent.Immutable

@Serializable
@Immutable
data class Skill(
    @SerialName("skill_id")
    val skillId: SkillId,
    @SerialName("cooldown")
    val cooldown: Int,
) {
    companion object {
        val MOCK_WHIRLWIND
            get() = Skill(SkillId.WHIRLWIND, 5)
        val MOCK_HEROIC_STRIKE
            get() = Skill(SkillId.HEROIC_STRIKE, 3)
    }
}