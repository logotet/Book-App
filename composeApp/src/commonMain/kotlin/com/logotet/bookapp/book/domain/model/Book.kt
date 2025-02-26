package com.logotet.bookapp.book.domain.model

data class Book(
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