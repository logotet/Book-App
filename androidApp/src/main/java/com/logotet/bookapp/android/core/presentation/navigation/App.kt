package com.logotet.bookapp.android.core.presentation.navigation

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.logotet.bookapp.android.book.presentation.details.BookDetailsScreen
import com.logotet.bookapp.android.book.presentation.details.BookDetailsViewModel
import com.logotet.bookapp.android.book.presentation.list.BookListScreen
import com.logotet.bookapp.android.book.presentation.list.BookListViewModel
import com.logotet.bookapp.android.core.presentation.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun App() {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = Route.BookList
            ) {
                composable<Route.BookList> {
                    BookListScreen(
                        viewModel = koinViewModel<BookListViewModel>(),
                        navigateToBookDetails = { bookId ->
                            navController.navigate(
                                Route.BookDetails(
                                    bookId
                                )
                            )
                        }
                    )
                }

                composable<Route.BookDetails> {
                    BookDetailsScreen(
                        viewModel = koinViewModel<BookDetailsViewModel>(),
                        navigateBack = { navController.navigateUp() }
                    )
                }
            }
        }
    }
}