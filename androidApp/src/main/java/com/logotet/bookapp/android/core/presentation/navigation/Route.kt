package com.logotet.bookapp.android.core.presentation.navigation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object BookList: Route

    @Serializable
    data class BookDetails(
        @SerialName(BOOK_ID)
        val bookId: String
    ): Route{
        companion object {
            const val BOOK_ID = "1234"
        }
    }
}