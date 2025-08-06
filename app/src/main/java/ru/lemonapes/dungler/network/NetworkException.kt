package ru.lemonapes.dungler.network

import io.ktor.http.HttpStatusCode
import ru.lemonapes.dungler.network.models.responses.ResponseErrorCode

data class NetworkException(
    val status: HttpStatusCode,
    val errorCode: ResponseErrorCode,
    val errorMessage: String,
) : Exception()