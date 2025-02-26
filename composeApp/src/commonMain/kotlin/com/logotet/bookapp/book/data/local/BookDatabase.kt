package com.logotet.bookapp.book.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.logotet.bookapp.book.data.local.entity.BookEntity
import com.logotet.bookapp.book.data.local.entity.StringListTypeConverter

@Database(
    version = 1,
    entities = [BookEntity::class], exportSchema = true
)
@TypeConverters(
    StringListTypeConverter::class
)
@ConstructedBy(BookDatabaseConstructor::class)
abstract class BookDatabase: RoomDatabase() {
    abstract val bookDao: BookDao

    companion object {
        const val DB_NAME = "book.db"
    }
}