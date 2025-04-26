package com.gorczycait.backbones.presentation.common.components.textfield.base

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.lerp
import androidx.compose.ui.unit.LayoutDirection.Ltr
import androidx.compose.ui.unit.dp
import com.gorczycait.backbones.presentation.common.components.VerticalSpacer

/**
 * Internal content of an input field. Handles showing of the:
 * @param content: the value of the field, e.g. BasicTextField
 * @param label: a label which acts as hint and shifts (animating) when field is focused or filled
 * @param placeholder: like label, but disappears instead of shifting when field is filled
 * @param leadingContent: placed at start and NOT wrapped by [contentPadding]
 * @param trailingContent: placed at end and NOT wrapped by [contentPadding]
 *
 * @param contentPadding wraps around [content], [label] and [placeholder]
 * @param textStyles takes care of [TextStyle]s for content, label, shifted label and placeholder
 *
 * [focused] and [empty] define the state of this composable
 *
 */
@Composable
internal fun InputInternalContent(
    label: AnnotatedString?,
    placeholder: AnnotatedString?,
    leadingContent: (@Composable () -> Unit)?,
    trailingContent: (@Composable () -> Unit)?,
    contentPadding: PaddingValues,
    textStyles: InputTextStyles,
    focused: Boolean,
    empty: Boolean,
    singleLine: Boolean,
    content: @Composable () -> Unit,
) {
    Row(
        verticalAlignment = if (singleLine) CenterVertically else Top,
    ) {
        leadingContent?.invoke()
        Box(
            contentAlignment = CenterStart,
            modifier = Modifier
                .padding(
                    when {
                        singleLine -> contentPadding
                        else -> PaddingValues(
                            start = contentPadding.calculateStartPadding(Ltr),
                            end = contentPadding.calculateEndPadding(Ltr),
                            top = contentPadding.calculateTopPadding() + 16.dp,
                            bottom = contentPadding.calculateBottomPadding() + 16.dp,
                        )
                    },
                )
                .weight(1f),
        ) {
            if (label == null && placeholder != null) {
                Placeholder(
                    visible = empty,
                    textStyle = textStyles.placeholderStyle,
                    placeholder = placeholder,
                    singleLine = singleLine,
                )
            }
            ContentWithShiftingLabel(
                label = label?.let {
                    @Composable {
                        Text(
                            text = it,
                            modifier = Modifier.fillMaxWidth(),
                            // PC: Fixes issues with measuring AnnotatedString width.
                            // Without fillMaxWidth some labels might get broken into two lines.
                        )
                    }
                },
                content = content,
                empty = empty,
                focused = focused,
                textStyles = textStyles,
            )
        }
        trailingContent?.invoke()
    }
}

/**
 * The text styles used in [ContentWithShiftingLabel]
 *
 * @param labelStyle the style of label while the input is empty
 * @param labelShiftedStyle the style when there is a value and the label is shifted upwards
 * @param valueStyle the style of the value in the input
 */
@Immutable
data class InputTextStyles(
    val labelStyle: TextStyle,
    val placeholderStyle: TextStyle,
    val labelShiftedStyle: TextStyle,
    val valueStyle: TextStyle,
    val additionalHelperTextStyle: TextStyle,
    val additionalErrorTextStyle: TextStyle,
)

@Composable
private fun Placeholder(
    visible: Boolean,
    textStyle: TextStyle,
    placeholder: AnnotatedString,
    singleLine: Boolean,
    maxLines: Int = Int.MAX_VALUE,
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        ProvideTextStyle(value = textStyle) {
            Text(
                text = placeholder,
                maxLines = if (singleLine) 1 else maxLines,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

/**
 * Takes care of shifting the [label] and showing the [content] as part of a transition between
 * empty/focused/filled states as defined by [focused] and [empty]
 */
@Composable
private fun ContentWithShiftingLabel(
    label: (@Composable () -> Unit)?,
    empty: Boolean,
    focused: Boolean,
    textStyles: InputTextStyles,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val focusedOrFilled = focused || !empty

    val transition = updateTransition(focusedOrFilled, label = "ShiftingInputContentState")

    val labelProgress by transition.animateFloat(
        label = "LabelProgress",
        transitionSpec = { tween(durationMillis = 130) },
    ) {
        when (it) {
            false -> 0f
            true -> 1f
        }
    }

    val spacingSize by transition.animateDp(
        label = "SpacingSize",
        transitionSpec = { tween(durationMillis = 130) },
    ) {
        when (it) {
            false -> 0.dp
            true -> 4.dp
        }
    }

    val labelStyle = lerp(
        textStyles.labelStyle,
        textStyles.labelShiftedStyle,
        labelProgress,
    )

    Column(
        modifier = modifier,
        verticalArrangement = Center,
    ) {
        label?.let {
            ProvideTextStyle(value = labelStyle) { it() }
        }
        VerticalSpacer(spacingSize)
        Box(
            modifier = Modifier
                .alpha(labelProgress)
                .layout { measurable, constraints ->
                    val placeable = measurable.measure(constraints)
                    layout(placeable.width, (placeable.height * labelProgress).toInt()) {
                        placeable.placeRelative(0, 0, 0f)
                    }
                },
        ) {
            ProvideTextStyle(value = textStyles.valueStyle) {
                content()
            }
        }
    }
}
