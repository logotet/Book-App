package com.logotet.bookapp.android.book.data

import com.logotet.bookapp.android.book.data.local.RoomLocalBookDataSource
import com.logotet.bookapp.android.book.data.local.mapper.toBook
import com.logotet.bookapp.android.book.data.local.mapper.toBookEntity
import com.logotet.bookapp.android.book.data.network.RemoteBookDataSource
import com.logotet.bookapp.android.book.data.network.mapper.toBookDetails
import com.logotet.bookapp.android.book.data.network.mapper.toBookList
import com.logotet.bookapp.android.book.domain.BookRepository
import com.logotet.bookapp.android.book.domain.model.Book
import com.logotet.bookapp.android.book.domain.model.BookDetails
import com.logotet.bookapp.android.core.domain.result.DataError
import com.logotet.bookapp.android.core.domain.result.DataResult
import com.logotet.bookapp.android.core.domain.result.mapSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class DefaultBookRepository(
    private val remoteBookDataSource: RemoteBookDataSource,
    private val localBookDataSource: RoomLocalBookDataSource,
) : BookRepository {
    override suspend fun getBooksList(
        query: String
    ): Flow<DataResult<List<Book>, DataError>> =
        remoteBookDataSource.searchBooks(query)
            .mapSuccess { bookItemsDto ->
                bookItemsDto.toBookList().also {
                    it.forEach { b ->
                        insertFavoriteBook(b)
                    }
                }
            }
            .flowOn(Dispatchers.IO)

    override suspend fun getBookDetails(
        bookId: String
    ): Flow<DataResult<BookDetails, DataError>> =
        remoteBookDataSource.getBookDetails(bookId)
            .mapSuccess { bookDetailsDto ->
                bookDetailsDto.toBookDetails()
            }
            .flowOn(Dispatchers.IO)

    override suspend fun insertFavoriteBook(
        book: Book
    ): Flow<DataResult<Unit, DataError>> =
        localBookDataSource.insertBook(book.toBookEntity())


    override suspend fun removeBookFromFavorites(
        book: Book
    ): Flow<DataResult<Unit, DataError>> =
        localBookDataSource.deleteBook(book.toBookEntity())


    override suspend fun getFavoriteBookById(
        bookId: String
    ): Flow<DataResult<Book?, DataError>> =
        localBookDataSource.getBookById(bookId).mapSuccess { bookEntity ->
            bookEntity?.toBook()
        }

    override suspend fun getFavoriteBooksByTitle(title: String): Flow<DataResult<List<Book>, DataError>> =
        localBookDataSource.getBooksByQuery(query = title).mapSuccess { bookEntityList ->
            bookEntityList.map { bookEntity ->
                bookEntity.toBook()
            }
        }

    override suspend fun getAllFavoriteBooks(): Flow<DataResult<List<Book>, DataError>> =
        localBookDataSource.getAllBooks().mapSuccess { bookEntityList ->
            bookEntityList.map { bookEntity ->
                bookEntity.toBook()
            }
        }
}