package com.logotet.bookapp.android.book.domain

import com.logotet.bookapp.android.book.domain.model.Book
import com.logotet.bookapp.android.book.domain.model.BookWithDetails
import com.logotet.bookapp.android.core.domain.result.DataError
import com.logotet.bookapp.android.core.domain.result.DataResult
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun getBooksList(query: String): Flow<DataResult<List<Book>, DataError>>

    suspend fun getBookDetails(bookId: String, isSaved: Boolean): Flow<DataResult<BookWithDetails, DataError>>

    suspend fun insertFavoriteBook(book: Book): Flow<DataResult<Unit, DataError>>

    suspend fun removeBookFromFavorites(bookId: String): Flow<DataResult<Unit, DataError>>

    suspend fun isFavoriteBook(bookId: String): Flow<DataResult<Boolean, DataError>>

    suspend fun getFavoriteBooksByTitle(title: String): Flow<DataResult<List<Book>, DataError>>

    suspend fun getAllFavoriteBooks(): Flow<DataResult<List<Book>, DataError>>
}