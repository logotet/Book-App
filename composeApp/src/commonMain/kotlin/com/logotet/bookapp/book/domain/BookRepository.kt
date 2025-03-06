package com.logotet.bookapp.book.domain

import com.logotet.bookapp.book.domain.model.Book
import com.logotet.bookapp.book.domain.model.BookWithDetails
import com.logotet.bookapp.core.domain.result.AppError
import com.logotet.bookapp.core.domain.result.DataResult
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun getBooksList(query: String): Flow<DataResult<List<Book>, AppError>>

    suspend fun getBookDetails(bookId: String, isSaved: Boolean): Flow<DataResult<BookWithDetails, AppError>>

    suspend fun insertFavoriteBook(book: Book): Flow<DataResult<Unit, AppError>>

    suspend fun removeBookFromFavorites(bookId: String): Flow<DataResult<Unit, AppError>>

    suspend fun isFavoriteBook(bookId: String): Flow<DataResult<Boolean, AppError>>

    suspend fun getFavoriteBooksByTitle(title: String): Flow<DataResult<List<Book>, AppError>>

    suspend fun getAllFavoriteBooks(): Flow<DataResult<List<Book>, AppError>>
}