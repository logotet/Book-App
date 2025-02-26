package com.logotet.bookapp.book.presentation.details.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.logotet.bookapp.core.presentation.theme.AppTheme
import com.logotet.bookapp.core.presentation.theme.Dimensions
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TextChip(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .padding(Dimensions.Spacing.small)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.secondary)
            .height(50.dp)
            .width(50.dp)
            .padding(Dimensions.Spacing.small),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

//@Preview(showBackground = true)
@Preview
@Composable
fun TextChipPreview() {
    AppTheme {
        TextChip {
            Text("450")
        }
    }
}