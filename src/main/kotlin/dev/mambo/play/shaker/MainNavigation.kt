package dev.mambo.play.shaker

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import dev.mambo.play.shaker.screen.MainScreen

@Composable
fun MainNavigation(close: () -> Unit) {
    Navigator(MainScreen(close = close))
}
