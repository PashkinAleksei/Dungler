package ru.lemonapes.dungler.network.models.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.navigation.SkillSlot

@Serializable
data class ActivateSkillRequest(
    val slot: SkillSlot,
    @SerialName("is_active") val isActive: Boolean,
)

