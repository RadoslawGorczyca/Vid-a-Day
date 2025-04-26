package com.gorczycait.backbones.presentation.common.components.textfield.base

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gorczycait.backbones.presentation.common.components.IconWithText
import com.gorczycait.backbones.presentation.common.components.textfield.TextFieldStyles

/**
 * Base Composable for inputs container - a container for TextFields, Dropdowns etc.
 *
 * Responsible for:
 * - drawing and managing the state of the outer appearance of a form input
 * - showing errors and helper texts
 * - optionally receiving clicks for e.g. dropdowns
 *
 * @param [internalContent] is the main content of the Composable; can be dynamic TextField or e.g.
 * static label for Dropdowns.
 */
@Composable
fun InputDecoration(
    borderColor: InputColor?,
    backgroundColor: InputColor?,
    onClick: (() -> Unit)?,
    error: AdditionalContentData?,
    helper: AdditionalContentData?,
    enabled: Boolean,
    readOnly: Boolean,
    isFocused: Boolean,
    singleLine: Boolean,
    minHeight: Dp,
    shape: Shape,
    additionalHelperTextStyle: TextStyle,
    additionalErrorTextStyle: TextStyle,
    charCounterContent: (@Composable ColumnScope.() -> Unit)?,
    internalContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isError = error != null

    val background = backgroundColor
        ?.color(isError, enabled, isFocused, readOnly)?.value
        ?: Color.Transparent

    val borderStroke = borderColor
        ?.color(isError, enabled, isFocused, readOnly)?.value
        ?.let { BorderStroke(1.dp, it) }

    Column(
        modifier = Modifier.animateContentSize(),
    ) {
        Surface(
            shape = shape,
            color = background,
            border = borderStroke,
            modifier = Modifier
                .defaultMinSize(minHeight = minHeight)
                .then(modifier)
                .clip(shape)
                .then(onClick?.let { Modifier.clickable(enabled = enabled, onClick = it) } ?: Modifier),
        ) {
            Column(
                verticalArrangement = if (singleLine) Center else SpaceBetween,
            ) {
                internalContent()
                charCounterContent?.invoke(this)
            }
        }
        AnimatedVisibility(
            visible = error != null,
        ) {
            if (error != null) {
                TextFieldAdditionalContent(
                    data = error,
                    topPadding = 8.dp,
                    textStyle = additionalErrorTextStyle,
                )
            }
        }
        AnimatedVisibility(
            visible = helper != null,
        ) {
            if (helper != null) {
                TextFieldAdditionalContent(
                    data = helper,
                    topPadding = if (error != null) 4.dp else 8.dp,
                    textStyle = additionalHelperTextStyle,
                )
            }
        }
    }
}

@Composable
fun TextFieldAdditionalContent(
    data: AdditionalContentData,
    topPadding: Dp,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextFieldStyles
        .defaultTextFieldStyles(enabled = true, error = true)
        .additionalErrorTextStyle,
) {
    IconWithText(
        text = data.text,
        modifier = modifier.padding(top = topPadding),
        verticalAlignment = Top,
        textStyle = textStyle,
        textColor = data.textColor,
        icon = data.icon,
        iconTint = data.iconTint,
    )
}

@Immutable
data class AdditionalContentData(
    val text: String,
    val textColor: Color,
    @DrawableRes val icon: Int,
    val iconTint: Color = Color.Unspecified,
)

@Immutable
data class InputColorsByState(
    val defaultColor: Color,
    val disabledColor: Color,
    val focusedColor: Color,
    val errorColor: Color,
    val readOnlyColor: Color,
) : InputColor {
    @Composable
    override fun color(
        error: Boolean,
        enabled: Boolean,
        focused: Boolean,
        readOnly: Boolean,
    ): State<Color> =
        rememberUpdatedState(
            when {
                error -> errorColor
                !enabled -> disabledColor
                focused -> focusedColor
                readOnly -> readOnlyColor
                else -> defaultColor
            },
        )
}

@Stable
interface InputColor {
    @Composable
    fun color(error: Boolean, enabled: Boolean, focused: Boolean, readOnly: Boolean): State<Color>
}
