package ru.lemonapes.dungler.network.models.actions.action_data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HeroDamageDataDto(
    @SerialName("hero_pure_damage") val heroPureDamage: Int,
    @SerialName("target_index") val targetIndex: Int,
)