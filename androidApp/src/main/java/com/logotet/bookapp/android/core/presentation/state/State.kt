package com.logotet.bookapp.android.core.presentation.state

sealed interface ScreenState<out T> {
    data object Idle : ScreenState<Nothing>
    data object Loading : ScreenState<Nothing>
    data class Success<T>(val data: T) : ScreenState<T>
}