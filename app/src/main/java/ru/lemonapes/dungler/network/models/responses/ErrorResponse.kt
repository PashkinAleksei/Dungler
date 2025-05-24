package ru.lemonapes.dungler.network.models.responses

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode.Companion.OK
import kotlinx.serialization.Serializable
import ru.lemonapes.dungler.network.NetworkException

@Serializable
data class ErrorResponse(
    val code: ResponseErrorCode = ResponseErrorCode.UNKNOWN_ERROR,
    val message: String = "",
)

enum class ResponseErrorCode {
    HERO_IN_A_DUNGEON,
    INTERNAL_ERROR,
    INVALID_REQUEST,
    UNPROCESSABLE_REQUEST,

    NOT_ENOUGH_REAGENTS,

    UNKNOWN_ERROR,
}

suspend fun HttpResponse.checkNetworkException() {
    if (status != OK) {
        val body = body<ErrorResponse>()
        throw NetworkException(body.code, body.message)
    }
}