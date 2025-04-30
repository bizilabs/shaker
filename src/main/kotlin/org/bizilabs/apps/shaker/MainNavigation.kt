package org.bizilabs.apps.shaker

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import org.bizilabs.apps.shaker.screen.MainScreen

@Composable
fun MainNavigation(
    close: () -> Unit,
    hide: () -> Unit,
) {
    Navigator(MainScreen(close = close, hide = hide))
}
