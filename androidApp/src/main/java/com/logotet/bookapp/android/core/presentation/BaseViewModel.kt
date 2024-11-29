package com.logotet.bookapp.android.core.presentation

import androidx.lifecycle.ViewModel
import com.logotet.bookapp.android.core.domain.result.AppError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel<T> : ViewModel(){
    protected val _state: MutableStateFlow<ScreenState<T>> =
        MutableStateFlow(ScreenState.Loading)
    val state: StateFlow<ScreenState<T>> = _state.asStateFlow()

    sealed interface ScreenState<out T> {
        data object Idle : ScreenState<Nothing>
        data object Loading : ScreenState<Nothing>
        data class Success<T>(val data: T) : ScreenState<T>
        data class Error(val error: AppError) : ScreenState<Nothing>
    }

    sealed interface Event {
        data class ShowError(val error: AppError) : Event
    }
}