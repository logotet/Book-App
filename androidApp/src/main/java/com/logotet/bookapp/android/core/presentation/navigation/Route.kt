package com.logotet.bookapp.android.core.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object BookList: Route

    @Serializable
    data class BookDetails(val bookId: String): Route
}