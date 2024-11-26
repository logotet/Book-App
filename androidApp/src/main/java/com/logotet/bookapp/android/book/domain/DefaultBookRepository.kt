package com.logotet.bookapp.android.book.domain

import com.logotet.bookapp.android.book.data.network.RemoteBookDataSource
import com.logotet.bookapp.android.book.data.network.mapper.mapSuccess
import com.logotet.bookapp.android.book.data.network.mapper.toBookList
import com.logotet.bookapp.android.book.domain.model.Book
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
}