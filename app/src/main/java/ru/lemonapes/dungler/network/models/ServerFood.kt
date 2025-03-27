package ru.lemonapes.dungler.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ServerFood(
    @SerialName("food_id")
    val id: String,
    @SerialName("health_regen_amount")
    val healthRegenAmount: Int,
    val count: Int,
) 