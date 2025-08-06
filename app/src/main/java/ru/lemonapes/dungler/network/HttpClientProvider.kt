package ru.lemonapes.dungler.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy


@OptIn(ExperimentalSerializationApi::class)
object HttpClientProvider {

    private var _client: HttpClient? = null

    val client: HttpClient
        get() = _client ?: createClient().also { _client = it }

    private fun createClient(): HttpClient =
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    namingStrategy = JsonNamingStrategy.SnakeCase
                })
            }
        }

    fun close() {
        _client?.close()
        _client = null
    }
}
