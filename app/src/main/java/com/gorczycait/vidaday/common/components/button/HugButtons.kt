package com.gorczycait.vidaday.common.components.button

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize.Max
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.gorczycait.vidaday.common.components.VerticalSpacer

@Composable
fun ColumnScope.HugButtons(
    primary: HugButton?,
    secondary: HugButton?,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    var maxWidth by remember { mutableStateOf(0.dp) }
    val widthModifier = modifier
        .width(Max)
        .widthIn(min = maxWidth)
        .onSizeChanged {
            val width = with(density) { it.width.toDp() }
            maxWidth = max(maxWidth, width)
        }
    primary?.let {
        VerticalSpacer(primary.topSpacing)
        PrimaryButton(
            label = stringResource(primary.label),
            onClick = primary.onClick,
            modifier = widthModifier,
        )
    }
    secondary?.let {
        VerticalSpacer(secondary.topSpacing)
        SecondaryButton(
            label = stringResource(secondary.label),
            onClick = secondary.onClick,
            modifier = widthModifier,
        )
    }
}

data class HugButton(
    @StringRes val label: Int,
    val onClick: () -> Unit,
    val topSpacing: Dp,
)
