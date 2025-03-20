package ru.lemonapes.dungler.network.endpoints

import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.isActive
import ru.lemonapes.dungler.Utils.Companion.log
import ru.lemonapes.dungler.network.ENDPOINT
import ru.lemonapes.dungler.network.HERO_ID
import ru.lemonapes.dungler.network.HttpClientProvider
import ru.lemonapes.dungler.network.models.ServerHeroStateResponse
import ru.lemonapes.dungler.network.models.checkNetworkException
import kotlin.coroutines.coroutineContext

suspend fun getHeroState(): ServerHeroStateResponse {
    val url = "$ENDPOINT/hero_state?hero_id=$HERO_ID"
    log("getHeroState ${coroutineContext.isActive}")
    val response = HttpClientProvider.client.get(url)
    log("HeroState got ${coroutineContext.isActive}")
    response.checkNetworkException()
    return response.body()
}