package com.logotet.bookapp.android.book.presentation.details.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.logotet.bookapp.android.book.presentation.details.HeaderText
import com.logotet.bookapp.android.core.presentation.theme.Dimensions

@Composable
fun HeaderWithChip(
    modifier: Modifier = Modifier,
    headerText: String,
    infoText: String
) {
    Column(
        modifier = modifier
            .padding(Dimensions.Spacing.small),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderText(text = headerText)

        TextChip {
            Text(text = infoText)
        }
    }
}