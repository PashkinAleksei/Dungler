package ru.lemonapes.dungler.network.endpoints

import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import ru.lemonapes.dungler.domain_models.GearId
import ru.lemonapes.dungler.network.ENDPOINT
import ru.lemonapes.dungler.network.HERO_ID
import ru.lemonapes.dungler.network.HttpClientProvider
import ru.lemonapes.dungler.network.models.CraftItemsResponse

private const val MAP_FIRST_PARAM = "gear_id"

suspend fun createItem(gearId: GearId): CraftItemsResponse {
    val url = "$ENDPOINT/craft_items?hero_id=$HERO_ID"
    val response = HttpClientProvider.client.post(url) {
        contentType(ContentType.Application.Json)
        setBody(mapOf(MAP_FIRST_PARAM to gearId))
    }
    if (response.status.value != 200) throw Exception()
    return response.body()
}

suspend fun upgradeItem(gearId: GearId): CraftItemsResponse {
    val url = "$ENDPOINT/upgrade_items?hero_id=$HERO_ID"
    val response = HttpClientProvider.client.post(url) {
        contentType(ContentType.Application.Json)
        setBody(mapOf(MAP_FIRST_PARAM to gearId))
    }
    if (response.status.value != 200) throw Exception()
    return response.body()
}