package ru.lemonapes.dungler.network.models.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.domain_models.SkillId
import ru.lemonapes.dungler.navigation.character.SkillSlot

@Serializable
data class EquipSkillRequest(
    val slot: SkillSlot,
    @SerialName("skill") val skill: SkillId,
)
