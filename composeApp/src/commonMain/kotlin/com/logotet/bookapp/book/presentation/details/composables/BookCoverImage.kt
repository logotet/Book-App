package com.logotet.bookapp.book.presentation.details.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.logotet.bookapp.core.presentation.theme.AppTheme
import com.logotet.bookapp.core.presentation.theme.Dimensions
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

private val imageHeight = 220.dp
private val imageWidth = 150.dp
private val heartSize = 30.dp

@Composable
fun BookCoverImage(
    painter: Painter,
    isSaved: Boolean,
    modifier: Modifier = Modifier,
    onHeartClick: (Boolean) -> Unit
) {
    Box(
        modifier = modifier
            .size(height = imageHeight, width = imageWidth)
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painter,
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )

        IconButton(
            modifier = Modifier
                .padding(Dimensions.Spacing.small)
                .align(Alignment.TopEnd),
            onClick = { onHeartClick(isSaved) },
        ) {
            Icon(
                modifier = Modifier.size(heartSize),
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
                tint =
                if (isSaved)
                    MaterialTheme.colorScheme.error
                else
                    MaterialTheme.colorScheme.primaryContainer
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun BookCoverImagePreview() {
//    AppTheme {
//        BookCoverImage(
//            painter = painterResource(R.drawable.image_book_cover_placeholder),
//            isSaved = false,
//            onHeartClick = {}
//        )
//    }
//}