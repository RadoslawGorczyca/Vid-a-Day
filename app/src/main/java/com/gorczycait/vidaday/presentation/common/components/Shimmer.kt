package com.gorczycait.vidaday.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import com.gorczycait.vidaday.presentation.common.theme.BackbonesTheme
import com.valentinilk.shimmer.shimmer

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun ShimmerBox(
    size: Dp,
    shape: Shape = RectangleShape,
    alpha: Float = 1f,
) {
    ShimmerBox(size, size, shape, alpha)
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun ShimmerBox(
    width: Dp,
    height: Dp,
    shape: Shape = RectangleShape,
    alpha: Float = 1f,
) {
    Box(
        modifier = Modifier
            .size(width, height)
            .clip(shape)
            .shimmer()
            .background(BackbonesTheme.colors.surfaceSkeleton.copy(alpha = alpha)),
    )
}

@Composable
fun ShimmerBox(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    alpha: Float = 1f,
) {
    Box(
        modifier = modifier
            .clip(shape)
            .shimmer()
            .background(BackbonesTheme.colors.surfaceSkeleton.copy(alpha = alpha)),
    )
}
