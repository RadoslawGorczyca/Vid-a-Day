package com.gorczycait.vidaday.presentation.common.util

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@Composable
fun rememberAnimatedProgress(
    vararg keys: Any?,
    condition: () -> Float,
): Float {
    var progressState by rememberSaveable { mutableFloatStateOf(0f) }
    val animatedProgress by animateFloatAsState(
        targetValue = progressState,
        animationSpec = tween(durationMillis = 1000),
        label = "AnimatedFloatProgress",
    )
    val latestCondition by rememberUpdatedState(condition)
    LaunchedEffect(*keys) {
        progressState = latestCondition()
    }
    return animatedProgress
}
