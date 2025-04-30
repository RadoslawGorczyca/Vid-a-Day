package com.gorczycait.backbones.presentation.video

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gorczycait.backbones.R
import com.gorczycait.backbones.presentation.common.components.BottomSheet
import com.gorczycait.backbones.presentation.common.components.VerticalSpacer
import com.gorczycait.backbones.presentation.common.components.button.HugButton
import com.gorczycait.backbones.presentation.common.components.button.HugButtons
import com.gorczycait.backbones.presentation.common.components.hide
import com.gorczycait.backbones.presentation.common.components.textfield.TextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveVideoSheet(
    shouldSave: (Boolean, String?) -> Unit,
    modifier: Modifier = Modifier,
) {
    val description = remember { mutableStateOf("") }
    BottomSheet(
        onDismiss = { shouldSave(false, null) },
        shouldDismissOnOutsideClick = false,
    ) {
        Column(modifier = modifier.padding(horizontal = 16.dp)) {
            Text(text = stringResource(R.string.video_description))
            VerticalSpacer(8.dp)
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = description.value,
                onValueChange = { description.value = it },
                minHeight = 48.dp,
            )
            VerticalSpacer(16.dp)
            HugButtons(
                modifier = Modifier.fillMaxWidth(),
                primary = HugButton(
                    label = R.string.save,
                    onClick = { it.hide { shouldSave(true, description.value) } },
                    topSpacing = 8.dp,
                ),
                secondary = HugButton(
                    label = R.string.redo,
                    onClick = { it.hide { shouldSave(false, null) } },
                    topSpacing = 8.dp,
                ),
            )
        }
    }
}
