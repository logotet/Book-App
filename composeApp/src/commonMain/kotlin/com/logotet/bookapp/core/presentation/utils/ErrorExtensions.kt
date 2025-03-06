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
import com.logotet.bookapp.core.domain.result.Local
import com.logotet.bookapp.core.domain.result.Remote
import org.jetbrains.compose.resources.StringResource

fun AppError.asString(): StringResource {
    val stringResource = when (this) {
        is Remote -> {
            when (this) {
                is Remote.Redirect -> Res.string.error_unknown
                is Remote.BadRequest -> Res.string.error_unknown
                is Remote.Server -> Res.string.error_unknown
                is Remote.Unauthorized -> Res.string.remote_error_authorization
                is Remote.Forbidden -> Res.string.error_unknown
                is Remote.NotFound -> Res.string.error_unknown
                is Remote.MethodNotAllowed -> Res.string.error_unknown
                is Remote.Timeout -> Res.string.remote_error_request_timeout
                is Remote.Serialization -> Res.string.remote_error_serialization
                is Remote.Unknown -> Res.string.error_unknown
            }
        }

        is Local -> {
            when (this) {
                is Local.Insert -> Res.string.local_error_insert
                is Local.Update -> Res.string.local_error_update
                is Local.GetData -> Res.string.local_error_retrieve
                is Local.Delete -> Res.string.local_error_delete
                is Local.Unknown -> Res.string.error_unknown
            }
        }

        else -> Res.string.error_unknown
    }

    return stringResource
}
