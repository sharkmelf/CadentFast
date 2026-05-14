package com.cadent.cadentfast.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val CadentDarkColors = darkColorScheme(
    primary = Copper,
    onPrimary = Charcoal,
    secondary = BrushedGold,
    onSecondary = Charcoal,
    tertiary = Ember,
    onTertiary = Parchment,
    background = Charcoal,
    onBackground = Parchment,
    surface = CharcoalRaised,
    onSurface = Parchment,
    surfaceVariant = SmokedClay,
    onSurfaceVariant = ParchmentDim,
    error = RestrainedRed,
    onError = Parchment,
)

@Composable
fun CadentFastTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = CadentDarkColors,
        typography = CadentTypography,
        content = content,
    )
}
