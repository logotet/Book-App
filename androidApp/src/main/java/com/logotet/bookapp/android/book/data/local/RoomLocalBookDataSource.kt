package com.logotet.bookapp.android.book.data.local

import com.logotet.bookapp.android.book.data.local.entity.BookEntity
import com.logotet.bookapp.android.core.domain.result.DataError
import com.logotet.bookapp.android.core.domain.result.DataResult
import com.logotet.bookapp.android.core.domain.result.DatabaseAction
import com.logotet.bookapp.android.core.domain.result.makeLocalRequest
import kotlinx.coroutines.flow.Flow

class RoomLocalBookDataSource(
    private val bookDao: BookDao
): LocalBookDataSource {
    companion object {
        private const val SUCCESSFUL_THRESHOLD = 0
    }

    override suspend fun insertBook(book: BookEntity): Flow<DataResult<Unit, DataError.Local>> =
        makeLocalRequest(databaseAction = DatabaseAction.INSERT) {
            val insertBookResult = bookDao.insertBook(book)

            val result = if (insertBookResult > SUCCESSFUL_THRESHOLD) {
                DataResult.Success(Unit)
            } else {
                DataResult.Error(DataError.Local.Insert(null))
            }

            result
        }

    override suspend fun deleteBook(bookId: String): Flow<DataResult<Unit, DataError.Local>> =
        makeLocalRequest(databaseAction = DatabaseAction.DELETE) {
            bookDao.deleteBookById(bookId)
            DataResult.Success(Unit)
        }

    override suspend fun getBookById(bookId: String): Flow<DataResult<BookEntity?, DataError.Local>> =
        makeLocalRequest(databaseAction = DatabaseAction.GET) {
            val book = bookDao.getBookById(bookId)
            book?.let {
                DataResult.Success(it)
            } ?: DataResult.Error(DataError.Local.GetData(null))
        }

    override suspend fun getBooksByQuery(query: String): Flow<DataResult<List<BookEntity>, DataError.Local>> =
        makeLocalRequest(databaseAction = DatabaseAction.GET) {
            val books = bookDao.getBooksByTittle(title = query)
            if (books.isNotEmpty()) {
                DataResult.Success(books)
            } else {
                DataResult.Error(DataError.Local.GetData(null))
            }
        }

    override suspend fun getAllBooks(): Flow<DataResult<List<BookEntity>, DataError.Local>> =
        makeLocalRequest(databaseAction = DatabaseAction.GET) {
            val books = bookDao.getAllBooks()
            if (books.isNotEmpty()) {
                DataResult.Success(books)
            } else {
                DataResult.Error(DataError.Local.GetData(null))
            }
        }

    override suspend fun deleteAllBooks(): Flow<DataResult<Unit, DataError.Local>> =
        makeLocalRequest(databaseAction = DatabaseAction.DELETE) {
            bookDao.deleteAllBooks()
            DataResult.Success(Unit)
        }
}

