package com.logotet.bookapp.book.data.local

import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

actual class DatabaseFactory {
    actual fun create(): RoomDatabase.Builder<BookDatabase> {
        val os = System.getProperty(OS_PROPERTY_KEY).lowercase()
        val userHome = System.getProperty(USER_PROPERTY_KEY)
        val appDataDir = when {
            os.contains(WINDOWS_OS) -> File(System.getenv(WINDOWS_ENVIRONMENT), DB_FOLDER_NAME)
            os.contains(MAC_OS) -> File(userHome, MAC_OS_DB_PATH)
            else -> File(userHome, ELSE_OS_DB_PATH)
        }

        if (!appDataDir.exists()) {
            appDataDir.mkdirs()
        }

        val dbFile = File(appDataDir, BookDatabase.DB_NAME)
        return Room.databaseBuilder(dbFile.absolutePath)
    }

    companion object {
        private const val OS_PROPERTY_KEY = "os.name"
        private const val USER_PROPERTY_KEY = "user.home"
        private const val DB_FOLDER_NAME = "Books"
        private const val MAC_OS = "mac"
        private const val MAC_OS_DB_PATH = "Library/Application Support/${DB_FOLDER_NAME}"
        private const val ELSE_OS_DB_PATH = ".local/share/${DB_FOLDER_NAME}"
        private const val WINDOWS_OS = "win"
        private const val WINDOWS_ENVIRONMENT = "APPDATA"
    }
}