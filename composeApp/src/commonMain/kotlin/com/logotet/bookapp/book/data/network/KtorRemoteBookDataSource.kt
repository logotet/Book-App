package com.logotet.bookapp.book.data.network

import com.logotet.bookapp.book.data.network.dto.BookDetailsDto
import com.logotet.bookapp.book.data.network.dto.BookItemsDto
import com.logotet.bookapp.core.data.network.KtorClient
import com.logotet.bookapp.core.data.network.makeRequest
import com.logotet.bookapp.core.domain.result.DataResult
import com.logotet.bookapp.core.domain.result.Remote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class KtorRemoteBookDataSource(
    private val ktorClient: KtorClient
) : RemoteBookDataSource {
    override suspend fun searchBooks(
        query: String,
        resultLimit: Int?
    ): Flow<DataResult<BookItemsDto, Remote>> =
        makeRequest<BookItemsDto> {
            ktorClient.get(
                path = "$BOOKS_LIST_PATH$JSON_FORMAT",
                parameters = arrayOf(
                    QUERY_KEY to query,
                    LIMIT_KEY to resultLimit.toString(),
                    LANGUAGE_KEY to ENGLISH_LANGUAGE_PARAM,
                    FIELDS_KEY to FIELDS_LIST
                )
            )
        }.flowOn(Dispatchers.IO)

    override suspend fun getBookDetails(
        bookWorkId: String
    ): Flow<DataResult<BookDetailsDto, Remote>> =
        makeRequest<BookDetailsDto> {
            ktorClient.get(
                path = "$BOOK_DETAILS_PATH$bookWorkId$JSON_FORMAT"
            )
        }.flowOn(Dispatchers.IO)

    companion object {
        private const val BOOK_DETAILS_PATH = "/works/"
        private const val BOOKS_LIST_PATH = "/search"
        private const val JSON_FORMAT = ".json"

        private const val QUERY_KEY = "q"
        private const val LIMIT_KEY = "limit"
        private const val LANGUAGE_KEY = "language"
        private const val FIELDS_KEY = "fields"
        private const val ENGLISH_LANGUAGE_PARAM = "eng"
        private const val FIELDS_LIST =
            "key," +
                    "title," +
                    "author_name," +
                    "author_key," +
                    "cover_edition_key," +
                    "cover_i," +
                    "ratings_average," +
                    "ratings_count," +
                    "first_publish_year," +
                    "language," +
                    "number_of_pages_median," +
                    "edition_count"
    }
}
