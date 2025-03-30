package ru.lemonapes.dungler.network.endpoints

import io.ktor.client.call.body
import io.ktor.client.request.patch
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import ru.lemonapes.dungler.domain_models.GearType
import ru.lemonapes.dungler.network.ENDPOINT
import ru.lemonapes.dungler.network.HERO_ID
import ru.lemonapes.dungler.network.HttpClientProvider
import ru.lemonapes.dungler.network.models.EquipmentResponse
import ru.lemonapes.dungler.network.models.checkNetworkException

suspend fun patchDeEquipItem(gearType: GearType): EquipmentResponse {
    val url = "$ENDPOINT/equipment_gear?hero_id=$HERO_ID"
    val response = HttpClientProvider.client.patch(url) {
        contentType(ContentType.Application.Json)
        setBody(mapOf(gearType to null))
    }
    response.checkNetworkException()
    return response.body()
}