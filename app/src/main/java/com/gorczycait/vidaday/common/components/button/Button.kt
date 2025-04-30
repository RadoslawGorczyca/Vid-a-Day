package com.gorczycait.vidaday.common.components.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.gorczycait.vidaday.common.components.button.base.BaseButton
import com.gorczycait.vidaday.common.components.button.base.BaseButtonShape
import com.gorczycait.vidaday.common.theme.BackbonesTheme

val ButtonHeight = 56.dp

@Composable
fun AccentButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    @DrawableRes leadingIcon: Int? = null,
    @DrawableRes trailingIcon: Int? = null,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = BackbonesTheme.colors.accent,
            contentColor = BackbonesTheme.colors.onScrim,
            disabledContainerColor = BackbonesTheme.colors.onSurfaceQuaternary,
            disabledContentColor = BackbonesTheme.colors.onPrimary,
        ),
        border = null,
        loading = loading,
        leadingIcon = leadingIcon,
        label = label,
        trailingIcon = trailingIcon,
        contentBrush = null,
    )
}

@Composable
fun PrimaryButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    @DrawableRes leadingIcon: Int? = null,
    @DrawableRes trailingIcon: Int? = null,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = BackbonesTheme.colors.primary,
            contentColor = BackbonesTheme.colors.onPrimary,
            disabledContainerColor = BackbonesTheme.colors.onSurfaceQuaternary,
            disabledContentColor = BackbonesTheme.colors.onPrimary,
        ),
        border = null,
        loading = loading,
        leadingIcon = leadingIcon,
        label = label,
        trailingIcon = trailingIcon,
        contentBrush = null,
    )
}

@Composable
fun SecondaryButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    @DrawableRes leadingIcon: Int? = null,
    @DrawableRes trailingIcon: Int? = null,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = BackbonesTheme.colors.onSurface,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = BackbonesTheme.colors.onSurfaceTertiary,
        ),
        border = BorderStroke(
            width = 1.dp,
            color = if (enabled) BackbonesTheme.colors.borderActive else BackbonesTheme.colors.border,
        ),
        loading = loading,
        leadingIcon = leadingIcon,
        label = label,
        trailingIcon = trailingIcon,
        contentBrush = null,
    )
}

@Composable
private fun Button(
    onClick: () -> Unit,
    enabled: Boolean,
    colors: ButtonColors,
    border: BorderStroke?,
    loading: Boolean,
    @DrawableRes leadingIcon: Int?,
    label: String,
    @DrawableRes trailingIcon: Int?,
    contentBrush: Brush?,
    modifier: Modifier = Modifier,
) {
    BaseButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = BaseButtonShape(
            height = ButtonHeight,
            shape = RectangleShape,
        ),
        colors = colors,
        border = border,
        contentPadding = PaddingValues(
            horizontal = 24.dp,
            vertical = 20.dp,
        ),
        loading = loading,
        leadingIcon = leadingIcon,
        label = label.uppercase(),
        textStyle = BackbonesTheme.typography.HEADER_XS_BOLD,
        trailingIcon = trailingIcon,
        contentBrush = contentBrush,
    )
}
