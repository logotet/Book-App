package com.logotet.bookapp.android.book.domain

import com.logotet.bookapp.android.book.domain.model.Book
import com.logotet.bookapp.android.book.domain.model.BookDetails
import com.logotet.bookapp.android.core.domain.result.DataError
import com.logotet.bookapp.android.core.domain.result.DataResult
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun getBooksList(query: String): Flow<DataResult<List<Book>, DataError.Remote>>

    suspend fun getBookDetails(bookId: String): Flow<DataResult<BookDetails, DataError.Remote>>
}