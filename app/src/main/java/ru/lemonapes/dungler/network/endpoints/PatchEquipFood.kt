package ru.lemonapes.dungler.network.endpoints

import io.ktor.client.call.body
import io.ktor.client.request.patch
import io.ktor.client.request.setBody
import ru.lemonapes.dungler.domain_models.FoodId
import ru.lemonapes.dungler.network.ENDPOINT
import ru.lemonapes.dungler.network.HERO_ID
import ru.lemonapes.dungler.network.HttpClientProvider
import ru.lemonapes.dungler.network.models.EquipmentResponse
import ru.lemonapes.dungler.network.models.checkNetworkException

suspend fun patchEquipFood(foodId: FoodId): EquipmentResponse {
    val url = "$ENDPOINT/equipment_food?hero_id=$HERO_ID"
    val response = HttpClientProvider.client.patch(url) {
        setBody(mapOf("food_id" to foodId))
    }
    response.checkNetworkException()
    return response.body()
} 