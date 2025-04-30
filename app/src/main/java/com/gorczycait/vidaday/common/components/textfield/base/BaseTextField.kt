package com.gorczycait.vidaday.common.components.textfield.base

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp

/**
 * [BaseTextField] is a Design System alternative to material TextField.
 * Key differences:
 * - based on the same foundation as [BaseClickableTextField] to maintain consistent styling and behavior,
 * - handles [error] and [helper],
 * - height and padding can be easily controlled by [minHeight] and [contentPadding],
 * - [readOnly] properly handles focused state of label (doesn't shift),
 * - filled or outlined style is controlled by [borderColor] and [backgroundColor]
 * instead of relying on separate Composables
 *
 * Key similarities:
 * - can show a shifting [label] or a disappearing [placeholder]
 * - handles [enabled], [singleLine], [maxLines], [readOnly], [keyboardActions],
 * [keyboardOptions], [visualTransformation]
 * - handles [leadingContent] and [trailingContent] (and their padding independently from [contentPadding])
 *
 */
@Composable
fun BaseTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: AnnotatedString?,
    placeholder: AnnotatedString?,
    error: AdditionalContentData?,
    helper: AdditionalContentData?,
    enabled: Boolean,
    singleLine: Boolean,
    maxLines: Int,
    readOnly: Boolean,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    visualTransformation: VisualTransformation,
    leadingContent: (@Composable () -> Unit)?,
    trailingContent: (@Composable () -> Unit)?,
    shape: Shape,
    minHeight: Dp,
    contentPadding: PaddingValues,
    textStyles: @Composable (Boolean, Boolean) -> InputTextStyles,
    borderColor: InputColorsByState?,
    backgroundColor: InputColorsByState?,
    charCounterContent: (@Composable ColumnScope.() -> Unit)?,
    cursorColor: Color,
    modifier: Modifier = Modifier,
    requestFocus: Boolean = false,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusRequester = remember { FocusRequester() }

    val currentTextStyles = textStyles(enabled, error != null)

    val box = @Composable { innerTextField: @Composable () -> Unit ->
        InputDecoration(
            borderColor = borderColor,
            backgroundColor = backgroundColor,
            error = error,
            helper = helper,
            enabled = enabled,
            readOnly = readOnly,
            shape = shape,
            singleLine = singleLine,
            minHeight = minHeight,
            isFocused = isFocused && !readOnly,
            additionalHelperTextStyle = currentTextStyles.additionalHelperTextStyle,
            additionalErrorTextStyle = currentTextStyles.additionalErrorTextStyle,
            charCounterContent = charCounterContent,
            onClick = null,
            internalContent = {
                InputInternalContent(
                    contentPadding = contentPadding,
                    focused = isFocused && !readOnly,
                    empty = value.isEmpty(),
                    textStyles = currentTextStyles,
                    content = innerTextField,
                    leadingContent = leadingContent,
                    trailingContent = trailingContent,
                    label = label,
                    placeholder = placeholder,
                    singleLine = singleLine,
                )
            },
        )
    }

    var textFieldValueState by remember {
        mutableStateOf(
            TextFieldValue(
                text = value,
                selection = when {
                    value.isEmpty() -> TextRange.Zero
                    else -> TextRange(value.length)
                },
            ),
        )
    }

    val updateTextFieldValueSelection by remember(value) { derivedStateOf { value != textFieldValueState.text } }
    LaunchedEffect(key1 = updateTextFieldValueSelection) {
        if (updateTextFieldValueSelection) {
            textFieldValueState =
                textFieldValueState.copy(text = value, selection = TextRange(value.length))
        }
    }
    LaunchedEffect(key1 = requestFocus) {
        if (requestFocus) {
            focusRequester.requestFocus()
        }
    }

    val textFieldValue = textFieldValueState.copy(text = value)

    var lastTextValue by remember(value) { mutableStateOf(value) }

    BasicTextField(
        value = textFieldValue,
        enabled = enabled,
        onValueChange = { newTextFieldValueState ->
            textFieldValueState = newTextFieldValueState

            val stringChangedSinceLastInvocation = lastTextValue != newTextFieldValueState.text
            lastTextValue = newTextFieldValueState.text

            if (stringChangedSinceLastInvocation) {
                onValueChange(newTextFieldValueState.text)
            }
        },
        modifier = modifier.focusRequester(focusRequester),
        readOnly = readOnly,
        singleLine = singleLine,
        maxLines = maxLines,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        textStyle = currentTextStyles.valueStyle,
        interactionSource = interactionSource,
        visualTransformation = visualTransformation,
        decorationBox = box,
        cursorBrush = SolidColor(cursorColor),
    )
}
