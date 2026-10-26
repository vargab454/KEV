package com.event.teamapp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.compose.runtime.remember
import com.event.teamapp.view.AppScreen
import com.event.teamapp.viewmodel.MainViewModel

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Team Generator",
        state = rememberWindowState(placement = WindowPlacement.Fullscreen),
        undecorated = true // This hides the Windows/Mac title bar and borders
    ) {
        val viewModel = remember { MainViewModel() }
        AppScreen(viewModel)
    }
}