package com.logotet.bookapp.android.book.presentation.list

import androidx.lifecycle.viewModelScope
import com.logotet.bookapp.android.book.data.DefaultBookRepository
import com.logotet.bookapp.android.book.domain.model.Book
import com.logotet.bookapp.android.core.domain.result.DataResult
import com.logotet.bookapp.android.core.presentation.BaseViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BookListViewModel(
    private val bookRepository: DefaultBookRepository
) : BaseViewModel<List<Book>>() {
    sealed interface HomeScreenAction {
        data class Search(val query: String) : HomeScreenAction
    }

    private fun getBooksList(query: String) {
        viewModelScope.launch {
            bookRepository.getBooksList(query)
                .collectLatest { result ->
                    when (result) {
                        is DataResult.Loading -> {
                            _state.value = ScreenState.Loading
                        }

                        is DataResult.Success -> {
                            _state.value = ScreenState.Success(result.data)
                        }

                        is DataResult.Error -> {
                            _state.value = ScreenState.Error(result.error)
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

