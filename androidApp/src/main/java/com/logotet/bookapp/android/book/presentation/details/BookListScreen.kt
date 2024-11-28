@file:OptIn(ExperimentalMaterial3Api::class)

package com.logotet.bookapp.android.book.presentation.details

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.logotet.bookapp.android.book.domain.model.Book
import com.logotet.bookapp.android.book.presentation.list.BookListViewModel
import com.logotet.bookapp.android.book.presentation.list.composables.BookListItemCard
import com.logotet.bookapp.android.book.presentation.list.composables.SearchBar
import com.logotet.bookapp.android.book.presentation.list.composables.TabRow
import com.logotet.bookapp.android.core.presentation.BaseViewModel

private const val EMPTY_STRING = ""

@Composable
fun BookListScreen(
    viewModel: BookListViewModel,
    navigateToBookDetails: (String) -> Unit
) {
    val bookListState by viewModel.state.collectAsState()

    when (val currentState = bookListState) {
        is BaseViewModel.ScreenState.Loading -> {}
        is BaseViewModel.ScreenState.Success -> {
            BookListContent(
                books = currentState.data,
                onSearch = { query ->
                    viewModel.onAction(BookListViewModel.BookListScreenAction.Search(query))
                },
                navigateToBookDetails = { bookId ->
                    navigateToBookDetails(bookId)
                }
            )
        }

        is BaseViewModel.ScreenState.Error -> { }
    }

}

@Composable
fun BookListContent(
    books: List<Book> = emptyList(),
    onSearch: (String) -> Unit,
    navigateToBookDetails: (String) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SearchBar(
            search = { query ->
                Log.d("bookListScreen", query)
                onSearch(query)
            }
        )

        //  Tabs
        TabRow {
            Log.d("bookListScreen", "${it.toString()}")
        }

        LazyColumn {
            items(books) { book ->
                BookListItemCard(
                    bookTitle = book.title,
                    authorName = book.authors.firstOrNull() ?: EMPTY_STRING,
                    bookRating = book.averageRating,
                    navigate = { navigateToBookDetails(book.id) }
                )
            }
        }
    }
}