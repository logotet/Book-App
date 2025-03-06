package com.logotet.bookapp.book.data.local

import com.logotet.bookapp.book.data.local.entity.BookEntity
import com.logotet.bookapp.core.domain.result.DataResult
import com.logotet.bookapp.core.domain.result.Local
import kotlinx.coroutines.flow.Flow

interface LocalBookDataSource {
    suspend fun insertBook(book: BookEntity): Flow<DataResult<Unit, Local>>

    suspend fun deleteBook(bookId: String): Flow<DataResult<Unit, Local>>

    suspend fun getBookById(bookId: String): Flow<DataResult<BookEntity?, Local>>

    suspend fun getBooksByQuery(query: String): Flow<DataResult<List<BookEntity>, Local>>

    suspend fun getAllBooks(): Flow<DataResult<List<BookEntity>, Local>>

    suspend fun deleteAllBooks(): Flow<DataResult<Unit, Local>>
}