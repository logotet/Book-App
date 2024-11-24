package com.logotet.bookapp.android.core.domain.result

sealed interface DataResult<out D, out E : AppError> {
    data class Success<D>(val data: D) : DataResult<D, Nothing>
    data class Error<E : AppError>(val error: E) :
        DataResult<Nothing, E>
}

sealed class DataError(val throwable: Throwable?) : AppError {
    sealed class RemoteError(throwable: Throwable?) : DataError(throwable) {
        class Timeout(throwable: Throwable?) : RemoteError(throwable)
        class NoInternet(throwable: Throwable?) : RemoteError(throwable)
        class Server(throwable: Throwable?) : RemoteError(throwable)
        class Serialization(throwable: Throwable?) : RemoteError(throwable)
        class Unknown(throwable: Throwable? = null) : LocalError(throwable)
    }

    sealed class LocalError(throwable: Throwable?) : DataError(throwable) {
        class Insert(throwable: Throwable?) : LocalError(throwable)
        class Update(throwable: Throwable?) : LocalError(throwable)
        class GetData(throwable: Throwable?) : LocalError(throwable)
        class Unknown(throwable: Throwable? = null) : LocalError(throwable)
    }
}