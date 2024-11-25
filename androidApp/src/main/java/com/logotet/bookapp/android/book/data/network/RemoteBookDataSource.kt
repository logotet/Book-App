package com.logotet.bookapp.android.book.data.network

import com.logotet.bookapp.android.book.data.dto.BookDetailsDto
import com.logotet.bookapp.android.book.data.dto.BookItemsDto
import com.logotet.bookapp.android.core.domain.result.DataError
import com.logotet.bookapp.android.core.domain.result.DataResult

interface RemoteBookDataSource {
    suspend fun searchBooks(
        query: String,
        resultLimit: Int? = null
    ): DataResult<BookItemsDto, DataError.Remote>

    suspend fun getBookDetails(bookId: String): DataResult<BookDetailsDto, DataError.Remote>
}