package com.logotet.bookapp.book.presentation.list.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.logotet.bookapp.book.presentation.common.RatingText
import com.logotet.bookapp.core.presentation.theme.AppTheme
import com.logotet.bookapp.core.presentation.theme.Dimensions
import org.jetbrains.compose.ui.tooling.preview.Preview

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
    bookRating: String,
    bookCoverPainter: Painter,
    navigate: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(Dimensions.Spacing.medium)
            .clip(MaterialTheme.shapes.medium)
            .fillMaxWidth()
            .height(cardHeight)
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .clickable { navigate() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = bookCoverPainter,
            contentDescription = null,
            modifier = Modifier
                .fillMaxHeight()
                .width(bookCoverWidth)
                .weight(BOOK_COVER_WEIGHT)
                .padding(Dimensions.Spacing.medium)
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


            RatingText(bookRating)
        }

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            modifier = Modifier
                .height(arrowHeight)
                .weight(NAVIGATION_ARROW_WEIGHT)
                .padding(Dimensions.Spacing.small)
        )
    }
}

//@Preview(showBackground = true)
@Preview
@Composable
fun BookListItemPreview() {
    AppTheme {
//        BookListItemCard(
//            bookTitle = "Harry Potter",
//            authorName = "J.K. Rowling",
//            bookRating = "4.3",
//            bookCoverPainter = painterResource(R.drawable.image_book_cover_placeholder),
//            navigate = {}
//        )
    }
}