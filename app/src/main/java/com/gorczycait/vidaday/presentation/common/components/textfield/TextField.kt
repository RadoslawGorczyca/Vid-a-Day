package com.gorczycait.vidaday.presentation.common.components.textfield

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gorczycait.vidaday.presentation.common.components.textfield.base.AdditionalContentData
import com.gorczycait.vidaday.presentation.common.components.textfield.base.BaseTextField
import com.gorczycait.vidaday.presentation.common.components.textfield.base.InputColorsByState
import com.gorczycait.vidaday.presentation.common.components.textfield.base.InputTextStyles
import com.gorczycait.vidaday.presentation.common.theme.BackbonesTheme

@Composable
fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    enabled: Boolean = true,
    error: AdditionalContentData? = null,
    helper: AdditionalContentData? = null,
    minHeight: Dp = TextFieldStyles.minHeight,
    readOnly: Boolean = false,
    markAsObligatory: Boolean = false,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    shape: Shape = TextFieldStyles.shape,
    contentPadding: PaddingValues = PaddingValues(horizontal = 20.dp),
    textStyles: @Composable (Boolean, Boolean) -> InputTextStyles = { enabled, error ->
        TextFieldStyles.defaultTextFieldStyles(enabled, error)
    },
    borderColor: InputColorsByState = TextFieldStyles.defaultBorderColors(),
    backgroundColor: InputColorsByState = TextFieldStyles.defaultBackgroundColors(),
    charCounterContent: (@Composable ColumnScope.() -> Unit)? = null,
    requestFocus: Boolean = false,
) {
    BaseTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = obligatoryAnnotatedString(label, markAsObligatory && !readOnly, enabled),
        placeholder = obligatoryAnnotatedString(
            label = placeholder,
            markAsObligatory = markAsObligatory && !readOnly,
            enabled = enabled,
        ),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        enabled = enabled,
        error = error,
        helper = helper,
        minHeight = minHeight,
        readOnly = readOnly,
        leadingContent = leadingContent?.takeUnless { readOnly },
        trailingContent = trailingContent?.takeUnless { readOnly },
        visualTransformation = visualTransformation,
        shape = shape,
        textStyles = textStyles,
        contentPadding = contentPadding,
        borderColor = borderColor,
        backgroundColor = backgroundColor,
        cursorColor = BackbonesTheme.colors.focus,
        charCounterContent = charCounterContent,
        requestFocus = requestFocus,
    )
}
