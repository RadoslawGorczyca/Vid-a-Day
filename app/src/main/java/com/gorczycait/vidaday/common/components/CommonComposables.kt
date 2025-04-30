@file:Suppress("TooManyFunctions")

package com.gorczycait.vidaday.common.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gorczycait.vidaday.R
import com.gorczycait.vidaday.common.theme.BackbonesTheme

@Composable
fun IconWithText(
    text: String,
    modifier: Modifier = Modifier,
    verticalAlignment: Alignment.Vertical = CenterVertically,
    textStyle: TextStyle = BackbonesTheme.typography.Body_XXS_Medium,
    textColor: Color = BackbonesTheme.colors.onSurface,
    @DrawableRes icon: Int = R.drawable.ic_info,
    iconSize: Dp = 16.dp,
    iconTint: Color = Color.Unspecified,
    spacing: Dp = 6.dp,
    body: String? = null,
    bodyStyle: TextStyle = BackbonesTheme.typography.Header_S_Medium,
    bodyColor: Color = BackbonesTheme.colors.onSurfaceSecondary,
) {
    Row(
        modifier = modifier,
        verticalAlignment = verticalAlignment,
    ) {
        Icon(
            modifier = Modifier.size(iconSize),
            painter = painterResource(icon),
            tint = iconTint,
            contentDescription = null,
        )
        HorizontalSpacer(spacing)
        Column {
            Text(
                text = text,
                style = textStyle,
                color = textColor,
            )
            body?.let {
                VerticalSpacer(8.dp)
                Text(
                    text = body,
                    style = bodyStyle,
                    color = bodyColor,
                )
            }
        }
    }
}
