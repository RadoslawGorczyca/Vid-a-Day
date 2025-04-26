package com.gorczycait.backbones.presentation.common.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gorczycait.backbones.presentation.common.theme.BackbonesTheme

@Composable
fun MaxWidthDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = 1.dp,
    color: Color = BackbonesTheme.colors.borderSecondary,
) = HorizontalDivider(
    modifier = modifier.fillMaxWidth(),
    thickness = thickness,
    color = color,
)

@Composable
fun MaxHeightDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = 1.dp,
    color: Color = BackbonesTheme.colors.onPrimary,
) = VerticalDivider(
    modifier = modifier.fillMaxHeight(),
    thickness = thickness,
    color = color,
)

@Composable
fun MaxHeightDashedDivider(
    color: Color,
    modifier: Modifier = Modifier,
    thickness: Dp = 1.dp,
) {
    Canvas(
        modifier = modifier
            .fillMaxHeight()
            .width(thickness),
    ) {
        drawLine(
            color = color,
            strokeWidth = thickness.toPx(),
            start = Offset(thickness.toPx() / 2, 0f),
            end = Offset(thickness.toPx() / 2, size.height),
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(16f, 8f), 2f),
        )
    }
}
