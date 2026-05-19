package com.alifatma.firewatch.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/** Composition local for the warm card background token — switches on dark mode. */
val LocalCardContainerColor = staticCompositionLocalOf<Color> { SurfaceContainer }

// ── Light colour scheme (The Sentinel Aesthetic) ────────────────────────────
private val LightColors = lightColorScheme(
    primary                = Primary,
    onPrimary              = OnPrimary,
    primaryContainer       = PrimaryContainer,
    onPrimaryContainer     = OnPrimaryContainer,
    secondary              = Secondary,
    onSecondary            = OnSecondary,
    secondaryContainer     = SecondaryContainer,
    onSecondaryContainer   = OnSecondaryContainer,
    tertiary               = Tertiary,
    onTertiary             = OnTertiary,
    tertiaryContainer      = TertiaryContainer,
    onTertiaryContainer    = OnTertiaryContainer,
    surface                = Surface,
    onSurface              = OnSurface,
    surfaceVariant         = SurfaceContainerLow,
    onSurfaceVariant       = OnSurfaceVariant,
    surfaceContainer       = SurfaceContainer,
    surfaceContainerHigh   = SurfaceContainerHigh,
    surfaceContainerHighest= SurfaceContainerHighest,
    surfaceContainerLow    = SurfaceContainerLow,
    surfaceContainerLowest = SurfaceContainerLowest,
    outline                = Outline,
    outlineVariant         = OutlineVariant,
    error                  = Error,
    onError                = OnError,
)

// ── Dark colour scheme ───────────────────────────────────────────────────────
private val DarkColors = darkColorScheme(
    primary                = PrimaryContainer,   // lighter red on dark bg
    onPrimary              = OnPrimaryContainer,
    primaryContainer       = Primary,
    onPrimaryContainer     = OnPrimary,
    secondary              = SecondaryContainer,
    onSecondary            = OnSecondaryContainer,
    secondaryContainer     = Secondary,
    onSecondaryContainer   = OnSecondary,
    tertiary               = TertiaryContainer,
    onTertiary             = OnTertiaryContainer,
    tertiaryContainer      = Tertiary,
    onTertiaryContainer    = OnTertiary,
    surface                = DarkSurface,
    onSurface              = DarkOnSurface,
    surfaceVariant         = DarkSurfaceContainerLow,
    onSurfaceVariant       = DarkOnSurfaceVariant,
    surfaceContainer       = DarkSurfaceContainer,
    surfaceContainerHigh   = DarkSurfaceContainerHigh,
    surfaceContainerHighest= DarkSurfaceContainerHighest,
    surfaceContainerLow    = DarkSurfaceContainerLow,
    surfaceContainerLowest = DarkSurfaceContainerLowest,
    outline                = DarkOutline,
    outlineVariant         = DarkOutlineVariant,
    error                  = Color(0xFFFFB4AB),
    onError                = Color(0xFF690005),
)

// ── Shapes (sharp/professional per spec — md = 6dp, sm = 2dp, full = 50dp) ──
private val FireWatchShapes = Shapes(
    extraSmall = RoundedCornerShape(2.dp),   // sm: input field bottom-accent style
    small      = RoundedCornerShape(4.dp),
    medium     = RoundedCornerShape(6.dp),   // md: primary button corner
    large      = RoundedCornerShape(12.dp),
    extraLarge = RoundedCornerShape(50.dp),  // full: telemetry chips
)

// ── App theme entry point ────────────────────────────────────────────────────
@Composable
fun FireWatchTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalCardContainerColor provides if (darkTheme) DarkCardContainer else SurfaceContainer
    ) {
        MaterialTheme(
            colorScheme = if (darkTheme) DarkColors else LightColors,
            typography  = FireWatchTypography,
            shapes      = FireWatchShapes,
            content     = content
        )
    }
}
