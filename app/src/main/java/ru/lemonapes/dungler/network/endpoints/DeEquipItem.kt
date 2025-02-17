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

suspend fun deEquipItem(gearType: GearType): EquipmentResponse {
    val url = "$ENDPOINT/equipment?hero_id=$HERO_ID"
    val response = HttpClientProvider.client.patch(url) {
        contentType(ContentType.Application.Json)
        setBody(mapOf(gearType to null))
    }
    if (response.status.value != 200) throw Exception()
    return response.body()
}