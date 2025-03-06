package ru.lemonapes.dungler.network.endpoints

import io.ktor.client.call.body
import io.ktor.client.request.get
import ru.lemonapes.dungler.network.ENDPOINT
import ru.lemonapes.dungler.network.HERO_ID
import ru.lemonapes.dungler.network.HttpClientProvider
import ru.lemonapes.dungler.network.models.EquipmentResponse
import ru.lemonapes.dungler.network.models.checkNetworkException

suspend fun getEquipment(): EquipmentResponse {
    val url = "$ENDPOINT/equipment?hero_id=$HERO_ID"
    val response = HttpClientProvider.client.get(url)
    response.checkNetworkException()
    return response.body()
}
