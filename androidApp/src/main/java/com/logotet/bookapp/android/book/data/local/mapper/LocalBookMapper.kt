package com.logotet.bookapp.android.book.data.local.mapper

import com.logotet.bookapp.android.book.data.local.entity.BookEntity
import com.logotet.bookapp.android.book.domain.model.Book

fun Book.toBookEntity(): BookEntity =
    BookEntity(
        id = id,
        title = title,
        imageUrl = imageUrl,
        authors = authors,
        languages = languages,
        firstPublishYear = firstPublishYear,
        averageRating = averageRating,
        ratingCount = ratingCount,
        numberOfPages = numberOfPages,
        numberOfEditions = numberOfEditions
    )

fun BookEntity.toBook(): Book =
    Book(
        id = id,
        title = title,
        imageUrl = imageUrl,
        authors = authors,
        languages = languages,
        firstPublishYear = firstPublishYear,
        averageRating = averageRating,
        ratingCount = ratingCount,
        numberOfPages = numberOfPages,
        numberOfEditions = numberOfEditions
    )