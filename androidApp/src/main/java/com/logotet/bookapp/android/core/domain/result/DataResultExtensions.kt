package com.logotet.bookapp.android.core.domain.result

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <Remote, Local, Error : AppError> Flow<DataResult<Remote, Error>>.mapData(
    covert: (Remote) -> Local
): Flow<DataResult<Local, Error>> =
    map {
        when (it) {
            is DataResult.Success -> DataResult.Success(covert(it.data))
            is DataResult.Error -> DataResult.Error(it.error)
        }
    }
