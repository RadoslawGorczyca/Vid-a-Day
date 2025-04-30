package com.gorczycait.vidaday.presentation.common.theme

import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.LocalShimmerTheme
import com.valentinilk.shimmer.defaultShimmerTheme
import com.valentinilk.shimmer.shimmerSpec

private val LocalColors = compositionLocalOf<BackbonesColors> { BackbonesLightColors }
private val LocalTypography =
    staticCompositionLocalOf { BackbonesTypography }

object BackbonesTheme {
    val colors: BackbonesColors @Composable get() = LocalColors.current
    val typography: BackbonesTypography @Composable get() = LocalTypography.current
}

@Composable
fun BackbonesTheme(
    colorScheme: ColorScheme = createColors(),
    typography: Typography = createTypography(),
    content: @Composable () -> Unit,
) {
    val backbonesColors = if (isSystemInDarkTheme()) BackbonesDarkColors else BackbonesLightColors
    val shimmerTheme = defaultShimmerTheme.copy(
        animationSpec = infiniteRepeatable(
            animation = shimmerSpec(durationMillis = 2500),
        ),
    )
    CompositionLocalProvider(
        LocalColors provides backbonesColors,
        LocalContentColor provides backbonesColors.onSurface,
        LocalTypography provides BackbonesTypography,
        LocalShimmerTheme provides shimmerTheme,
        LocalMinimumInteractiveComponentSize provides 40.dp,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            content = content,
        )
    }
}

@Composable
private fun createColors() = when {
    isSystemInDarkTheme() -> darkColorScheme(
        primary = BackbonesDarkColors.primary,
        onPrimary = BackbonesDarkColors.onPrimary,
        secondary = BackbonesDarkColors.accent,
        tertiary = BackbonesDarkColors.accentSecondary,
        background = BackbonesDarkColors.background,
        surface = BackbonesDarkColors.surface,
        onSurface = BackbonesDarkColors.onSurface,
        surfaceVariant = BackbonesDarkColors.surfaceSecondary,
        onSurfaceVariant = BackbonesDarkColors.onSurfaceSecondary,
        error = BackbonesDarkColors.error,
        outline = BackbonesDarkColors.border,
        scrim = BackbonesDarkColors.onSurface.copy(alpha = 0.6f),
    )

    else -> lightColorScheme(
        primary = BackbonesLightColors.primary,
        onPrimary = BackbonesLightColors.onPrimary,
        secondary = BackbonesLightColors.accent,
        tertiary = BackbonesLightColors.accentSecondary,
        background = BackbonesLightColors.background,
        surface = BackbonesLightColors.surface,
        onSurface = BackbonesLightColors.onSurface,
        surfaceVariant = BackbonesLightColors.surfaceSecondary,
        onSurfaceVariant = BackbonesLightColors.onSurfaceSecondary,
        error = BackbonesLightColors.error,
        outline = BackbonesLightColors.border,
        scrim = BackbonesDarkColors.onSurface.copy(alpha = 0.6f),
    )
}

@Composable
private fun createTypography() = Typography(
    displayLarge = BackbonesTypography.DISPLAY_XL_EXTRABOLD,
    displayMedium = BackbonesTypography.DISPLAY_L_EXTRABOLD,
    displaySmall = BackbonesTypography.DISPLAY_M_BOLD,
    headlineLarge = BackbonesTypography.HEADER_M_SEMIBOLD,
    headlineMedium = BackbonesTypography.HEADER_S_BOLD,
    headlineSmall = BackbonesTypography.HEADER_XS_BOLD,
    titleLarge = BackbonesTypography.Header_M_SemiBold,
    titleMedium = BackbonesTypography.Header_S_Medium,
    titleSmall = BackbonesTypography.Header_XS_Medium,
    bodyLarge = BackbonesTypography.Body_L_Medium,
    bodyMedium = BackbonesTypography.Body_M_Medium,
    bodySmall = BackbonesTypography.Body_S_Medium,
    labelLarge = BackbonesTypography.HEADER_XS_MEDIUM,
    labelMedium = BackbonesTypography.STICKER,
    labelSmall = BackbonesTypography.Badge,
)
