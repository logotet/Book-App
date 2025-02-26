package com.logotet.bookapp.core.presentation.utils

import android.content.Context
import com.logotet.bookapp.android.R
import com.logotet.bookapp.core.domain.result.AppError
import com.logotet.bookapp.core.domain.result.DataError

fun AppError.asString(context: Context): String {
    val resourceId = when (this) {
        is DataError.Remote -> {
            when (this) {
                is DataError.Remote.Redirect -> R.string.error_unknown
                is DataError.Remote.BadRequest -> R.string.error_unknown
                is DataError.Remote.Server -> R.string.error_unknown
                is DataError.Remote.Unauthorized -> R.string.remote_error_authorization
                is DataError.Remote.Forbidden -> R.string.error_unknown
                is DataError.Remote.NotFound -> R.string.error_unknown
                is DataError.Remote.MethodNotAllowed -> R.string.error_unknown
                is DataError.Remote.Timeout -> R.string.remote_error_request_timeout
                is DataError.Remote.Serialization -> R.string.remote_error_serialization
                is DataError.Remote.Unknown -> R.string.error_unknown
            }
        }

        is DataError.Local -> {
            when (this) {
                is DataError.Local.Insert -> R.string.local_error_insert
                is DataError.Local.Update -> R.string.local_error_update
                is DataError.Local.GetData -> R.string.local_error_retrieve
                is DataError.Local.Delete -> R.string.local_error_delete
                is DataError.Local.Unknown -> R.string.error_unknown
            }
        }

        else -> R.string.error_unknown
    }

    return
    context.getString(resourceId)
}
