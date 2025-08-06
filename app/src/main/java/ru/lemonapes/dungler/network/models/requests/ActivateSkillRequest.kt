package ru.lemonapes.dungler.network.models.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.navigation.SkillSlotNum

@Serializable
data class ActivateSkillRequest(
    val slot: SkillSlotNum,
    @SerialName("is_active") val isActive: Boolean,
)

