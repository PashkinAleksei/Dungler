package ru.lemonapes.dungler.network.endpoints

import io.ktor.client.call.body
import io.ktor.client.request.get
import ru.lemonapes.dungler.domain_models.GearType
import ru.lemonapes.dungler.network.ENDPOINT
import ru.lemonapes.dungler.network.HERO_ID
import ru.lemonapes.dungler.network.HttpClientProvider
import ru.lemonapes.dungler.network.models.responses.GearsToEquipResponse
import ru.lemonapes.dungler.network.models.responses.checkNetworkException

suspend fun getGearsToEquip(gearType: GearType): GearsToEquipResponse {
    val url = "$ENDPOINT/gears_to_equip?hero_id=$HERO_ID&gear_type=${gearType.serialName}"
    val response = HttpClientProvider.client.get(url)
    response.checkNetworkException()
    return response.body()
}