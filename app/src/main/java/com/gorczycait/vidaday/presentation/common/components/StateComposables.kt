package com.gorczycait.vidaday.presentation.common.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gorczycait.vidaday.presentation.common.components.button.HugButton
import com.gorczycait.vidaday.presentation.common.components.button.HugButtons
import com.gorczycait.vidaday.presentation.common.theme.BackbonesTheme

@Composable
fun ScreenState(
    @DrawableRes icon: Int?,
    @StringRes header: Int,
    body: String,
    modifier: Modifier = Modifier,
    headerColor: Color = BackbonesTheme.colors.onSurface,
    bodyColor: Color = BackbonesTheme.colors.onSurfaceSecondary,
    fullscreen: Boolean = true,
    extraComposable: (@Composable ColumnScope.() -> Unit)? = null,
    topButton: StateButtonResource? = null,
    bottomButton: StateButtonResource? = null,
) {
    Column(
        modifier = modifier.then(if (fullscreen) Modifier.fillMaxSize() else Modifier.fillMaxWidth()),
        verticalArrangement = Center,
        horizontalAlignment = CenterHorizontally,
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 40.dp),
            verticalArrangement = Center,
            horizontalAlignment = CenterHorizontally,
        ) {
            icon?.let {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(160.dp),
                )
                VerticalSpacer(24.dp)
            }
            Text(
                text = stringResource(header).uppercase(),
                color = headerColor,
                style = BackbonesTheme.typography.DISPLAY_L_EXTRABOLD,
                textAlign = TextAlign.Center,
            )
            VerticalSpacer(16.dp)
            Text(
                text = body,
                color = bodyColor,
                style = BackbonesTheme.typography.Body_M_Medium,
                textAlign = TextAlign.Center,
            )
        }
        extraComposable?.invoke(this)
        HugButtons(
            modifier = Modifier.padding(horizontal = 40.dp),
            primary = topButton?.let { HugButton(it.label, it.onClick, 24.dp) },
            secondary = bottomButton?.let { HugButton(it.label, it.onClick, 8.dp) },
        )
    }
}

data class StateButtonResource(
    @StringRes val label: Int,
    val onClick: () -> Unit,
)
