package com.logotet.bookapp.book.data.network

import com.logotet.bookapp.book.data.network.dto.BookDetailsDto
import com.logotet.bookapp.book.data.network.dto.BookItemsDto
import com.logotet.bookapp.core.domain.result.DataError
import com.logotet.bookapp.core.domain.result.DataResult
import kotlinx.coroutines.flow.Flow

interface RemoteBookDataSource {
    suspend fun searchBooks(
        query: String,
        resultLimit: Int? = null
    ): Flow<DataResult<BookItemsDto, DataError.Remote>>

    suspend fun getBookDetails(bookWorkId: String): Flow<DataResult<BookDetailsDto, DataError.Remote>>
}