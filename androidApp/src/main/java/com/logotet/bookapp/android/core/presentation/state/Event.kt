package com.logotet.bookapp.android.core.presentation.state

import com.logotet.bookapp.android.core.domain.result.AppError

sealed interface Event {
    data class ShowError(val error: AppError) : Event
}