package ru.lemonapes.dungler.network.models.actions.action_data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HealEffectDto(
    @SerialName("heal_amount") val healAmount: Int,
)