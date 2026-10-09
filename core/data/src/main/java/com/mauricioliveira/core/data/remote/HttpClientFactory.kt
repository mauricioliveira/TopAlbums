package com.mauricioliveira.core.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private const val BASE_URL = "https://itunes.apple.com/"
private const val REQUEST_TIMEOUT_MS = 15_000L

internal val AppJson = Json { ignoreUnknownKeys = true }

internal fun createHttpClient(
    engine: HttpClientEngine = OkHttp.create(),
    enableLogging: Boolean = true,
): HttpClient = HttpClient(engine) {
    expectSuccess = true
    defaultRequest { url(BASE_URL) }

    install(ContentNegotiation) {
        json(AppJson)
        json(AppJson, contentType = ContentType.Text.JavaScript )
    }

    install(HttpTimeout) {
        requestTimeoutMillis = REQUEST_TIMEOUT_MS
    }

    if (enableLogging) {
        install(Logging) { level = LogLevel.INFO }
    }
}