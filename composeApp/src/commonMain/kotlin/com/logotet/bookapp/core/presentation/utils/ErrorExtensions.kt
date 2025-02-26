package com.logotet.bookapp.core.presentation.utils

import bookapp.composeapp.generated.resources.Res
import bookapp.composeapp.generated.resources.error_unknown
import bookapp.composeapp.generated.resources.local_error_delete
import bookapp.composeapp.generated.resources.local_error_insert
import bookapp.composeapp.generated.resources.local_error_retrieve
import bookapp.composeapp.generated.resources.local_error_update
import bookapp.composeapp.generated.resources.remote_error_authorization
import bookapp.composeapp.generated.resources.remote_error_request_timeout
import bookapp.composeapp.generated.resources.remote_error_serialization
import com.logotet.bookapp.core.domain.result.AppError
import com.logotet.bookapp.core.domain.result.DataError
import org.jetbrains.compose.resources.StringResource

fun AppError.asString(): StringResource {
    val stringResource = when (this) {
        is DataError.Remote -> {
            when (this) {
                is DataError.Remote.Redirect -> Res.string.error_unknown
                is DataError.Remote.BadRequest -> Res.string.error_unknown
                is DataError.Remote.Server -> Res.string.error_unknown
                is DataError.Remote.Unauthorized -> Res.string.remote_error_authorization
                is DataError.Remote.Forbidden -> Res.string.error_unknown
                is DataError.Remote.NotFound -> Res.string.error_unknown
                is DataError.Remote.MethodNotAllowed -> Res.string.error_unknown
                is DataError.Remote.Timeout -> Res.string.remote_error_request_timeout
                is DataError.Remote.Serialization -> Res.string.remote_error_serialization
                is DataError.Remote.Unknown -> Res.string.error_unknown
            }
        }

        is DataError.Local -> {
            when (this) {
                is DataError.Local.Insert -> Res.string.local_error_insert
                is DataError.Local.Update -> Res.string.local_error_update
                is DataError.Local.GetData -> Res.string.local_error_retrieve
                is DataError.Local.Delete -> Res.string.local_error_delete
                is DataError.Local.Unknown -> Res.string.error_unknown
            }
        }

        else -> Res.string.error_unknown
    }

    return stringResource
}
