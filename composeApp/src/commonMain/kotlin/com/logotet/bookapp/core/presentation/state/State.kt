package com.logotet.bookapp.core.presentation.state

sealed interface ScreenState<out T> {
    data object Idle : ScreenState<Nothing>
    data object Loading : ScreenState<Nothing>
    data class Success<T>(val data: T) : ScreenState<T>
}