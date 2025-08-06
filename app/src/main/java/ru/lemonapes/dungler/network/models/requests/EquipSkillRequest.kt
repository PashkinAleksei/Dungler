package ru.lemonapes.dungler.network.models.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.domain_models.SkillId
import ru.lemonapes.dungler.navigation.SkillSlotNum

@Serializable
data class EquipSkillRequest(
    val slot: SkillSlotNum,
    @SerialName("skill_id") val skillId: SkillId,
)
