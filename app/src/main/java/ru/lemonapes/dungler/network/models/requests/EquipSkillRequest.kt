package ru.lemonapes.dungler.network.models.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.domain_models.SkillId
import ru.lemonapes.dungler.navigation.SkillSlot

@Serializable
data class EquipSkillRequest(
    val slot: SkillSlot,
    @SerialName("skill_id") val skillId: SkillId,
)
