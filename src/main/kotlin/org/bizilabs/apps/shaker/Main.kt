package org.bizilabs.apps.shaker

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowDecoration
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberNotification
import androidx.compose.ui.window.rememberTrayState
import androidx.compose.ui.window.rememberWindowState
import org.bizilabs.apps.shaker.resources.Res
import org.bizilabs.apps.shaker.resources.pointer
import org.bizilabs.apps.shaker.theme.getColorScheme
import org.bizilabs.apps.shaker.theme.getShape
import org.bizilabs.apps.shaker.theme.provideShakerColorScheme
import org.jetbrains.compose.resources.painterResource

@Composable
@Preview
fun App(
    close: () -> Unit,
    hide: () -> Unit,
) {
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
        var isOpen by remember { mutableStateOf(true) }
        val trayState = rememberTrayState()
        val notification =
            rememberNotification(
                title = "Hola Shaker?",
                message = "We're still building this feature...",
            )

        Tray(
            state = trayState,
            icon = painterResource(Res.drawable.pointer),
            menu = {
                if (isOpen) {
                    Item(
                        text = "Close",
                        onClick = {
                            isOpen == !isOpen
                        },
                    )
                }
                Item(
                    "Coming Soon..",
                    onClick = {
                        trayState.sendNotification(notification)
                    },
                )
            },
        )

        val windowState = rememberWindowState(width = 250.dp, height = 500.dp)

        Window(
            title = "Shaker",
            resizable = false,
            state = windowState,
            onCloseRequest = ::exitApplication,
            decoration = WindowDecoration.Undecorated(),
        ) {
            App(
                close = ::exitApplication,
                hide = { isOpen = false },
            )
        }
    }
