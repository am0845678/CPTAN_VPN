package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ParsPingCyberColorScheme = darkColorScheme(
    primary = CyberCrimsonMain,
    onPrimary = Color.White,
    primaryContainer = CyberCardBg,
    onPrimaryContainer = CyberCrimsonGlow,
    secondary = CyberCrimsonGlow,
    onSecondary = CyberBlackBg,
    background = CyberBlackBg,
    onBackground = CyberPureWhite,
    surface = CyberCharcoalSurface,
    onSurface = CyberPureWhite,
    surfaceVariant = CyberCardBg,
    onSurfaceVariant = CyberSilverText,
    outline = CyberGunmetalBorder,
    error = CyberErrorRed,
    onError = Color.White
)

@Composable
fun ParsPingTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = ParsPingCyberColorScheme,
        typography = Typography,
        content = content
    )
}
