package com.logotet.bookapp.android.book.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

class BookDatabaseFactory(
    private val context: Context
) {
    fun create(): RoomDatabase.Builder<BookDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(BookDatabase.DB_NAME)

        return Room.databaseBuilder<BookDatabase>(
                context = appContext,
                name = dbFile.absolutePath
            ).fallbackToDestructiveMigration(true)
    }
}