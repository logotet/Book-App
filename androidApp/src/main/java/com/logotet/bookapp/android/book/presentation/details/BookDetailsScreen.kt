package com.logotet.bookapp.android.book.presentation.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.logotet.bookapp.android.R
import com.logotet.bookapp.android.book.domain.model.Book
import com.logotet.bookapp.android.book.domain.model.BookDetails
import com.logotet.bookapp.android.book.domain.model.BookWithDetails
import com.logotet.bookapp.android.book.presentation.details.composables.BookCoverImage
import com.logotet.bookapp.android.book.presentation.details.composables.HeaderWithChip
import com.logotet.bookapp.android.book.presentation.details.composables.TextChip
import com.logotet.bookapp.android.core.presentation.composable.ScreenScaffold
import com.logotet.bookapp.android.core.presentation.composable.TopBar
import com.logotet.bookapp.android.core.presentation.composable.rememberCoilImagePainter
import com.logotet.bookapp.android.core.presentation.theme.AppTheme
import com.logotet.bookapp.android.core.presentation.theme.Dimensions
import com.logotet.bookapp.android.core.presentation.utils.asCommaSeparatedString

@Composable
fun BookDetailsScreen(
    viewModel: BookDetailsViewModel,
    navigateBack: () -> Unit
) {
    val bookDetailsState by viewModel.state.collectAsState()
    val isSaved by viewModel.isSaved.collectAsState()

    ScreenScaffold(baseViewModel = viewModel,
        topBar = {
            TopBar(
                onNavigate = navigateBack
            )
        }
    ) { bookWithDetails ->
        bookWithDetails?.let { data ->
            val book = data.first

            BookDetailsContent(
                bookWithDetails = data,
                isSaved = isSaved,
                setBookFavoriteStatus = { isSaved ->
                    if (isSaved)
                        viewModel.onAction(
                            BookDetailsViewModel.BookDetailsAction.DeleteBook(book)
                        )
                    else
                        viewModel.onAction(
                            BookDetailsViewModel.BookDetailsAction.SaveBook(book)
                        )
                }
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BookDetailsContent(
    bookWithDetails: BookWithDetails,
    isSaved: Boolean,
    setBookFavoriteStatus: (Boolean) -> Unit
) {
    val book = bookWithDetails.first
    val bookTitle = book.title
    val bookAuthors = book.authors.asCommaSeparatedString()
    val bookCoverUrl = book.imageUrl
    val bookRating = book.averageRating
    val bookPages = book.numberOfPages.toString()
    val bookLanguages = book.languages
    val description = bookWithDetails.second.description

    val scrollableState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = Dimensions.Spacing.large)
            .verticalScroll(scrollableState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.medium)
    ) {
        //In the background glass morphism

        Column(
            modifier = Modifier
                .padding(
                    horizontal = Dimensions.Spacing.medium,
                    vertical = Dimensions.Spacing.large
                )
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.medium)
        ) {
            val bookPainter = rememberCoilImagePainter(bookCoverUrl)

            BookCoverImage(
                painter = bookPainter,
                isSaved = isSaved,
                onHeartClick = { isSaved ->
                    setBookFavoriteStatus(isSaved)
                }
            )

            Text(
                text = bookTitle,
                style = MaterialTheme.typography.headlineLarge
            )

            Text(
                text = bookAuthors,
                style = MaterialTheme.typography.headlineSmall
            )

            Row(
                modifier = Modifier.padding(top = Dimensions.Spacing.large),
                horizontalArrangement = Arrangement.Center
            ) {
                HeaderWithChip(
                    headerText = stringResource(R.string.header_rating),
                    infoText = bookRating
                )

                HeaderWithChip(
                    headerText = stringResource(R.string.header_pages),
                    infoText = bookPages
                )
            }
        }

        if (bookLanguages.isNotEmpty()) {
            HeaderText(text = stringResource(R.string.header_languages))

            FlowRow(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.wrapContentSize(Alignment.Center)
            ) {
                bookLanguages.forEach { language ->
                    TextChip {
                        Text(text = language)
                    }
                }
            }
        }

        description?.let { bookDescription ->
            HeaderText(text = stringResource(R.string.header_synopsis))

            Text(
                text = bookDescription,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun HeaderText(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.titleLarge
    )
}

@Preview(showBackground = true)
@Composable
fun BookListScreenPreview() {
    AppTheme {
        BookDetailsContent(
            bookWithDetails = BookWithDetails(
                Book(
                    title = "Fantastic Mr Fox",
                    imageUrl = "https://ia800507.us.archive.org/view_archive.php?archive=/8/items/l_covers_0009/l_covers_0009_15.zip&file=0009159151-L.jpg",
                    authors = listOf("John Doe", "Theo Van Der Walt"),
                    languages = listOf("en", "bg", "en", "bg", "en", "bg", "en", "bg"),
                    firstPublishYear = "4.7",
                    averageRating = "3.5",
                    ratingCount = 0,
                    numberOfPages = 511,
                    numberOfEditions = 0,
                    id = ""
                ),
                BookDetails(
                    id = "",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
                )
            ),
            isSaved = true,
            setBookFavoriteStatus = {}
        )
    }
}