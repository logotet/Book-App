package com.logotet.bookapp.core.data.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType

class KtorClient(
    private val httpClient: HttpClient
) {
    suspend fun get(
        path: String = EMPTY_PATH,
        vararg parameters: Pair<String, String>
    ): HttpResponse {
        val url = BASE_URL.plus(path)

        return httpClient.get(url) {
            parameters.forEach { parameter ->
                val key = parameter.first
                val value = parameter.second
                parameter(key, value)
            }
        }
    }

    suspend fun <T> post(
        path: String = EMPTY_PATH,
        body: T,
        vararg parameters: Pair<String, String>
    ): HttpResponse {
        val url = BASE_URL.plus(path)
        val serializable =
            body as? kotlinx.serialization.Serializable ?: throw Exception()

        return httpClient.post(url) {
            parameters.forEach { parameter ->
                val key = parameter.first
                val value = parameter.second
                parameter(key, value)
            }

            contentType(ContentType.Application.Json)
            setBody(serializable)
        }
    }

    companion object {
        private const val BASE_URL = "https://openlibrary.org"
        private const val EMPTY_PATH = ""
    }
}