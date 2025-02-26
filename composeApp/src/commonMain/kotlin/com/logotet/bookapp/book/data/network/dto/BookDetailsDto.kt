package com.logotet.bookapp.book.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookDetailsDto(
    @SerialName("key")
    val key: String,
    @Serializable(with = BookDescriptionSerializer::class)
    val description: BookDescriptionDto? = null,
)

@Serializable
data class BookDescriptionDto(
    @SerialName("value")
    val value: String,
)