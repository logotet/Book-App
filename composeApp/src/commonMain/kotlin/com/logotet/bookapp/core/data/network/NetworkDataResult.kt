package com.logotet.bookapp.core.data.network

import com.logotet.bookapp.core.domain.result.DataResult
import com.logotet.bookapp.core.domain.result.Remote
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.JsonConvertException
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.SerializationException
import kotlin.coroutines.coroutineContext

inline fun <reified Data> makeRequest(
    crossinline execute: suspend () -> HttpResponse
): Flow<DataResult<Data, Remote>> {
    return flow {
        val result = try {
            val response = execute()
            val body = response.body<Data>()

            DataResult.Success(body)
        } catch (throwable: Throwable) {
            parseError(throwable)
        }

        emit(result)
    }
}

suspend fun parseError(throwable: Throwable?): DataResult<Nothing, Remote> =
    DataResult.Error(
        when (throwable) {
            // for 3xx responses
            is RedirectResponseException -> Remote.Redirect
            // for 4xx responses
            is ClientRequestException -> {
                when (throwable.response.status) {
                    HttpStatusCode.Unauthorized -> Remote.Unauthorized
                    HttpStatusCode.Forbidden -> Remote.Forbidden
                    HttpStatusCode.NotFound -> Remote.NotFound
                    HttpStatusCode.MethodNotAllowed -> Remote.MethodNotAllowed
                    else -> Remote.BadRequest
                }
            }
            // for 5xx responses
            is ServerResponseException -> Remote.Server
            // for deserialization errors
            is JsonConvertException, is SerializationException -> Remote.Serialization
            // for timeout errors
            is HttpRequestTimeoutException -> Remote.Timeout
            // for any other errors
            else -> {
                coroutineContext.ensureActive()
                Remote.Unknown
            }
        }
    )