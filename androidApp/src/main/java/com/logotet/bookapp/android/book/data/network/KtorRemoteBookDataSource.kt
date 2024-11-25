package com.logotet.bookapp.android.book.data.network

import com.logotet.bookapp.android.book.data.dto.BookDetailsDto
import com.logotet.bookapp.android.book.data.dto.BookItemsDto
import com.logotet.bookapp.android.core.domain.result.DataError
import com.logotet.bookapp.android.core.domain.result.DataResult
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class KtorRemoteBookDataSource(
    private val httpClient: HttpClient
) : RemoteBookDataSource {
    override suspend fun searchBooks(
        query: String,
        resultLimit: Int?
    ): DataResult<BookItemsDto, DataError.Remote> =
        makeRequest {
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
        }

    override suspend fun getBookDetails(
        bookWorkId: String
    ): DataResult<BookDetailsDto, DataError.Remote> =
        makeRequest<BookDetailsDto> {
            httpClient.get(
                urlString = "$BASE_URL/works/$bookWorkId.json"
            )
        }


    companion object {
        private const val BASE_URL = "https://openlibrary.org"
    }
}
