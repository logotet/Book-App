package com.logotet.bookapp.core.presentation.composable

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.logotet.bookapp.core.presentation.BaseViewModel
import com.logotet.bookapp.core.presentation.state.Event
import com.logotet.bookapp.core.presentation.state.ScreenState
import com.logotet.bookapp.core.presentation.utils.asString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun <T : Any> ScreenScaffold(
    modifier: Modifier = Modifier,
    baseViewModel: BaseViewModel<T>,
    topBar: @Composable () -> Unit = {},
    content: @Composable BoxScope.(T?) -> Unit,
) {
    val viewLifecycleOwner = LocalLifecycleOwner.current

    val uiState by baseViewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    var data: T? by remember { mutableStateOf(null) }

    var errorMessageResource: StringResource? by remember {
        mutableStateOf(null)
    }

    val rememberCoroutineScope = rememberCoroutineScope()

    // consider using separate string resource management for each platform
    errorMessageResource?.let {
        val errorMessage = stringResource(it)

        rememberCoroutineScope.launch {
            snackbarHostState.showSnackbar(errorMessage)
            errorMessageResource = null
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { AppSnackbar(snackbarHostState) },
        topBar = topBar
    ) { padding ->

        LaunchedEffect(viewLifecycleOwner.lifecycle) {
            withContext(Dispatchers.Main.immediate) {
                baseViewModel.event.collect { event ->
                    when (event) {
                        is Event.ShowError -> {
                            errorMessageResource = event.error.asString()
                        }
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