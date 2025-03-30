package ru.lemonapes.dungler.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.domain_models.FoodId

@Serializable
data class ServerFood(
    @SerialName("food_id")
    val id: FoodId,
    @SerialName("health_regen_amount")
    val healthRegenAmount: Int,
    val count: Int,
    @SerialName("is_equipped")
    val isEquipped: Boolean,
) 