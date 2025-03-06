package com.logotet.bookapp.core.domain.result

import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.coroutines.coroutineContext

inline fun <reified Data, Action : DatabaseAction> makeLocalRequest(
    databaseAction: Action,
    crossinline execute: suspend () -> DataResult<Data, Local>
): Flow<DataResult<Data, Local>> =
    flow {
        val result = try {
            execute()
        } catch (throwable: Throwable) {
            parseLocalError(databaseAction = databaseAction)
        }

        emit(result)
    }

suspend fun parseLocalError(
    databaseAction: DatabaseAction,
    throwable: Throwable? = null
): DataResult<Nothing, Local> =
    when (databaseAction) {
        DatabaseAction.INSERT -> DataResult.Error(Local.Insert)
        DatabaseAction.UPDATE -> DataResult.Error(Local.Update)
        DatabaseAction.GET -> DataResult.Error(Local.GetData)
        DatabaseAction.DELETE -> DataResult.Error(Local.Delete)
        else -> {
            coroutineContext.ensureActive()
            DataResult.Error(Local.Unknown)
        }
    }

enum class DatabaseAction {
    INSERT,
    UPDATE,
    GET,
    DELETE
}