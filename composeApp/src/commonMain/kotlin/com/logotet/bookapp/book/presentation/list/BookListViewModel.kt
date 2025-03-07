package com.logotet.bookapp.book.presentation.list

import androidx.lifecycle.viewModelScope
import com.logotet.bookapp.book.domain.BookRepository
import com.logotet.bookapp.book.domain.model.Book
import com.logotet.bookapp.book.presentation.list.BookListScreenAction.DismissSearch
import com.logotet.bookapp.book.presentation.list.BookListScreenAction.Search
import com.logotet.bookapp.book.presentation.list.BookListScreenAction.TabChange
import com.logotet.bookapp.book.presentation.list.BookListScreenEvent.ClearQuery
import com.logotet.bookapp.book.presentation.list.TabState.ALL_BOOKS
import com.logotet.bookapp.book.presentation.list.TabState.FAVORITE_BOOKS
import com.logotet.bookapp.core.presentation.BaseViewModel
import com.logotet.bookapp.core.presentation.state.ScreenState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class BookListViewModel(
    private val bookRepository: BookRepository
) : BaseViewModel<List<Book>>() {
    private var _tabState = MutableStateFlow(ALL_BOOKS)
    val tabState = _tabState.asStateFlow()

    private val _queryState = MutableStateFlow(EMPTY_QUERY)
    val queryState = _queryState.asStateFlow()

    override fun getData() {
        observeQuery()
    }

    fun onAction(action: BookListScreenAction) {
        when (action) {
            is Search -> {
                _queryState.value = action.query
            }

            is DismissSearch -> {
                _queryState.value = EMPTY_QUERY
                _state.value = ScreenState.Idle
            }

            is TabChange -> {
                onEvent(ClearQuery)
                toggleTab()
                getBooks()
            }
        }
    }

    private fun onEvent(event: BookListScreenEvent) {
        when (event) {
            is ClearQuery -> {
                _queryState.value = EMPTY_QUERY
            }
        }
    }

    private fun getBooks(query: String = EMPTY_QUERY) {
        startLoading()
        when (_tabState.value) {
            ALL_BOOKS -> getBooksByQuery(query)
            FAVORITE_BOOKS ->
                if (query.isBlank())
                    getFavoriteBooks()
                else
                    getFavoriteBooksByQuery(query)
        }
    }

    private fun getFavoriteBooksByQuery(query: String) {
        viewModelScope.launch {
            bookRepository.getFavoriteBooksByTitle(query)
                .collectLatest { result ->
                    result.handleResult()
                }
        }
    }

    private fun observeQuery() {
        viewModelScope.launch {
            queryState
                .debounce(DEBOUNCE_QUERY_TIME)
                .distinctUntilChanged()
                .onEach { query ->
                    getBooks(query)
                }
                .stateIn(viewModelScope)
        }
    }

    private fun getBooksByQuery(query: String) {
        viewModelScope.launch {
            bookRepository.getBooksList(query)
                .collectLatest { result ->
                    result.handleResult()
                }
        }
    }

    private fun getFavoriteBooks() {
        viewModelScope.launch {
            bookRepository.getAllFavoriteBooks()
                .collectLatest { result ->
                    result.handleResult()
                }
        }
    }

    private fun toggleTab() {
        when (_tabState.value) {
            ALL_BOOKS -> _tabState.value = FAVORITE_BOOKS
            FAVORITE_BOOKS -> _tabState.value = ALL_BOOKS
        }
    }

    companion object {
        private const val EMPTY_QUERY = ""
        private const val DEBOUNCE_QUERY_TIME = 500L
    }
}

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

