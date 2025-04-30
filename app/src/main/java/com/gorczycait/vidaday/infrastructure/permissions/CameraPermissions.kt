package com.gorczycait.vidaday.infrastructure.permissions

import android.Manifest.permission.CAMERA
import android.Manifest.permission.RECORD_AUDIO
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun rememberVideoRecordingPermissionsState(
    onAllPermissionGrant: () -> Unit = {},
    onPermissionsDeny: () -> Unit = {},
) = rememberMultiplePermissionsState(
    permissions = listOf(CAMERA, RECORD_AUDIO),
    onPermissionsResult = { results ->
        if (results.values.all { it }) {
            onAllPermissionGrant()
        } else {
            onPermissionsDeny()
        }
    },
)
