package com.logotet.bookapp.android.core.domain.result

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map

fun <Fetched, Local> Flow<DataResult<Fetched, DataError>>.mapSuccess(
    convert: suspend (Fetched) -> Local
): Flow<DataResult<Local, DataError>> =
    map { result ->
        when (result) {
            is DataResult.Success<Fetched> -> {
                try {
                    DataResult.Success(convert(result.data))
                } catch (e: Exception) {
                    DataResult.Error(DataError.Local.Unknown())
                }
            }

            is DataResult.Error -> {
                result
            }
        }
    }

suspend fun <Data> Flow<DataResult<Data, DataError>>.onSuccess(
    action: (Data) -> Unit
) {
    collectLatest { result ->
        if (result is DataResult.Success<Data>) {
            action(result.data)
        }
    }
}