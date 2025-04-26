package com.gorczycait.backbones.presentation.common.components.textfield.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp

/**
 * [BaseClickableTextField] has all the features of the [BaseTextField], except instead of handling text
 * input, it has a nullable [value] and handles [onClick]. Can be used for delegating controls,
 * like a dropdown which shows a BottomSheet or a Dialog based calendar.
 *
 * Can show [error] and [helper].
 *
 * Can have a filled or outlined style as controlled by [borderColor] and [backgroundColor].
 *
 * Can contain [leadingContent] and [trailingContent].
 *
 * Can show either a shifting [label] or a [placeholder].
 */
@Composable
fun BaseClickableTextField(
    onClick: () -> Unit,
    value: String?,
    label: AnnotatedString?,
    placeholder: AnnotatedString?,
    error: AdditionalContentData?,
    helper: AdditionalContentData?,
    enabled: Boolean,
    readOnly: Boolean,
    leadingContent: (@Composable () -> Unit)?,
    trailingContent: (@Composable () -> Unit)?,
    shape: Shape,
    minHeight: Dp,
    contentPadding: PaddingValues,
    textStyles: @Composable (Boolean, Boolean) -> InputTextStyles,
    borderColor: InputColorsByState?,
    backgroundColor: InputColorsByState?,
    modifier: Modifier = Modifier,
) {
    val currentTextStyles = textStyles(enabled, error != null)
    Box(modifier = modifier) {
        InputDecoration(
            borderColor = borderColor,
            backgroundColor = backgroundColor,
            onClick = onClick,
            enabled = enabled,
            readOnly = readOnly,
            error = error,
            shape = shape,
            singleLine = true,
            minHeight = minHeight,
            helper = helper,
            additionalHelperTextStyle = currentTextStyles.additionalHelperTextStyle,
            additionalErrorTextStyle = currentTextStyles.additionalErrorTextStyle,
            charCounterContent = null,
            isFocused = false,
            internalContent = {
                InputInternalContent(
                    contentPadding = contentPadding,
                    focused = false,
                    empty = value.isNullOrEmpty(),
                    textStyles = currentTextStyles,
                    content = {
                        Text(
                            text = value ?: "",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    },
                    leadingContent = leadingContent,
                    trailingContent = trailingContent,
                    label = label,
                    placeholder = placeholder,
                    singleLine = true,
                )
            },
        )
    }
}
