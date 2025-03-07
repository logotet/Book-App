package com.logotet.bookapp.book.data

import com.logotet.bookapp.book.data.local.LocalBookDataSource
import com.logotet.bookapp.book.data.local.mapper.toBook
import com.logotet.bookapp.book.data.local.mapper.toBookEntity
import com.logotet.bookapp.book.data.network.RemoteBookDataSource
import com.logotet.bookapp.book.data.network.mapper.toBookDetails
import com.logotet.bookapp.book.data.network.mapper.toBookList
import com.logotet.bookapp.book.domain.BookRepository
import com.logotet.bookapp.book.domain.model.Book
import com.logotet.bookapp.book.domain.model.BookWithDetails
import com.logotet.bookapp.core.domain.result.AppError
import com.logotet.bookapp.core.domain.result.DataResult
import com.logotet.bookapp.core.domain.result.mapSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn

class DefaultBookRepository(
    private val remoteBookDataSource: RemoteBookDataSource,
    private val localBookDataSource: LocalBookDataSource,
) : BookRepository {
    private val bookListRemoteCache = mutableListOf<Book>()
    private val bookListLocalCache = mutableListOf<Book>()

    override suspend fun getBooksList(
        query: String
    ): Flow<DataResult<List<Book>, AppError>> =
        if (query.isNotBlank()) {
            remoteBookDataSource.searchBooks(query)
                .mapSuccess { bookItemsDto ->
                    val bookList = bookItemsDto.toBookList()

                    bookListRemoteCache.clear()
                    bookListRemoteCache.addAll(bookList)

                    bookList
                }
        } else {
            flowOf(DataResult.Success(emptyList()))
        }
            .flowOn(Dispatchers.IO)

    override suspend fun getBookDetails(
        bookId: String,
        isSaved: Boolean
    ): Flow<DataResult<BookWithDetails, AppError>> =
        remoteBookDataSource.getBookDetails(bookId)
            .mapSuccess { bookDetailsDto ->
                val bookDetails = bookDetailsDto.toBookDetails()

                val bookListCache = if (isSaved) bookListLocalCache else bookListRemoteCache
                val book = bookListCache.first { book ->
                    book.id == bookId
                }

                BookWithDetails(book, bookDetails)
            }
            .flowOn(Dispatchers.IO)

    override suspend fun insertFavoriteBook(
        book: Book
    ): Flow<DataResult<Unit, AppError>> =
        localBookDataSource.insertBook(book.toBookEntity())
            .flowOn(Dispatchers.IO)

    override suspend fun removeBookFromFavorites(
        bookId: String
    ): Flow<DataResult<Unit, AppError>> =
        localBookDataSource.deleteBook(bookId)
            .flowOn(Dispatchers.IO)

    override suspend fun isFavoriteBook(
        bookId: String
    ): Flow<DataResult<Boolean, AppError>> =
        localBookDataSource.getBookById(bookId).mapSuccess { bookEntity ->
            bookEntity?.let { true } ?: false
        }.flowOn(Dispatchers.IO)

    override suspend fun getFavoriteBooksByTitle(title: String): Flow<DataResult<List<Book>, AppError>> =
        localBookDataSource.getBooksByQuery(query = title).mapSuccess { bookEntityList ->
            bookEntityList.map { bookEntity ->
                bookEntity.toBook()
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun getAllFavoriteBooks(): Flow<DataResult<List<Book>, AppError>> =
        localBookDataSource.getAllBooks().mapSuccess { bookEntityList ->
            val favoriteBooks = bookEntityList.map { bookEntity ->
                bookEntity.toBook()
            }

            bookListLocalCache.clear()
            bookListLocalCache.addAll(favoriteBooks)

            favoriteBooks
        }.flowOn(Dispatchers.IO)
}