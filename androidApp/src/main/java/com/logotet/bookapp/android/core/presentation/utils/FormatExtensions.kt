package com.logotet.bookapp.android.core.presentation.utils

import java.util.Locale

private const val ONE_DECIMAL_PLACE_FORMAT = "%.1f"
private const val EMPTY_VALUE = " - "
private const val COMMA_SEPARATOR = ", "

fun Double?.formatToSecondDigit(): String =
    this?.let {
        String.format(
            Locale.getDefault(),
            ONE_DECIMAL_PLACE_FORMAT,
            this
        )
    } ?: EMPTY_VALUE

fun List<String>?.asCommaSeparatedString(): String =
    this?.joinToString(COMMA_SEPARATOR) ?: EMPTY_VALUE
