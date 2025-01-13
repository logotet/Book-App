package com.logotet.bookapp.android.core.presentation.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.logotet.bookapp.android.core.presentation.BaseViewModel

@Composable
fun <T : Any> ScreenScaffold(
    modifier: Modifier = Modifier,
    baseViewModel: BaseViewModel<T>,
    topBar: @Composable () -> Unit = {},
    content: @Composable BoxScope.(T?) -> Unit,
) {
    val uiState by baseViewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    var data: T? by remember { mutableStateOf(null) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { AppSnackbar(snackbarHostState) },
        topBar = topBar
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            content(data)

            when (uiState) {
                is BaseViewModel.ScreenState.Idle -> {}

                is BaseViewModel.ScreenState.Loading -> {
                    ProgressIndicator()
                }

                is BaseViewModel.ScreenState.Success -> {
                    data = (uiState as? BaseViewModel.ScreenState.Success<T>)?.data
                }
            }
        }
    }
}