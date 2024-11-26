package com.logotet.bookapp.android.book.data.network.mapper

import com.logotet.bookapp.android.book.data.network.dto.AuthorDetailsDto
import com.logotet.bookapp.android.book.data.network.dto.BookDetailsDto
import com.logotet.bookapp.android.book.data.network.dto.BookDto
import com.logotet.bookapp.android.book.data.network.dto.BookItemsDto
import com.logotet.bookapp.android.book.domain.model.Author
import com.logotet.bookapp.android.book.domain.model.AuthorDetails
import com.logotet.bookapp.android.book.domain.model.Book
import com.logotet.bookapp.android.book.domain.model.BookDetails
import com.logotet.bookapp.android.book.domain.model.Type
import java.time.LocalDate
import java.time.LocalDateTime

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
        description = null,
        languages = languages ?: emptyList(),
        firstPublishYear = firstPublishYear.toString(),
        averageRating = ratingsAverage,
        ratingCount = ratingsCount,
        numberOfPages = numberOfPagesMedian,
        numberOfEditions = numberOfEditions ?: EMPTY_NUMBER_OF_EDITIONS
    )

fun BookDetailsDto.toBookDetails(): BookDetails =
    BookDetails(
        title = title,
        authors = authors.toAuthorDetails(),
        covers = covers,
        created = LocalDateTime.parse(created.value),
        key = key,
        revision = revision,
        subjects = subjects,
        type = Type(type.key)
    )

private fun List<AuthorDetailsDto>.toAuthorDetails(): List<AuthorDetails> =
    map { authorDetails ->
        AuthorDetails(
            author = Author(authorDetails.author.key),
            type = Type(authorDetails.type.key)
        )
    }