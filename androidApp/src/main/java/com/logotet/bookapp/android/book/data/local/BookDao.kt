package com.logotet.bookapp.android.book.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.logotet.bookapp.android.book.data.local.entity.BookEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: BookEntity)

    @Query("SELECT * FROM books")
    fun getAllBooks(): Flow<List<BookEntity>>

    @Query("SELECT * FROM books WHERE id = :id")
    fun getBookById(id: String): Flow<BookEntity?>

    @Query("DELETE FROM books WHERE id = :id")
    suspend fun deleteBookById(id: String)

    @Query("DELETE FROM books")
    suspend fun deleteAllBooks()
}