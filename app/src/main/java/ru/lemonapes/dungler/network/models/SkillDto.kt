package ru.lemonapes.dungler.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.domain_models.SkillId

@Serializable
class SkillDto(
    @SerialName("skill_id") val skillId: SkillId,
    @SerialName("cooldown") val cooldown: Int,
)