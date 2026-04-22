package com.songseed.songseedclaude.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = AccentGold,
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF3A2E00),
    onPrimaryContainer = AccentGold,
    secondary = AccentAmber,
    onSecondary = Color.Black,
    background = BackgroundDark,
    onBackground = TextLight,
    surface = SurfaceDark,
    onSurface = TextLight,
    surfaceVariant = SurfaceElevated,
    onSurfaceVariant = TextMuted,
    outline = Color(0xFF555555)
)

@Composable
fun SongSeedTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
