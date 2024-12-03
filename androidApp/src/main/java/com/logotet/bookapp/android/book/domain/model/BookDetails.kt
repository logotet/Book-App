package com.logotet.bookapp.android.book.domain.model

data class BookDetails(
    val id: String,
    val title: String,
    val description: String?,
    val covers: List<Int>,
    val revision: Int,
    val subjects: List<String>
)

typealias BookWithDetails = Pair<Book, BookDetails>