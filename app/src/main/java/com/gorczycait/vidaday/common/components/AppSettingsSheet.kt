package com.gorczycait.vidaday.common.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.gorczycait.vidaday.R
import com.gorczycait.vidaday.common.components.PermissionType.CAMERA

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSettingsSheet(
    permissionType: PermissionType,
    shouldNavigate: (Boolean) -> Unit,
) {
    BottomSheet(
        onDismiss = { shouldNavigate(false) },
    ) {
        ScreenState(
            icon = when (permissionType) {
                CAMERA -> android.R.drawable.ic_menu_camera
            },
            header = when (permissionType) {
                CAMERA -> R.string.allow_camera
            },
            body = stringResource(
                when (permissionType) {
                    CAMERA -> R.string.allow_camera_body
                },
            ),
            fullscreen = false,
            topButton = StateButtonResource(R.string.navigate_to_settings) { it.hide { shouldNavigate(true) } },
        )
    }
}

enum class PermissionType {
    CAMERA,
}
