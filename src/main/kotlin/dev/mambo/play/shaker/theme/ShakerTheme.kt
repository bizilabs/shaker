package dev.mambo.play.shaker.theme

import androidx.compose.runtime.Composable

object ShakerTheme {
    val colorScheme: ShakerColorScheme
        @Composable
        get() = LocalShakerColorScheme.current
}
