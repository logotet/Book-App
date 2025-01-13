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
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.logotet.bookapp.android.core.presentation.BaseViewModel
import com.logotet.bookapp.android.core.presentation.state.Event
import com.logotet.bookapp.android.core.presentation.state.ScreenState
import com.logotet.bookapp.android.core.presentation.utils.asString

@Composable
fun <T : Any> ScreenScaffold(
    modifier: Modifier = Modifier,
    baseViewModel: BaseViewModel<T>,
    topBar: @Composable () -> Unit = {},
    content: @Composable BoxScope.(T?) -> Unit,
) {
    val viewLifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    val uiState by baseViewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    var data: T? by remember { mutableStateOf(null) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { AppSnackbar(snackbarHostState) },
        topBar = topBar
    ) { padding ->

        LaunchedEffect(viewLifecycleOwner.lifecycle) {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.STARTED)
                baseViewModel.event.collect { event ->
                    when (event) {
                        is Event.ShowError -> {
                            snackbarHostState.showSnackbar(event.error.asString(context))
                        }
                    }
                }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            content(data)

            when (uiState) {
                is ScreenState.Idle -> {}

                is ScreenState.Loading -> {
                    ProgressIndicator()
                }

                is ScreenState.Success -> {
                    data = (uiState as? ScreenState.Success<T>)?.data
                }
            }
        }
    }
}