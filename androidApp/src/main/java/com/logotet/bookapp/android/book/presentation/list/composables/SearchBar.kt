@file:OptIn(ExperimentalMaterial3Api::class)

package com.logotet.bookapp.android.book.presentation.list.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.logotet.bookapp.android.core.presentation.theme.AppTheme
import com.logotet.bookapp.android.core.presentation.theme.Dimensions

private const val EMPTY_STRING = ""
private val searchBarHeight = 50.dp

@Composable
fun SearchBar(
    initialQuery: String,
    search: (String) -> Unit,
    dismissSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    var query = initialQuery

    Row(
        modifier = modifier
            .padding(
                horizontal = Dimensions.Spacing.medium,
                vertical = Dimensions.Spacing.xlarge
            )
            .height(searchBarHeight)
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
    ) {
        TextField(
            modifier = Modifier.fillMaxSize(),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        query = EMPTY_STRING
                        dismissSearch()
                    },
                    content = {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                    }
                )
            },
            value = query,
            onValueChange = { newValue ->
                query = newValue
                search(query)
            },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    AppTheme {
        SearchBar(
            initialQuery = EMPTY_STRING,
            search = {},
            dismissSearch = {}
        )
    }
}