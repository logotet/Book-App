package com.logotet.bookapp.book.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import bookapp.composeapp.generated.resources.Res
import bookapp.composeapp.generated.resources.no_books_message
import bookapp.composeapp.generated.resources.tab_book_list_all
import bookapp.composeapp.generated.resources.tab_book_list_favorite
import com.logotet.bookapp.book.domain.model.Book
import com.logotet.bookapp.book.presentation.list.composables.BookListItemCard
import com.logotet.bookapp.book.presentation.list.composables.SearchBar
import com.logotet.bookapp.book.presentation.list.composables.TabRow
import com.logotet.bookapp.core.presentation.composable.ScreenScaffold
import com.logotet.bookapp.core.presentation.composable.rememberCoilImagePainter
import com.logotet.bookapp.core.presentation.state.ScreenState
import com.logotet.bookapp.core.presentation.theme.Dimensions
import org.jetbrains.compose.resources.stringResource

private const val EMPTY_STRING = ""

@Composable
fun BookListScreen(
    viewModel: BookListViewModel,
    navigateToBookDetails: (String) -> Unit
) {
    val tabState by viewModel.tabState.collectAsState()
    val query by viewModel.queryState.collectAsState()

    ScreenScaffold(baseViewModel = viewModel) { bookList ->

        BookListContent(
            books = bookList ?: emptyList(),
            initialQuery = query,
            selectedTabIndex = tabState.ordinal,
            onSearch = { query ->
                viewModel.onAction(BookListScreenAction.Search(query))
            },
            dismissSearch = {
                viewModel.onAction(BookListScreenAction.Search(EMPTY_STRING))
            },
            onTabChange = {
                viewModel.onAction(BookListScreenAction.TabChange)
            },
            navigateToBookDetails = { bookId ->
                navigateToBookDetails(bookId)
            }
        )
    }
}

@Composable
fun BookListContent(
    books: List<Book> = emptyList(),
    selectedTabIndex: Int,
    initialQuery: String,
    onSearch: (String) -> Unit,
    dismissSearch: () -> Unit,
    onTabChange: () -> Unit,
    navigateToBookDetails: (String) -> Unit,
) {
    val tabsTitles = listOf(
        stringResource(Res.string.tab_book_list_all),
        stringResource(Res.string.tab_book_list_favorite)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
    ) {
        SearchBar(
            initialQuery = initialQuery,
            search = { query ->
                onSearch(query)
            },
            dismissSearch = dismissSearch
        )

        Column(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = Dimensions.Radius.large,
                        topEnd = Dimensions.Radius.large
                    )
                )
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            TabRow(
                tabs = tabsTitles,
                selectedTabIndex = selectedTabIndex,
                onTabChange = { _ ->
                    onTabChange()
                }
            )

            if (books.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = stringResource(Res.string.no_books_message))
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primaryContainer)
                ) {
                    items(books) { book ->
                        val authorName = book.authors.firstOrNull() ?: EMPTY_STRING
                        val bookPainter = rememberCoilImagePainter(book.imageUrl)

                        BookListItemCard(
                            bookTitle = book.title,
                            authorName = authorName,
                            bookRating = book.averageRating,
                            bookCoverPainter = bookPainter,
                            navigate = { navigateToBookDetails(book.id) }
                        )
                    }
                }
            }
        }
    }
}
