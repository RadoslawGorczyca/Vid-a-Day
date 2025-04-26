package com.gorczycait.backbones.presentation.common.components

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import androidx.annotation.RawRes
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperty
import com.gorczycait.backbones.R
import com.gorczycait.backbones.presentation.common.theme.BackbonesTheme

@Composable
fun LoadingLottieS(
    modifier: Modifier = Modifier,
    color: Color = BackbonesTheme.colors.onSurface,
) {
    LoadingLottie(
        size = 16.dp,
        color = color,
        modifier = modifier,
    )
}

@Composable
fun LoadingLottieM(
    modifier: Modifier = Modifier,
    color: Color = BackbonesTheme.colors.onSurface,
) {
    LoadingLottie(
        size = 24.dp,
        color = color,
        modifier = modifier,
    )
}

@Composable
fun LoadingLottieL(
    modifier: Modifier = Modifier,
    color: Color = BackbonesTheme.colors.onSurface,
) {
    LoadingLottie(
        size = 40.dp,
        color = color,
        modifier = modifier,
    )
}

@Composable
private fun LoadingLottie(
    size: Dp,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Lottie(
        lottieRes = R.raw.lottie_loader,
        color = color,
        modifier = modifier.size(size),
    )
}

@Composable
private fun Lottie(
    @RawRes lottieRes: Int,
    color: Color,
    modifier: Modifier = Modifier,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(lottieRes))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
    )
    val dynamicProperties = rememberLottieDynamicProperties(
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR_FILTER,
            value = PorterDuffColorFilter(color.toArgb(), PorterDuff.Mode.SRC_ATOP),
            keyPath = arrayOf("**"),
        ),
    )
    LottieAnimation(
        composition = composition,
        progress = { progress },
        dynamicProperties = dynamicProperties,
        modifier = modifier,
    )
}
