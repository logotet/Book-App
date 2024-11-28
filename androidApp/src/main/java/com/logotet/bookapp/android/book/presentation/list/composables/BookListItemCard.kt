package com.logotet.bookapp.android.book.presentation.list.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.logotet.bookapp.android.core.presentation.theme.AppTheme
import com.logotet.bookapp.android.core.presentation.theme.Dimensions

private val cardHeight = 100.dp
private val bookCoverWidth = 30.dp
private val arrowHeight = 40.dp

private const val BOOK_COVER_WEIGHT = 0.25F
private const val BOOK_INFO_WEIGHT = 0.55F
private const val NAVIGATION_ARROW_WEIGHT = 0.2F

@Composable
fun BookListItemCard(
    modifier: Modifier = Modifier,
    bookTitle: String,
    authorName: String,
    bookRating: Double?,
    bookCoverIcon: ImageVector = Icons.Default.AccountCircle,
    navigate: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(Dimensions.Spacing.medium)
            .clip(MaterialTheme.shapes.medium)
            .fillMaxWidth()
            .height(cardHeight)
            .background(MaterialTheme.colorScheme.secondaryContainer),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            imageVector = bookCoverIcon,
            contentDescription = null,
            modifier = Modifier
                .fillMaxHeight()
                .width(bookCoverWidth)
                .weight(BOOK_COVER_WEIGHT)
                .padding(Dimensions.Spacing.small)
        )

        Column(
            modifier = Modifier
                .weight(BOOK_INFO_WEIGHT)
                .padding(Dimensions.Spacing.small)
        ) {
            Text(
                text = bookTitle,
                style = MaterialTheme.typography.titleLarge,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = authorName,
                overflow = TextOverflow.Ellipsis
            )

            bookRating?.let { rating ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = rating.toString())
                    Icon(
                        imageVector = Icons.Default.Star,
                        tint = MaterialTheme.colorScheme.tertiary,
                        contentDescription = null
                    )
                }
            }
        }

        IconButton(
            content =
            {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                    modifier = Modifier
                        .height(arrowHeight)
                        .weight(NAVIGATION_ARROW_WEIGHT)
                        .padding(Dimensions.Spacing.small)
                )
            },
            onClick = navigate
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BookListItemPreview() {
    AppTheme {
        BookListItemCard(
            bookTitle = "Harry Potter",
            authorName = "J.K. Rowling",
            bookRating = 4.3,
            navigate = {}
        )
    }
}