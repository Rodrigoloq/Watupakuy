package com.rodrigoloq.watupakuy.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val WatupakuyColorScheme = lightColorScheme(
    primary = WatupakuyNavy,
    onPrimary = WatupakuySurface,
    primaryContainer = WatupakuySkySoft,
    onPrimaryContainer = WatupakuyNavy,
    secondary = WatupakuyTeal,
    onSecondary = WatupakuySurface,
    secondaryContainer = WatupakuyTealLight,
    onSecondaryContainer = WatupakuyNavy,
    tertiary = WatupakuySky,
    background = WatupakuyMist,
    onBackground = WatupakuyOnSurface,
    surface = WatupakuySurface,
    onSurface = WatupakuyOnSurface,
    surfaceVariant = WatupakuySurfaceVariant,
    onSurfaceVariant = WatupakuyOnSurfaceMuted,
    surfaceContainerLowest = WatupakuySurface,
    surfaceContainerLow = WatupakuySurface,
    surfaceContainer = WatupakuySurface,
    surfaceContainerHigh = WatupakuySurfaceVariant,
    outline = WatupakuyOutline,
    outlineVariant = WatupakuyOutline,
    error = WatupakuyError,
    errorContainer = WatupakuyErrorContainer
)

@Composable
fun WatupakuyTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = WatupakuyColorScheme,
        typography = WatupakuyTypography,
        shapes = WatupakuyShapes,
        content = content
    )
}