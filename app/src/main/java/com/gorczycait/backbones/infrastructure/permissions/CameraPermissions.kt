package com.gorczycait.backbones.infrastructure.permissions

import android.Manifest.permission.CAMERA
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun rememberCameraPermissionState(
    onPermissionGrant: () -> Unit,
    onPermissionsDeny: () -> Unit = {},
) = rememberPermissionState(
    permission = CAMERA,
    onPermissionResult = { granted ->
        if (granted) {
            onPermissionGrant()
        } else {
            onPermissionsDeny()
        }
    },
)
