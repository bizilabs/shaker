package dev.mambo.play.shaker

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowDecoration
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import dev.mambo.play.shaker.theme.getColorScheme
import dev.mambo.play.shaker.theme.getShape
import dev.mambo.play.shaker.theme.provideShakerColorScheme

@Composable
@Preview
fun App(close: () -> Unit, hide: () -> Unit) {
    CompositionLocalProvider(provideShakerColorScheme(isSystemInDarkTheme())) {
        MaterialTheme(
            colorScheme = getColorScheme(),
            shapes = getShape(),
        ) {
            MainNavigation(close = close, hide = hide)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
fun main() =
    application {
        val windowState =
            rememberWindowState(
                width = 250.dp,
                height = 500.dp,
            )
        Window(
            title = "Shakker",
            resizable = false,
            state = windowState,
            onCloseRequest = ::exitApplication,
            decoration = WindowDecoration.Undecorated(),
        ) {
            App(
                close = ::exitApplication,
                hide = { window.isVisible = false },
            )
        }
    }
