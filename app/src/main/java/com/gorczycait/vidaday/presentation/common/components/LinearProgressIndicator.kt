package com.gorczycait.vidaday.presentation.common.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import com.gorczycait.vidaday.presentation.common.theme.BackbonesTheme

@Composable
fun LinearProgressIndicator(
    loading: Boolean,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = loading,
        enter = slideInVertically { it },
        exit = slideOutVertically { it },
    ) {
        LinearProgressIndicator(
            color = BackbonesTheme.colors.onSurface,
            trackColor = BackbonesTheme.colors.background,
            strokeCap = StrokeCap.Square,
            gapSize = 0.dp,
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp),
        )
    }
}

@Composable
fun LinearProgressIndicator(
    progress: Float,
    color: Color,
    trackColor: Color,
    modifier: Modifier = Modifier,
    strokeCap: StrokeCap = StrokeCap.Square,
    drawStopIndicator: DrawScope.() -> Unit = {},
) {
    LinearProgressIndicator(
        progress = { progress },
        color = color,
        trackColor = trackColor,
        strokeCap = strokeCap,
        gapSize = 0.dp,
        drawStopIndicator = drawStopIndicator,
        modifier = modifier,
    )
}
