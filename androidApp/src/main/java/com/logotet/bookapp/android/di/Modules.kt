package com.logotet.bookapp.android.di

import android.util.Log
import com.logotet.bookapp.android.book.data.network.KtorRemoteBookDataSource
import com.logotet.bookapp.android.book.data.network.RemoteBookDataSource
import com.logotet.bookapp.android.book.domain.BookRepository
import com.logotet.bookapp.android.book.domain.DefaultBookRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private const val HTTP_LOGGER_TAG = "KTOR"
private const val TIMEOUT_MILLIS = 30000L

val appModule = module {
    singleOf(::DefaultBookRepository).bind<BookRepository>()
}

val networkModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        explicitNulls = false
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.i(HTTP_LOGGER_TAG, message)
                    }
                }

                level = LogLevel.ALL
            }

            install(HttpTimeout) {
                connectTimeoutMillis = TIMEOUT_MILLIS
                socketTimeoutMillis = TIMEOUT_MILLIS
                requestTimeoutMillis = TIMEOUT_MILLIS
            }

            expectSuccess = true // throw exception when status code is not >=200 && <300
        }
    }

    singleOf(::KtorRemoteBookDataSource).bind<RemoteBookDataSource>()
}
