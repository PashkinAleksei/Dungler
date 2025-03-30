package ru.lemonapes.dungler.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.domain_models.FoodId

@Serializable
data class EquipmentFoodRequest(
    @SerialName("food")
    val food: FoodId,
) 