package ru.lemonapes.dungler.network.endpoints

import io.ktor.client.call.body
import io.ktor.client.request.patch
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import ru.lemonapes.dungler.navigation.SkillSlot
import ru.lemonapes.dungler.network.ENDPOINT
import ru.lemonapes.dungler.network.HERO_ID
import ru.lemonapes.dungler.network.HttpClientProvider
import ru.lemonapes.dungler.network.models.ServerHeroStateResponse
import ru.lemonapes.dungler.network.models.requests.ActivateSkillRequest
import ru.lemonapes.dungler.network.models.responses.checkNetworkException

suspend fun patchActivateSkill(slot: SkillSlot, isActive: Boolean): ServerHeroStateResponse {
    val url = "$ENDPOINT/activate_skill?hero_id=$HERO_ID"
    val response = HttpClientProvider.client.patch(url) {
        contentType(ContentType.Application.Json)
        setBody(ActivateSkillRequest(slot, isActive))
    }
    response.checkNetworkException()
    return response.body()
}

