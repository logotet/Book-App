package com.logotet.bookapp.android.book.data.network.mapper

import com.logotet.bookapp.android.core.domain.result.DataError
import com.logotet.bookapp.android.core.domain.result.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <Remote, Local> Flow<DataResult<Remote, DataError.Remote>>.mapSuccess(
    converter: suspend (Remote) -> Local
): Flow<DataResult<Local, DataError.Remote>> =
    map { result ->
        when (result) {
            is DataResult.Loading -> result
            is DataResult.Success<Remote> -> {
                DataResult.Success(converter(result.data))
            }
            is DataResult.Error -> {
                result
            }
        }
    }