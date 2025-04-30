package com.gorczycait.vidaday.common.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.gorczycait.vidaday.common.theme.BackbonesTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    shouldDismissOnBackPress: Boolean = true,
    shouldDismissOnOutsideClick: Boolean = true,
    drawDragHandleOver: Boolean = false,
    content: @Composable ColumnScope.(Pair<CoroutineScope, SheetState>) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { shouldDismissOnOutsideClick },
    )
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = modifier,
        sheetState = sheetState,
        shape = RectangleShape,
        containerColor = BackbonesTheme.colors.surface,
        contentColor = BackbonesTheme.colors.onSurface,
        tonalElevation = 0.dp,
        scrimColor = BackbonesTheme.colors.surfaceQuaternary.copy(alpha = 0.6f),
        dragHandle = null,
        properties = ModalBottomSheetProperties(shouldDismissOnBackPress = shouldDismissOnBackPress),
    ) {
        val mainContent: @Composable () -> Unit = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = if (drawDragHandleOver) 0.dp else 16.dp)
                    .padding(bottom = 16.dp)
                    .navigationBarsPadding(),
            ) {
                content(scope to sheetState)
            }
        }
        if (drawDragHandleOver) {
            Box {
                mainContent()
                DragHandle(modifier = Modifier.align(TopCenter))
            }
        } else {
            Column {
                DragHandle(modifier = Modifier.align(CenterHorizontally))
                mainContent()
            }
        }
    }
}

@Composable
private fun DragHandle(
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.padding(vertical = 8.dp),
        color = BackbonesTheme.colors.border,
        shape = RoundedCornerShape(100.dp),
    ) {
        Box(modifier = Modifier.size(40.dp, 5.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
fun Pair<CoroutineScope, SheetState>.hide(onHidden: () -> Unit) {
    first
        .launch { second.hide() }
        .invokeOnCompletion {
            if (!second.isVisible) {
                onHidden()
            }
        }
}
