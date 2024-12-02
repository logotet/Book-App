package com.logotet.bookapp.android.book.data.network

import com.logotet.bookapp.android.book.data.network.dto.BookDetailsDto
import com.logotet.bookapp.android.book.data.network.dto.BookItemsDto
import com.logotet.bookapp.android.core.data.network.makeRequest
import com.logotet.bookapp.android.core.domain.result.DataError
import com.logotet.bookapp.android.core.domain.result.DataResult
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class KtorRemoteBookDataSource(
    private val httpClient: HttpClient
) : RemoteBookDataSource {
    override suspend fun searchBooks(
        query: String,
        resultLimit: Int?
    ): Flow<DataResult<BookItemsDto, DataError.Remote>> =
        makeRequest<BookItemsDto> {
            httpClient.get(
                urlString = "$BASE_URL/search.json"
            ) {
                parameter("q", query)
                parameter("limit", resultLimit)
                parameter("language", "eng")
                parameter(
                    "fields",
                    "key,title,author_name,author_key,cover_edition_key,cover_i,ratings_average,ratings_count,first_publish_year,language,number_of_pages_median,edition_count"
                )
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun getBookDetails(
        bookWorkId: String
    ): Flow<DataResult<BookDetailsDto, DataError.Remote>> =
        makeRequest<BookDetailsDto> {
            httpClient.get(
                urlString = "$BASE_URL/works/$bookWorkId.json"
            )
        }.flowOn(Dispatchers.IO)

    companion object {
        private const val BASE_URL = "https://openlibrary.org"
    }
}
