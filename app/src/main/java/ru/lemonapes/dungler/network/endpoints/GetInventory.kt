package ru.lemonapes.dungler.network.endpoints

import io.ktor.client.call.body
import io.ktor.client.request.get
import ru.lemonapes.dungler.network.ENDPOINT
import ru.lemonapes.dungler.network.HERO_ID
import ru.lemonapes.dungler.network.HttpClientProvider
import ru.lemonapes.dungler.network.models.InventoryResponse

suspend fun getInventory(): InventoryResponse {
    val url = "$ENDPOINT/inventory?hero_id=$HERO_ID"
    val response = HttpClientProvider.client.get(url)
    if (response.status.value != 200) throw Exception()
    return response.body()
}