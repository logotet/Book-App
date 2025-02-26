package com.logotet.bookapp.book.domain.model

data class BookDetails(
    val id: String,
    val description: String?,
)

typealias BookWithDetails = Pair<Book, BookDetails>