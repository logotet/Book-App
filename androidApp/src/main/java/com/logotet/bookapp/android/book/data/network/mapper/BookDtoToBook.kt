package com.logotet.bookapp.android.book.data.network.mapper

import com.logotet.bookapp.android.book.data.network.dto.BookDetailsDto
import com.logotet.bookapp.android.book.data.network.dto.BookDto
import com.logotet.bookapp.android.book.data.network.dto.BookItemsDto
import com.logotet.bookapp.android.book.domain.model.Book
import com.logotet.bookapp.android.book.domain.model.BookDetails

private const val COVER_URL = "https://covers.openlibrary.org/b/olid/"
private const val COVER_URL_ALTERNATIVE = "https://covers.openlibrary.org/b/olid/"
private const val COVER_URL_SUFFIX = "-L.jpg"
private const val EMPTY_NUMBER_OF_EDITIONS = 0

fun BookItemsDto.toBookList(): List<Book> =
    books.map { bookDto ->
        bookDto.toBook()
    }

fun BookDto.toBook(): Book =
    Book(
        id = id.substringAfterLast("/"),
        title = title,
        imageUrl = if (coverKey != null) {
            COVER_URL + coverKey + COVER_URL_SUFFIX
        } else {
            COVER_URL_ALTERNATIVE + coverAlternativeKey + COVER_URL_SUFFIX
        },
        authors = authorNames ?: emptyList(),
        languages = languages ?: emptyList(),
        firstPublishYear = firstPublishYear.toString(),
        averageRating = ratingsAverage,
        ratingCount = ratingsCount,
        numberOfPages = numberOfPagesMedian,
        numberOfEditions = numberOfEditions ?: EMPTY_NUMBER_OF_EDITIONS
    )

fun BookDetailsDto.toBookDetails(): BookDetails =
    BookDetails(
        id = key,
        description = description?.value,
    )