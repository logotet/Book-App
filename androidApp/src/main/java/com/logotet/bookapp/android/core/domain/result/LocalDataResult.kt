package com.logotet.bookapp.android.core.domain.result

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

suspend inline fun <reified Data, Action : DatabaseAction> makeLocalRequest(
    databaseAction: Action,
    crossinline execute: suspend () -> DataResult<Data, DataError.Local>
): Flow<DataResult<Data, DataError.Local>> =
    flow {
        emit(DataResult.Loading)

        try {
            execute()
        } catch (e: Exception) {
            parseLocalError(databaseAction = databaseAction, throwable = e)
            emit(DataResult.Error(DataError.Local.Insert(e)))
        }
    }

fun parseLocalError(
    databaseAction: DatabaseAction,
    throwable: Throwable? = null
): DataResult<Nothing, DataError.Local> =
    when (databaseAction) {
        DatabaseAction.INSERT -> DataResult.Error(DataError.Local.Insert(throwable))
        DatabaseAction.UPDATE -> DataResult.Error(DataError.Local.Update(throwable))
        DatabaseAction.GET -> DataResult.Error(DataError.Local.GetData(throwable))
        DatabaseAction.DELETE -> DataResult.Error(DataError.Local.Delete(throwable))
        else -> DataResult.Error(DataError.Local.Unknown(throwable))
    }

enum class DatabaseAction {
    INSERT,
    UPDATE,
    GET,
    DELETE
}