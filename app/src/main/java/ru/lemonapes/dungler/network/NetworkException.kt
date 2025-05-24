package ru.lemonapes.dungler.network

import ru.lemonapes.dungler.network.models.responses.ResponseErrorCode

class NetworkException(
    val errorCode: ResponseErrorCode,
    val errorMessage: String,
) : Exception()