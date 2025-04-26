package com.gorczycait.backbones.presentation.common.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import com.gorczycait.backbones.R
import com.gorczycait.backbones.presentation.common.components.button.MediumIconButton
import com.gorczycait.backbones.presentation.common.theme.BackbonesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    header: String?,
    modifier: Modifier = Modifier,
    leadingIcon: TopBarIcon? = null,
    subtitle: String? = null,
    trailingActions: @Composable RowScope.() -> Unit = {},
    center: Boolean = true,
    backgroundColor: Color = BackbonesTheme.colors.surface,
    contentColor: Color = BackbonesTheme.colors.onSurface,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        val insets = WindowInsets(left = 12.dp)
        if (center) {
            CenterAlignedTopAppBar(
                title = { header?.let { TopBarTitle(it, subtitle, contentColor) } },
                modifier = Modifier.padding(statusBarPadding()),
                navigationIcon = { leadingIcon?.let { TopBarIconButton(leadingIcon, contentColor) } },
                actions = trailingActions,
                windowInsets = insets,
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors().copy(
                    containerColor = backgroundColor,
                ),
            )
        } else {
            TopAppBar(
                title = { header?.let { TopBarTitle(it, subtitle, contentColor) } },
                modifier = Modifier.padding(statusBarPadding()),
                navigationIcon = { leadingIcon?.let { TopBarIconButton(leadingIcon, contentColor) } },
                actions = trailingActions,
                windowInsets = insets,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor,
                ),
            )
        }
    }
}

@Composable
private fun TopBarTitle(
    header: String,
    subtitle: String?,
    headerColor: Color,
) {
    Column(
        verticalArrangement = Center,
        horizontalAlignment = CenterHorizontally,
    ) {
        Text(
            text = header.uppercase(),
            style = BackbonesTheme.typography.HEADER_S_BOLD,
            color = headerColor,
            overflow = Ellipsis,
            maxLines = 1,
        )
        AnimatedVisibility(
            visible = subtitle != null,
            enter = slideInVertically { it } + fadeIn(),
            exit = slideOutVertically { it } + fadeOut(),
        ) {
            subtitle?.let {
                VerticalSpacer(2.dp)
                Text(
                    text = subtitle,
                    style = BackbonesTheme.typography.Body_XS_Medium,
                    color = BackbonesTheme.colors.onSurfaceSecondary,
                    overflow = Ellipsis,
                    maxLines = 1,
                )
            }
        }
    }
}

@Composable
private fun TopBarIconButton(
    icon: TopBarIcon,
    tint: Color,
) {
    MediumIconButton(
        icon = icon.icon,
        onClick = icon.onClick,
        extraContent = icon.extraContent,
        tint = tint,
    )
}

@Composable
fun statusBarPadding() = WindowInsets.statusBars.asPaddingValues()

data class TopBarIcon(
    val onClick: () -> Unit,
    @DrawableRes val icon: Int = R.drawable.ic_chevron_left,
    val extraContent: (@Composable BoxScope.() -> Unit)? = null,
)
