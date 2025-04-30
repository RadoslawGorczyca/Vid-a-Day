package com.gorczycait.vidaday.presentation.common.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.airbnb.android.showkase.annotation.ShowkaseColor

interface BackbonesColors {

    val primary: Color
    val accent: Color
    val accentSecondary: Color

    val background: Color
    val surface: Color
    val surfaceSecondary: Color
    val surfaceTertiary: Color
    val surfaceQuaternary: Color
    val surfaceQuinary: Color
    val surfaceSkeleton: Color

    val onPrimary: Color
    val onSurface: Color
    val onSurfaceSecondary: Color
    val onSurfaceTertiary: Color
    val onSurfaceQuaternary: Color
    val onSurfaceQuinary: Color
    val onScrim: Color

    val success: Color
    val star: Color
    val focus: Color
    val info: Color
    val warning: Color
    val error: Color

    val border: Color
    val borderSecondary: Color
    val borderTertiary: Color
    val borderActive: Color

    val primaryFixed get() = Color(0xFF17222E)

    val gradientPrimary
        get() = Brush.verticalGradient(
            0f to primaryFixed.copy(alpha = .4f),
            1f to Color.Transparent,
        )
}

object BackbonesLightColors : BackbonesColors {

    @ShowkaseColor
    override val primary = Color(0xFF17222E)

    @ShowkaseColor
    override val accent = Color(0xFFB51F1C)

    @ShowkaseColor
    override val accentSecondary = Color(0xFFB51F1C)

    @ShowkaseColor
    override val background = Color(0xFFFFFFFF)

    @ShowkaseColor
    override val surface = Color(0xFFFFFFFF)

    @ShowkaseColor
    override val surfaceSecondary = Color(0xFFF3F5F6)

    @ShowkaseColor
    override val surfaceTertiary = Color(0xFFE9ECEE)

    @ShowkaseColor
    override val surfaceQuaternary = Color(0xFF17222E)

    @ShowkaseColor
    override val surfaceQuinary = Color(0xFFCEDFF3)

    @ShowkaseColor
    override val surfaceSkeleton = Color(0xFFE9ECEE)

    @ShowkaseColor
    override val onPrimary = Color(0xFFFFFFFF)

    @ShowkaseColor
    override val onSurface = Color(0xFF17222E)

    @ShowkaseColor
    override val onSurfaceSecondary = Color(0xFF5F6871)

    @ShowkaseColor
    override val onSurfaceTertiary = Color(0xFF878D94)

    @ShowkaseColor
    override val onSurfaceQuaternary = Color(0xFFADB2B8)

    @ShowkaseColor
    override val onSurfaceQuinary = Color(0xFFD4D7DB)

    @ShowkaseColor
    override val onScrim = Color(0xFFFFFFFF)

    @ShowkaseColor
    override val success = Color(0xFF00875A)

    @ShowkaseColor
    override val star = Color(0xFFFFC800)

    @ShowkaseColor
    override val focus = Color(0xFF2666B0)

    @ShowkaseColor
    override val info = Color(0xFF1C7ACC)

    @ShowkaseColor
    override val warning = Color(0xFFEB8A00)

    @ShowkaseColor
    override val error = Color(0xFFDF3A16)

    @ShowkaseColor
    override val border = Color(0xFFD4D7DB)

    @ShowkaseColor
    override val borderSecondary = Color(0xFFE9ECEE)

    @ShowkaseColor
    override val borderTertiary = Color(0xFFFFFFFF)

    @ShowkaseColor
    override val borderActive = Color(0xFF17222E)
}

object BackbonesDarkColors : BackbonesColors {

    @ShowkaseColor
    override val primary = Color(0xFFFFFFFF)

    @ShowkaseColor
    override val accent = Color(0xFFB51F1C)

    @ShowkaseColor
    override val accentSecondary = Color(0xFFF76464)

    @ShowkaseColor
    override val background = Color(0xFF17222E)

    @ShowkaseColor
    override val surface = Color(0xFF17222E)

    @ShowkaseColor
    override val surfaceSecondary = Color(0xFF0F161E)

    @ShowkaseColor
    override val surfaceTertiary = Color(0xFFE9ECEE)

    @ShowkaseColor
    override val surfaceQuaternary = Color(0xFF39434D)

    @ShowkaseColor
    override val surfaceQuinary = Color(0xFF5C718A)

    @ShowkaseColor
    override val surfaceSkeleton = Color(0xFF39434D)

    @ShowkaseColor
    override val onPrimary = Color(0xFF17222E)

    @ShowkaseColor
    override val onSurface = Color(0xFFFFFFFF)

    @ShowkaseColor
    override val onSurfaceSecondary = Color(0xFFB0B4B8)

    @ShowkaseColor
    override val onSurfaceTertiary = Color(0xFF616971)

    @ShowkaseColor
    override val onSurfaceQuaternary = Color(0xFF616971)

    @ShowkaseColor
    override val onSurfaceQuinary = Color(0xFF616971)

    @ShowkaseColor
    override val onScrim = Color(0xFFFFFFFF)

    @ShowkaseColor
    override val success = Color(0xFF0EAF79)

    @ShowkaseColor
    override val star = Color(0xFFFFC800)

    @ShowkaseColor
    override val focus = Color(0xFF2666B0)

    @ShowkaseColor
    override val info = Color(0xFF1C7ACC)

    @ShowkaseColor
    override val warning = Color(0xFFFFB661)

    @ShowkaseColor
    override val error = Color(0xFFEE5F40)

    @ShowkaseColor
    override val border = Color(0xFF616971)

    @ShowkaseColor
    override val borderSecondary = Color(0xFF39434D)

    @ShowkaseColor
    override val borderTertiary = Color(0xFF17222E)

    @ShowkaseColor
    override val borderActive = Color(0xFFFFFFFF)
}
