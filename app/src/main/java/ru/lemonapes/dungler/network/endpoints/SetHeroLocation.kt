package ru.lemonapes.dungler.network.endpoints

import io.ktor.client.call.body
import io.ktor.client.request.patch
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import ru.lemonapes.dungler.network.ENDPOINT
import ru.lemonapes.dungler.network.HERO_ID
import ru.lemonapes.dungler.network.HttpClientProvider
import ru.lemonapes.dungler.network.models.ServerHeroStateResponse
import ru.lemonapes.dungler.network.models.responses.checkNetworkException

suspend fun setHeroLocation(dungeonId: String): ServerHeroStateResponse {
    val url = "$ENDPOINT/set_hero_location?hero_id=$HERO_ID"
    val response = HttpClientProvider.client.patch(url) {
        contentType(ContentType.Application.Json)
        setBody(mapOf("dungeon_id" to dungeonId))
    }
    response.checkNetworkException()
    return response.body()
}