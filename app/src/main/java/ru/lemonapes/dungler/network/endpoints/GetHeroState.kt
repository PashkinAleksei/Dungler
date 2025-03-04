package ru.lemonapes.dungler.network.endpoints

import io.ktor.client.call.body
import io.ktor.client.request.get
import ru.lemonapes.dungler.network.ENDPOINT
import ru.lemonapes.dungler.network.HERO_ID
import ru.lemonapes.dungler.network.HttpClientProvider
import ru.lemonapes.dungler.network.models.HeroStateResponce

suspend fun getHeroState(): HeroStateResponce {
    val url = "$ENDPOINT/hero_state?hero_id=$HERO_ID"
    val response = HttpClientProvider.client.get(url)
    if (response.status.value != 200) throw Exception()
    return response.body()
}