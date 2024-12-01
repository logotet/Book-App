package com.logotet.bookapp.android.book.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.logotet.bookapp.android.book.data.local.entity.BookEntity

@Database(
    version = 1,
    entities = [BookEntity::class], exportSchema = true
)
abstract class BookDatabase: RoomDatabase() {
    abstract val bookDao: BookDao

    companion object {
        const val DB_NAME = "book.db"
    }
}