package com.logotet.bookapp.book.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val title: String,
    val imageUrl: String,
    val authors: List<String>,
    val languages: List<String>,
    val firstPublishYear: String?,
    val averageRating: String,
    val ratingCount: Int?,
    val numberOfPages: Int?,
    val numberOfEditions: Int
)
