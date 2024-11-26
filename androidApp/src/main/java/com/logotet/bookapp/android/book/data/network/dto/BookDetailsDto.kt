package com.logotet.bookapp.android.book.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class BookDetailsDto(
    val description: String? = null
)