package com.logotet.bookapp.android.book.presentation.list

import androidx.lifecycle.viewModelScope
import com.logotet.bookapp.android.book.data.DefaultBookRepository
import com.logotet.bookapp.android.book.domain.model.Book
import com.logotet.bookapp.android.core.domain.result.DataResult
import com.logotet.bookapp.android.core.presentation.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BookListViewModel(
    private val bookRepository: DefaultBookRepository
) : BaseViewModel<List<Book>>() {
    sealed interface BookListScreenAction {
        data class Search(val query: String) : BookListScreenAction
        data object DismissSearch : BookListScreenAction
        data object TabChange : BookListScreenAction
    }

    sealed interface BookListScreenEvent {
        data object ClearQuery : BookListScreenEvent
    }

    enum class TabState {
        ALL_BOOKS,
        FAVORITE_BOOKS
    }

    private var _tabState = MutableStateFlow(TabState.ALL_BOOKS)
    val tabState = _tabState.asStateFlow()

    fun onAction(action: BookListScreenAction) {
        when (action) {
            is BookListScreenAction.Search -> {
                getBooksByQuery(action.query)
            }

            is BookListScreenAction.DismissSearch -> {
                _state.value = ScreenState.Idle
            }

            is BookListScreenAction.TabChange -> {
                toggleTab()
                getBooks()
            }
        }
    }

    private fun onEvent(event: BookListScreenEvent) {
        when (event) {
            is BookListScreenEvent.ClearQuery -> {
                _queryState.value = EMPTY_QUERY
            }
        }
    }

    private fun getBooks(query: String = EMPTY_QUERY) {
        when (_tabState.value) {
            TabState.ALL_BOOKS -> getBooksByQuery("kotlin")
            TabState.FAVORITE_BOOKS -> getBooksByQuery("Harry Potter")
        }
    }

    private fun getBooksByQuery(query: String) {
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

    private fun toggleTab() {
        when (_tabState.value) {
            TabState.ALL_BOOKS -> _tabState.value = TabState.FAVORITE_BOOKS
            TabState.FAVORITE_BOOKS -> _tabState.value = TabState.ALL_BOOKS
        }
    }
}

