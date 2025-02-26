package com.logotet.bookapp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.logotet.bookapp.di.initKoin

fun main() = application {
    initKoin()

    Window(
        onCloseRequest = ::exitApplication,
        title = "BookApp",
    ) {
        App()
    }
}