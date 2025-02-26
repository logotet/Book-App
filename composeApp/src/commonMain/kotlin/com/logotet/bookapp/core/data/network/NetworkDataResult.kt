package com.logotet.bookapp.core.data.network

import com.logotet.bookapp.core.domain.result.DataError
import com.logotet.bookapp.core.domain.result.DataResult
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.JsonConvertException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.SerializationException

inline fun <reified Data> makeRequest(
    crossinline execute: suspend () -> HttpResponse
): Flow<DataResult<Data, DataError.Remote>> {
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

fun parseError(throwable: Throwable?): DataResult<Nothing, DataError.Remote> =
    DataResult.Error(
        when (throwable) {
            // for 3xx responses
            is RedirectResponseException -> DataError.Remote.Redirect(throwable)
            // for 4xx responses
            is ClientRequestException -> {
                when (throwable.response.status) {
                    HttpStatusCode.Unauthorized -> DataError.Remote.Unauthorized(throwable)
                    HttpStatusCode.Forbidden -> DataError.Remote.Forbidden(throwable)
                    HttpStatusCode.NotFound -> DataError.Remote.NotFound(throwable)
                    HttpStatusCode.MethodNotAllowed -> DataError.Remote.MethodNotAllowed(throwable)
                    else -> DataError.Remote.BadRequest(throwable)
                }
            }
            // for 5xx responses
            is ServerResponseException -> DataError.Remote.Server(throwable)
            // for deserialization errors
            is JsonConvertException, is SerializationException -> DataError.Remote.Serialization(
                throwable
            )
            // for timeout errors
            is HttpRequestTimeoutException -> DataError.Remote.Timeout(throwable)
            // for any other errors
            else -> DataError.Remote.Unknown(throwable)
        }
    )