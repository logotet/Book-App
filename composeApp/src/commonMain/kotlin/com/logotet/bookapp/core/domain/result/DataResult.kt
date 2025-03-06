package com.logotet.bookapp.core.domain.result

sealed interface DataResult<out D, out E : AppError> {
    data class Success<D>(val data: D) : DataResult<D, Nothing>
    data class Error<E : AppError>(val error: E) :
        DataResult<Nothing, E>
}

sealed interface Remote : AppError {
    data object Redirect : Remote
    data object BadRequest : Remote
    data object Server : Remote
    data object Unauthorized : Remote
    data object Forbidden : Remote
    data object NotFound : Remote
    data object MethodNotAllowed : Remote
    data object Timeout : Remote
    data object Serialization : Remote
    data object Unknown : Remote
}

sealed interface Local : AppError {
    data object Insert : Local
    data object Update : Local
    data object GetData : Local
    data object Delete : Local
    data object Unknown : Local
}