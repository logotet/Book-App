package com.logotet.bookapp

import androidx.compose.ui.window.ComposeUIViewController
import com.logotet.bookapp.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }