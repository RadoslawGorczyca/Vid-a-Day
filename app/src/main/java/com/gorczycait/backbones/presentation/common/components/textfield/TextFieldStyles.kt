package com.gorczycait.backbones.presentation.common.components.textfield

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.gorczycait.backbones.R
import com.gorczycait.backbones.presentation.common.components.textfield.base.AdditionalContentData
import com.gorczycait.backbones.presentation.common.components.textfield.base.InputColorsByState
import com.gorczycait.backbones.presentation.common.components.textfield.base.InputTextStyles
import com.gorczycait.backbones.presentation.common.theme.BackbonesTheme

@Composable
fun obligatoryAnnotatedString(
    label: String?,
    markAsObligatory: Boolean,
    enabled: Boolean,
): AnnotatedString? = when {
    label != null && markAsObligatory -> {
        val obligatoryCharColor = if (enabled) BackbonesTheme.colors.accent else BackbonesTheme.colors.accentSecondary
        obligatoryCharAnnotatedString(color = obligatoryCharColor) {
            append(label)
        }
    }

    label != null -> AnnotatedString(label)
    else -> null
}

@Composable
fun obligatoryCharAnnotatedString(
    color: Color = BackbonesTheme.colors.accent,
    builder: AnnotatedString.Builder.() -> Unit,
) = buildAnnotatedString {
    withStyle(SpanStyle(color = color)) {
        append('*')
    }
    builder()
}

object TextFieldStyles {

    val minHeight = 56.dp

    val shape = RectangleShape

    @Composable
    fun defaultBorderColors() = InputColorsByState(
        defaultColor = BackbonesTheme.colors.border,
        disabledColor = BackbonesTheme.colors.border,
        focusedColor = BackbonesTheme.colors.borderActive,
        errorColor = BackbonesTheme.colors.error,
        readOnlyColor = BackbonesTheme.colors.border,
    )

    @Composable
    fun defaultBackgroundColors() = InputColorsByState(
        defaultColor = BackbonesTheme.colors.surface,
        disabledColor = BackbonesTheme.colors.surfaceSecondary,
        focusedColor = BackbonesTheme.colors.surface,
        errorColor = BackbonesTheme.colors.surface,
        readOnlyColor = BackbonesTheme.colors.surface,
    )

    @Composable
    fun defaultTextFieldStyles(
        enabled: Boolean,
        error: Boolean,
    ): InputTextStyles {
        val labelColor = when {
            enabled -> BackbonesTheme.colors.onSurfaceSecondary
            else -> BackbonesTheme.colors.onSurfaceTertiary
        }
        return InputTextStyles(
            labelStyle = BackbonesTheme.typography.Header_S_Medium.copy(
                color = labelColor,
            ),
            placeholderStyle = BackbonesTheme.typography.Header_S_Medium.copy(
                color = labelColor,
            ),
            labelShiftedStyle = BackbonesTheme.typography.Header_XS_Medium.copy(
                color = when {
                    error -> BackbonesTheme.colors.error
                    else -> labelColor
                },
            ),
            valueStyle = BackbonesTheme.typography.Header_S_Medium.copy(
                color = when {
                    enabled -> BackbonesTheme.colors.onSurface
                    else -> BackbonesTheme.colors.onSurfaceSecondary
                },
            ),
            additionalHelperTextStyle = BackbonesTheme.typography.Body_XS_Medium,
            additionalErrorTextStyle = BackbonesTheme.typography.Body_XS_Medium,
        )
    }

    @Composable
    fun defaultHelper(text: String) = AdditionalContentData(
        text = text,
        icon = R.drawable.ic_info,
        textColor = BackbonesTheme.colors.onSurfaceSecondary,
    )

    @Composable
    fun defaultError(text: String) = AdditionalContentData(
        text = text,
        icon = R.drawable.ic_error,
        textColor = BackbonesTheme.colors.error,
    )
}
