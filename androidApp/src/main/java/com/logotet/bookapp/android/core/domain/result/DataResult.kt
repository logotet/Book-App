package com.logotet.bookapp.android.core.domain.result

sealed interface DataResult<out D, out E : AppError> {
    data object Loading : DataResult<Nothing, Nothing>
    data class Success<D>(val data: D) : DataResult<D, Nothing>
    data class Error<E : AppError>(val error: E) :
        DataResult<Nothing, E>
}

sealed class DataError(val throwable: Throwable?) : AppError {
    sealed class Remote(throwable: Throwable?) : DataError(throwable) {
        class Redirect(throwable: Throwable?) : Remote(throwable)
        class BadRequest(throwable: Throwable?) : Remote(throwable)
        class Server(throwable: Throwable?) : Remote(throwable)
        class Unauthorized(throwable: Throwable?) : Remote(throwable)
        class Forbidden(throwable: Throwable?) : Remote(throwable)
        class NotFound(throwable: Throwable?) : Remote(throwable)
        class MethodNotAllowed(throwable: Throwable?) : Remote(throwable)
        class Timeout(throwable: Throwable?) : Remote(throwable)
        class Serialization(throwable: Throwable?) : Remote(throwable)
        class Unknown(throwable: Throwable? = null) : Remote(throwable)
    }

    sealed class Local(throwable: Throwable?) : DataError(throwable) {
        class Insert(throwable: Throwable?) : Local(throwable)
        class Update(throwable: Throwable?) : Local(throwable)
        class GetData(throwable: Throwable?) : Local(throwable)
        class Unknown(throwable: Throwable? = null) : Local(throwable)
    }
}