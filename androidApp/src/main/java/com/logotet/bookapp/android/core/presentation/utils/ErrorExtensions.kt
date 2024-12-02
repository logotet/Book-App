package com.logotet.bookapp.android.core.presentation.utils

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.logotet.bookapp.android.R
import com.logotet.bookapp.android.core.domain.result.AppError
import com.logotet.bookapp.android.core.domain.result.DataError

@Composable
fun AppError.asString(): String =
    when (this) {
        is DataError.Remote -> {
            parseDataErrorAsString(
                when (this) {
                    is DataError.Remote.Redirect -> null
                    is DataError.Remote.BadRequest -> null
                    is DataError.Remote.Server -> null
                    is DataError.Remote.Unauthorized -> R.string.remote_error_authorization
                    is DataError.Remote.Forbidden -> null
                    is DataError.Remote.NotFound -> null
                    is DataError.Remote.MethodNotAllowed -> null
                    is DataError.Remote.Timeout -> R.string.remote_error_request_timeout
                    is DataError.Remote.Serialization -> R.string.remote_error_serialization
                    is DataError.Remote.Unknown -> R.string.remote_error_unknown
                }
            )
        }

        is DataError.Local -> {
            parseDataErrorAsString(
                when (this) {
                    is DataError.Local.Insert -> R.string.local_error_insert
                    is DataError.Local.Update -> R.string.local_error_update
                    is DataError.Local.GetData -> R.string.local_error_retrieve
                    is DataError.Local.Delete -> R.string.local_error_delete
                    is DataError.Local.Unknown -> R.string.local_unknown
                }
            )
        }

        else -> (this as DataError).parseDataErrorAsString()
    }

private const val UNKNOWN_MESSAGE = "Error Unknown"

@Composable
fun DataError.parseDataErrorAsString(@StringRes stringId: Int? = null): String =
    stringId?.let { id ->
        stringResource(id = id)
    }
        ?: run {
            throwable?.message ?: UNKNOWN_MESSAGE
        }