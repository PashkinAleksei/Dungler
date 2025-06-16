package ru.lemonapes.dungler.network.models.actions.action_data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EatingEffectDataDto(
    @SerialName("heal_amount") val healAmount: Int,
    @SerialName("reduce_food") val reduceFood: Boolean,
)