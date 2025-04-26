package com.gorczycait.backbones.presentation.common.components.button

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gorczycait.backbones.presentation.common.components.LoadingLottieM
import com.gorczycait.backbones.presentation.common.components.LoadingLottieS
import com.gorczycait.backbones.presentation.common.theme.BackbonesTheme

@Composable
fun MediumIconButton(
    @DrawableRes icon: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = BackbonesTheme.colors.onSurface,
    loading: Boolean = false,
    enabled: Boolean = true,
    testTag: String? = null,
    extraContent: (@Composable BoxScope.() -> Unit)? = null,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
        loading = loading,
        enabled = enabled,
        iconSize = 24.dp,
        icon = icon,
        tint = tint,
        testTag = testTag,
        extraContent = extraContent,
    )
}

@Composable
fun SmallIconButton(
    @DrawableRes icon: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = BackbonesTheme.colors.onSurface,
    loading: Boolean = false,
    enabled: Boolean = true,
    testTag: String? = null,
    extraContent: (@Composable BoxScope.() -> Unit)? = null,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
        loading = loading,
        enabled = enabled,
        iconSize = 16.dp,
        icon = icon,
        tint = tint,
        testTag = testTag,
        extraContent = extraContent,
    )
}

@Composable
private fun IconButton(
    onClick: () -> Unit,
    loading: Boolean,
    enabled: Boolean,
    iconSize: Dp,
    @DrawableRes icon: Int,
    tint: Color,
    testTag: String?,
    extraContent: (@Composable BoxScope.() -> Unit)?,
    modifier: Modifier = Modifier,
) {
    androidx.compose.material3.IconButton(
        onClick = onClick,
        modifier = testTag?.let { modifier.testTag(testTag) } ?: modifier,
        enabled = enabled,
    ) {
        AnimatedContent(
            targetState = loading,
            label = "IconButtonLoadingAnimatedContent",
        ) { loading ->
            Box {
                if (loading) {
                    if (iconSize == 16.dp) {
                        LoadingLottieS(color = tint)
                    } else {
                        LoadingLottieM(color = tint)
                    }
                } else {
                    Icon(
                        modifier = Modifier.size(iconSize),
                        painter = painterResource(icon),
                        tint = tint,
                        contentDescription = null,
                    )
                    extraContent?.invoke(this)
                }
            }
        }
    }
}
