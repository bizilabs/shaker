package dev.mambo.play.shaker.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Light Common Colors
private val LightSuccess: Color = Color(0xFF4CAF50)
private val LightOnSuccess: Color = Color(0xFFFFFFFF)
private val LightInfo: Color = Color(0xFF2196F3)
private val LightOnInfo: Color = Color(0xFFFFFFFF)
private val LightWarning: Color = Color(0xFFFFC107)
private val LightOnWarning: Color = Color(0xFF000000)
private val LightError: Color = Color(0xFFFF0800)
private val LightOnError: Color = Color(0xFFFFFFFF)
private val LightBackground: Color = Color(0xFFFFFFFF)
private val LightOnBackground: Color = Color(0xFF000000)
private val LightSurface: Color = Color(0xFFFFFFFF)
private val LightOnSurface: Color = Color(0xFF000000)
private val LightContainer: Color = Color(0xFFF8F8F8)
private val LightOnContainer: Color = Color(0xFF000000)

private val DarkBackground: Color = Color(0xFF000000)
private val DarkOnBackground: Color = Color(0xFFFFFFFF)
private val DarkSurface: Color = Color(0xFF000000)
private val DarkOnSurface: Color = Color(0xFFFFFFFF)
private val DarkContainer: Color = Color(0xFF121212)
private val DarkOnContainer: Color = Color(0xFFFFFFFF)
private val DarkError: Color = Color(0xFFFF4E47)
private val DarkOnError: Color = Color(0xFF000000)
private val DarkSuccess: Color = Color(0xFF388E3C)
private val DarkOnSuccess: Color = Color(0xFFFFFFFF)
private val DarkInfo: Color = Color(0xFF1976D2)
private val DarkOnInfo: Color = Color(0xFFFFFFFF)
private val DarkWarning: Color = Color(0xFFFFA000)
private val DarkOnWarning: Color = Color(0xFF000000)

private val DarkColorScheme =
    darkColorScheme(
        background = DarkBackground,
        onBackground = DarkOnBackground,
        surface = DarkBackground,
        onSurface = DarkOnBackground,
        error = DarkError,
        onError = DarkOnError,
    )

private val LightColorScheme =
    lightColorScheme(
        background = LightBackground,
        onBackground = LightOnBackground,
        surface = LightBackground,
        onSurface = LightOnBackground,
        error = LightError,
        onError = LightOnError,
    )

@Composable
fun getColorScheme(isDarkThemeEnabled: Boolean = isSystemInDarkTheme()) =
    when (isDarkThemeEnabled) {
        true -> DarkColorScheme
        false -> LightColorScheme
    }

@ConsistentCopyVisibility
data class ShakerColorScheme internal constructor(
    val background: Color = Color(0xFFFFFFFF),
    val onBackground: Color = Color(0xFF000000),
    val surface: Color = Color(0xFFFFFFFF),
    val onSurface: Color = Color(0xFF000000),
    val container: Color = Color(0xFFF1F1F1),
    val onContainer: Color = Color(0xFF333333),
    val success: Color = Color(0xFF4CAF50),
    val onSuccess: Color = Color(0xFFFFFFFF),
    val info: Color = Color(0xFF2196F3),
    val onInfo: Color = Color(0xFFFFFFFF),
    val warning: Color = Color(0xFFFFC107),
    val onWarning: Color = Color(0xFF000000),
    val error: Color = Color(0xFFF44336),
    val onError: Color = Color(0xFFFFFFFF),
)

// Light Color Scheme
internal val ShakerLightColorScheme =
    ShakerColorScheme(
        background = LightBackground,
        onBackground = LightOnBackground,
        surface = LightSurface,
        onSurface = LightOnSurface,
        container = LightContainer,
        onContainer = LightOnContainer,
        success = LightSuccess,
        onSuccess = LightOnSuccess,
        info = LightInfo,
        onInfo = LightOnInfo,
        warning = LightWarning,
        onWarning = LightOnWarning,
        error = LightError,
        onError = LightOnError,
    )

// Dark Color Scheme
internal val ShakerDarkColorScheme =
    ShakerColorScheme(
        background = DarkBackground,
        onBackground = DarkOnBackground,
        surface = DarkSurface,
        onSurface = DarkOnSurface,
        container = DarkContainer,
        onContainer = DarkOnContainer,
        success = DarkSuccess,
        onSuccess = DarkOnSuccess,
        info = DarkInfo,
        onInfo = DarkOnInfo,
        warning = DarkWarning,
        onWarning = DarkOnWarning,
        error = DarkError,
        onError = DarkOnError,
    )

internal fun getShakerColorScheme(isDarkThemeEnabled: Boolean) = if (isDarkThemeEnabled) ShakerDarkColorScheme else ShakerLightColorScheme

internal val LocalShakerColorScheme = staticCompositionLocalOf { ShakerColorScheme() }

internal fun provideShakerColorScheme(isDarkModeEnabled: Boolean) = LocalShakerColorScheme provides getShakerColorScheme(isDarkModeEnabled)
