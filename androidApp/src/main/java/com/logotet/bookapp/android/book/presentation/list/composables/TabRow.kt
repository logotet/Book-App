package com.logotet.bookapp.android.book.presentation.list.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.logotet.bookapp.android.core.presentation.theme.AppTheme

private const val INITIAL_INDEX = 0
private const val ALL_BOOKS = "All Books"
private const val FAVORITE_BOOKS = "Favorite Books"

@Composable
fun TabRow(
    modifier: Modifier = Modifier,
    onTabChange: (Int) -> Unit,
) {
    val tabs: List<String> = listOf(ALL_BOOKS, FAVORITE_BOOKS)
    var tabIndex by remember { mutableIntStateOf(INITIAL_INDEX) }

    TabRow(
        selectedTabIndex = tabIndex,
        modifier = modifier.fillMaxWidth()
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                text = {
                    Text(title)
                },
                selected = index == tabIndex,
                unselectedContentColor = MaterialTheme.colorScheme.onSurface,
                onClick = {
                    tabIndex = index
                    onTabChange(tabIndex)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TabsRowPreview() {
    AppTheme {
        TabRow {}
    }
}