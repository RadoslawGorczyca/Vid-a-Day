package com.gorczycait.vidaday.common.components.button.base

import androidx.annotation.DrawableRes
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize.Max
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.gorczycait.vidaday.common.components.HorizontalSpacer
import com.gorczycait.vidaday.common.components.LoadingLottieM

@Composable
fun BaseButton(
    onClick: () -> Unit,
    enabled: Boolean,
    shape: BaseButtonShape,
    colors: ButtonColors,
    border: BorderStroke?,
    contentPadding: PaddingValues,
    loading: Boolean,
    @DrawableRes leadingIcon: Int?,
    label: String,
    textStyle: TextStyle,
    @DrawableRes trailingIcon: Int?,
    contentBrush: Brush?,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    var labelContentWidth by remember(label) { mutableStateOf(0.dp) }
    val stateColors = ButtonColors(
        containerColor = if (enabled) colors.containerColor else colors.disabledContainerColor,
        contentColor = if (enabled) colors.contentColor else colors.disabledContentColor,
        disabledContainerColor = if (enabled) colors.containerColor else colors.disabledContainerColor,
        disabledContentColor = if (enabled) colors.contentColor else colors.disabledContentColor,
    )
    val contentColor = if (contentBrush != null) Color.Unspecified else stateColors.contentColor
    Button(
        onClick = onClick,
        modifier = modifier.then(Modifier.height(shape.height)),
        enabled = enabled && !loading,
        shape = shape.shape,
        colors = stateColors,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            focusedElevation = 0.dp,
            hoveredElevation = 0.dp,
            disabledElevation = 0.dp,
        ),
        border = border,
        contentPadding = contentPadding,
    ) {
        Crossfade(
            targetState = loading,
            modifier = Modifier
                .animateContentSize()
                .width(Max)
                .widthIn(min = labelContentWidth),
            label = "BaseButtonCrossfade",
        ) { loading ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                if (loading) {
                    LoadingLottieM(color = stateColors.contentColor)
                } else {
                    Row(
                        verticalAlignment = CenterVertically,
                        horizontalArrangement = Center,
                        modifier = Modifier.onGloballyPositioned {
                            val width = with(density) { it.size.width.toDp() }
                            labelContentWidth = max(labelContentWidth, width)
                        },
                    ) {
                        leadingIcon?.let {
                            BaseIcon(
                                icon = it,
                                tint = contentColor,
                            )
                            HorizontalSpacer(8.dp)
                        }
                        Text(
                            text = label,
                            color = contentColor,
                            overflow = Ellipsis,
                            maxLines = 1,
                            style = textStyle.copy(
                                brush = contentBrush,
                            ),
                        )
                        trailingIcon?.let {
                            HorizontalSpacer(8.dp)
                            BaseIcon(
                                icon = it,
                                tint = contentColor,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Immutable
data class BaseButtonShape(
    val height: Dp,
    val shape: Shape,
)

@Composable
private fun BaseIcon(
    @DrawableRes icon: Int,
    tint: Color,
) {
    Icon(
        modifier = Modifier.size(16.dp),
        painter = painterResource(icon),
        tint = tint,
        contentDescription = null,
    )
}
