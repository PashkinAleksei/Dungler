package ru.lemonapes.dungler.network.endpoints

import io.ktor.client.call.body
import io.ktor.client.request.get
import ru.lemonapes.dungler.network.ENDPOINT
import ru.lemonapes.dungler.network.HERO_ID
import ru.lemonapes.dungler.network.HttpClientProvider
import ru.lemonapes.dungler.network.models.CraftItemsResponse
import ru.lemonapes.dungler.network.models.checkNetworkException

suspend fun getCraftItems(): CraftItemsResponse {
    val url = "$ENDPOINT/craft_items?hero_id=$HERO_ID"
    val response = HttpClientProvider.client.get(url)
    response.checkNetworkException()
    return response.body()
}