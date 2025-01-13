package com.logotet.bookapp.android.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.logotet.bookapp.android.core.domain.result.AppError
import com.logotet.bookapp.android.core.domain.result.DataError
import com.logotet.bookapp.android.core.domain.result.DataResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn

abstract class BaseViewModel<T> : ViewModel() {
    protected val _state: MutableStateFlow<ScreenState<T>> =
        MutableStateFlow(ScreenState.Loading)
    val state: StateFlow<ScreenState<T>> = _state
        .onStart {
            getData()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(EMISSION_DELAY),
            initialValue = ScreenState.Loading
        )

    protected val _event = Channel<Event>()
    val event = _event.receiveAsFlow()

    sealed interface ScreenState<out T> {
        data object Idle : ScreenState<Nothing>
        data object Loading : ScreenState<Nothing>
        data class Success<T>(val data: T) : ScreenState<T>
    }

    sealed interface Event {
        data class ShowError(val error: AppError) : Event
    }

    abstract fun getData()

    fun DataResult<T, DataError>.handleResult(
        onSuccess: (T) -> Unit = {},
        onError: (AppError) -> Unit = {},
    ) {
        when (val result = this) {
            is DataResult.Success -> {
                _state.value = ScreenState.Success(result.data)
                onSuccess(result.data)
            }

            is DataResult.Error -> {
                _event.trySend(Event.ShowError(result.error))
                onError(result.error)
            }
        }
    }

    fun handleEvents(event: Event) {
        when (event) {
            is Event.ShowError -> {
                _event.trySend(event)
            }
        }
    }

    companion object {
        private const val EMISSION_DELAY = 3000L
    }
}