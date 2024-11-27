@file:OptIn(ExperimentalMaterial3Api::class)

package com.logotet.bookapp.android.book.presentation.list.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.logotet.bookapp.android.core.presentation.theme.AppTheme

private const val EMPTY_STRING = ""
private val searchBarHeight = 50.dp

@Composable
fun SearchBar(
    search: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var query by remember { mutableStateOf(EMPTY_STRING) }

    SearchBar(
        leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
        trailingIcon = {
            IconButton(
                onClick = { query = EMPTY_STRING },
                content = {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                }
            )
        },
        query = query,
        onQueryChange = { newValue ->
            query = newValue
            search(newValue)
        },
        onSearch = {},
        active = true,
        onActiveChange = {},
        modifier = modifier
            .fillMaxWidth()
            .height(searchBarHeight)
    ) { }
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    AppTheme {
       SearchBar(search = {})
    }
}