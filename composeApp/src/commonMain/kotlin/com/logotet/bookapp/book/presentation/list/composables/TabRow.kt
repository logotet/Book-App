package com.logotet.bookapp.book.presentation.list.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.logotet.bookapp.core.presentation.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TabRow(
    modifier: Modifier = Modifier,
    tabs: List<String>,
    selectedTabIndex: Int,
    onTabChange: (Int) -> Unit
) {
    var tabIndex by remember { mutableIntStateOf(selectedTabIndex) }

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

//@Preview(showBackground = true)
@Preview
@Composable
fun TabsRowPreview() {
    AppTheme {
        TabRow (
            tabs = listOf("All", "Favorite"),
            selectedTabIndex = 0,
            onTabChange = {}
        )
    }
}