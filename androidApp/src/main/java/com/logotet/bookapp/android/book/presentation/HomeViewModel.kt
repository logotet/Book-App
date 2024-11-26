package com.logotet.bookapp.android.book.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.logotet.bookapp.android.book.data.DefaultBookRepository
import com.logotet.bookapp.android.book.domain.model.Book
import com.logotet.bookapp.android.core.domain.result.DataError
import com.logotet.bookapp.android.core.domain.result.DataResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(
    private val bookRepository: DefaultBookRepository
) : ViewModel() {
    sealed interface HomeScreenState {
        data object Loading : HomeScreenState
        data class Success(val books: List<Book>) : HomeScreenState
        data class Error(val error: DataError) : HomeScreenState
    }

    sealed interface HomeScreenAction {
        data class Search(val query: String) : HomeScreenAction
    }

    private val _state: MutableStateFlow<HomeScreenState> =
        MutableStateFlow(HomeScreenState.Loading)
    val state: StateFlow<HomeScreenState> = _state.asStateFlow()

    private fun getBooksList(query: String) {
        viewModelScope.launch {
            bookRepository.getBooksList(query)
                .collectLatest { result ->
                    when (result) {
                        is DataResult.Loading -> {
                            _state.value = HomeScreenState.Loading
                        }

                        is DataResult.Success -> {
                            _state.value = HomeScreenState.Success(result.data)
                        }

                        is DataResult.Error -> {
                            _state.value = HomeScreenState.Error(result.error)
                        }
                    }
                }
        }
    }

    fun onAction(action: HomeScreenAction) {
        when (action) {
            is HomeScreenAction.Search -> {
                getBooksList(action.query)
            }
        }
    }
}

