package ru.lemonapes.dungler.network.endpoints

import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import ru.lemonapes.dungler.domain_models.FoodId
import ru.lemonapes.dungler.domain_models.GearId
import ru.lemonapes.dungler.network.ENDPOINT
import ru.lemonapes.dungler.network.HERO_ID
import ru.lemonapes.dungler.network.HttpClientProvider
import ru.lemonapes.dungler.network.models.CraftItemsResponse
import ru.lemonapes.dungler.network.models.checkNetworkException

private const val GEAR_ID_PARAM = "gear_id"
private const val FOOD_ID_PARAM = "food_id"

suspend fun postCreateItem(gearId: GearId): CraftItemsResponse {
    val url = "$ENDPOINT/craft_items?hero_id=$HERO_ID"
    val response = HttpClientProvider.client.post(url) {
        contentType(ContentType.Application.Json)
        setBody(mapOf(GEAR_ID_PARAM to gearId))
    }
    response.checkNetworkException()
    return response.body()
}

suspend fun postUpgradeItem(gearId: GearId): CraftItemsResponse {
    val url = "$ENDPOINT/upgrade_items?hero_id=$HERO_ID"
    val response = HttpClientProvider.client.post(url) {
        contentType(ContentType.Application.Json)
        setBody(mapOf(GEAR_ID_PARAM to gearId))
    }
    response.checkNetworkException()
    return response.body()
}

suspend fun postCreateFood(foodId: FoodId): CraftItemsResponse {
    val url = "$ENDPOINT/create_food?hero_id=$HERO_ID"
    val response = HttpClientProvider.client.post(url) {
        contentType(ContentType.Application.Json)
        setBody(mapOf(FOOD_ID_PARAM to foodId))
    }
    response.checkNetworkException()
    return response.body()
}