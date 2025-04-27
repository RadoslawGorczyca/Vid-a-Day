package com.gorczycait.backbones.presentation.paparazzi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import com.gorczycait.backbones.presentation.common.theme.BackbonesTheme

@Composable
fun PreviewScreen(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .height(height = LocalWindowInfo.current.containerSize.height.dp)
            .background(BackbonesTheme.colors.surface),
    ) {
        content()
    }
}
