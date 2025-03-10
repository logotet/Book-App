package com.logotet.bookapp.book.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.logotet.bookapp.book.data.local.entity.BookEntity

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: BookEntity): Long

    @Query("SELECT * FROM books")
    suspend fun getAllBooks(): List<BookEntity>

    @Query("SELECT * FROM books WHERE id = :id")
    suspend fun getBookById(id: String): BookEntity?

    @Query("SELECT * FROM books WHERE title LIKE '%' || :title || '%'")
    suspend fun getBooksByTittle(title: String): List<BookEntity>

    @Query("DELETE FROM books WHERE id = :id")
    suspend fun deleteBookById(id: String)

    @Query("DELETE FROM books")
    suspend fun deleteAllBooks()
}