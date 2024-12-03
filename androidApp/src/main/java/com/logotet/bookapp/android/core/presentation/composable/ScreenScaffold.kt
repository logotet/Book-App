package com.logotet.bookapp.android.core.presentation.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.logotet.bookapp.android.core.presentation.BaseViewModel
import com.logotet.bookapp.android.core.presentation.utils.asString

@Composable
fun <T : Any> ScreenScaffold(
    modifier: Modifier = Modifier,
    uiState: BaseViewModel.ScreenState<T>,
    topBar: @Composable () -> Unit = {},
    content: @Composable BoxScope.(T) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { AppSnackbar(snackbarHostState) },
        topBar = topBar
    ) { padding ->
        var data: T? by remember { mutableStateOf(null) }
        var isLoading by remember { mutableStateOf(false) }
        var showError by remember { mutableStateOf(false) }
        var errorMessage: String? by remember { mutableStateOf(null) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            if (isLoading) {
                ProgressIndicator()
            }

            LaunchedEffect(showError) {
                if (showError) {
                    errorMessage?.let { snackbarHostState.showSnackbar(it) }
                }
            }

            when (uiState) {
                is BaseViewModel.ScreenState.Idle -> {}

                is BaseViewModel.ScreenState.Loading -> {
                    isLoading = true
                }

                is BaseViewModel.ScreenState.Success -> {
                    data = uiState.data
                    isLoading = false
                }

                is BaseViewModel.ScreenState.Error -> {
                    isLoading = false
                    showError = true
                    errorMessage = uiState.error.asString()
                }
            }

            data?.let { content(it) }
        }
    }
}