package com.logotet.bookapp.android.core.domain.result

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <Fetched, Local> Flow<DataResult<Fetched, DataError>>.mapSuccess(
    convert: suspend (Fetched) -> Local
): Flow<DataResult<Local, DataError>> =
    map { result ->
        when (result) {
            is DataResult.Loading -> result
            is DataResult.Success<Fetched> -> {
                DataResult.Success(convert(result.data))
            }
            is DataResult.Error -> {
                result
            }
        }
    }