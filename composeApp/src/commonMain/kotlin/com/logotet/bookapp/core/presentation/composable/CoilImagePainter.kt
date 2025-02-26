package com.logotet.bookapp.core.presentation.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import coil3.compose.rememberAsyncImagePainter
import com.logotet.bookapp.android.R
import org.jetbrains.compose.resources.painterResource

@Composable
fun rememberCoilImagePainter(
    imageUrl: String,
    placeholder: Int = R.drawable.image_book_cover_placeholder
): Painter {
    var imageLoadResult by remember {
        mutableStateOf<Result<Painter>?>(null)
    }

    val bookCoverPainter = rememberAsyncImagePainter(
        model = imageUrl,
        onSuccess = {
            imageLoadResult =
                if (it.painter.intrinsicSize.width > 1 && it.painter.intrinsicSize.height > 1) {
                    Result.success(it.painter)
                } else {
                    Result.failure(Exception())
                }
        },
        onError = {
            it.result.throwable.printStackTrace()
            imageLoadResult = Result.failure(it.result.throwable)
        }
    )

    val painter =
        when (val result = imageLoadResult) {
            null -> painterResource(placeholder)

            else -> if (result.isSuccess) {
                bookCoverPainter
            } else {
                painterResource(placeholder)
            }
        }

    return painter
}