package com.logotet.bookapp.android.book.domain.model

import java.time.LocalDateTime

data class BookDetails (
    val title: String,
    val authors: List<AuthorDetails>,
    val covers: List<Int>,
    val created: LocalDateTime,
    val key: String,
    val revision: Int,
    val subjects: List<String>,
    val type: Type
)

data class AuthorDetails(
    val author: Author,
    val type: Type
)

data class Type(
    val key: String
)

data class Author(
    val key: String
)