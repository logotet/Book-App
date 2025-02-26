package com.logotet.bookapp.core.presentation.state

import com.logotet.bookapp.core.domain.result.AppError

sealed interface Event {
    data class ShowError(val error: AppError) : Event
}