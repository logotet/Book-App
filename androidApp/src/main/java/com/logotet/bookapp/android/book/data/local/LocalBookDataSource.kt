package com.logotet.bookapp.android.book.data.local

import com.logotet.bookapp.android.book.data.local.entity.BookEntity
import com.logotet.bookapp.android.core.domain.result.DataError
import com.logotet.bookapp.android.core.domain.result.DataResult
import kotlinx.coroutines.flow.Flow

interface LocalBookDataSource {
    suspend fun insertBook(book: BookEntity): Flow<DataResult<Unit, DataError.Local>>

    suspend fun deleteBook(book: BookEntity): Flow<DataResult<Unit, DataError.Local>>

    suspend fun getBookById(bookId: String): Flow<DataResult<BookEntity?, DataError.Local>>

    suspend fun getBooksByQuery(query: String): Flow<DataResult<List<BookEntity>, DataError.Local>>

    suspend fun getAllBooks(): Flow<DataResult<List<BookEntity>, DataError.Local>>

    suspend fun deleteAllBooks(): Flow<DataResult<Unit, DataError.Local>>
}