package com.logotet.bookapp.book.data.network.dto

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonPrimitive

object BookDescriptionSerializer : KSerializer<BookDescriptionDto?> {
    private const val SERIAL_NAME = "BookDescription"
    private const val VALUE_KEY = "value"
    private const val ILLEGAL_STATE_MESSAGE = "This serializer can only be used with Json"

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(SERIAL_NAME, PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, bookDescription: BookDescriptionDto?) {
        if (bookDescription != null) {
            encoder.encodeString(bookDescription.value)
        }
    }

    override fun deserialize(decoder: Decoder): BookDescriptionDto? {
        val input = decoder as? kotlinx.serialization.json.JsonDecoder
            ?: throw IllegalStateException(ILLEGAL_STATE_MESSAGE)

        return when (val jsonElement = input.decodeJsonElement()) {
            is JsonPrimitive -> {
                BookDescriptionDto(value = jsonElement.content)
            }

            is JsonObject -> {
                val value = jsonElement[VALUE_KEY]?.jsonPrimitive?.content
                BookDescriptionDto(value = value ?: "")
            }

            else -> null
        }
    }
}