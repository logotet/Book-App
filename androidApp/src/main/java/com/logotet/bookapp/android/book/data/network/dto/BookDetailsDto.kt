package com.logotet.bookapp.android.book.data.network.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName


@Serializable
data class BookDetailsDto(
    @SerialName("authors")
    val authors: List<AuthorDetailsDto>,
    @SerialName("covers")
    val covers: List<Int>,
    @SerialName("created")
    val created: Created,
    @SerialName("key")
    val key: String,
    @SerialName("last_modified")
    val lastModified: LastModified,
    @SerialName("latest_revision")
    val latestRevision: Int,
    @SerialName("revision")
    val revision: Int,
    @SerialName("subjects")
    val subjects: List<String>,
    @SerialName("title")
    val title: String,
    @SerialName("type")
    val type: Type
)

@Serializable
data class AuthorDetailsDto(
    @SerialName("author")
    val author: Author,
    @SerialName("type")
    val type: Type
)

@Serializable
data class Created(
    @SerialName("type")
    val type: String,
    @SerialName("value")
    val value: String
)

@Serializable
data class LastModified(
    @SerialName("type")
    val type: String,
    @SerialName("value")
    val value: String
)

@Serializable
data class Type(
    @SerialName("key")
    val key: String
)

@Serializable
data class Author(
    @SerialName("key")
    val key: String
)