package com.logotet.bookapp.android.book.data

import com.logotet.bookapp.android.book.data.network.RemoteBookDataSource
import com.logotet.bookapp.android.book.data.network.mapper.toBookDetails
import com.logotet.bookapp.android.core.domain.result.mapSuccess
import com.logotet.bookapp.android.book.data.network.mapper.toBookList
import com.logotet.bookapp.android.book.domain.BookRepository
import com.logotet.bookapp.android.book.domain.model.Book
import com.logotet.bookapp.android.book.domain.model.BookDetails
import com.logotet.bookapp.android.core.domain.result.DataError
import com.logotet.bookapp.android.core.domain.result.DataResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class DefaultBookRepository(
    private val remoteBookDataSource: RemoteBookDataSource
) : BookRepository {
    override suspend fun getBooksList(
        query: String
    ): Flow<DataResult<List<Book>, DataError.Remote>> =
        remoteBookDataSource.searchBooks(query)
            .mapSuccess { bookItemsDto ->
                bookItemsDto.toBookList()
            }
            .flowOn(Dispatchers.IO)

    override suspend fun getBookDetails(
        bookId: String
    ): Flow<DataResult<BookDetails, DataError.Remote>> =
        remoteBookDataSource.getBookDetails(bookId)
            .mapSuccess { bookDetailsDto ->
                bookDetailsDto.toBookDetails()
            }
            .flowOn(Dispatchers.IO)
}