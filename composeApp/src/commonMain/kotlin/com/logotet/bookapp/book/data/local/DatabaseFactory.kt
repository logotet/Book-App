package com.logotet.bookapp.book.data.local

import androidx.room.RoomDatabase

expect class DatabaseFactory {
    fun create(): RoomDatabase.Builder<BookDatabase>
}