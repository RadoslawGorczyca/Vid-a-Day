package com.gorczycait.backbones.presentation.video

import android.Manifest.permission.CAMERA
import android.Manifest.permission.RECORD_AUDIO
import androidx.annotation.RequiresPermission
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gorczycait.backbones.presentation.common.theme.BackbonesTheme
import com.gorczycait.backbones.presentation.video.CameraState.IDLE
import com.gorczycait.backbones.presentation.video.CameraState.RECORDING
import com.gorczycait.backbones.presentation.video.CameraState.STOPPED
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import org.koin.androidx.compose.koinViewModel

@RequiresPermission(allOf = [CAMERA, RECORD_AUDIO])
@Destination<RootGraph>
@Composable
fun VideoScreen(
    viewModel: VideoViewModel = koinViewModel(),
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    VideoScreen(
        state = viewModel.uiState.collectAsStateWithLifecycle().value,
        onStartCamera = { previewView ->
            viewModel.startCamera(previewView, lifecycleOwner)
        },
        onStartRecording = {
            viewModel.startRecording(context)
        },
        onStopRecording = viewModel::stopRecording,
    )
}

@Composable
private fun VideoScreen(
    state: VideoUiState,
    onStartCamera: (PreviewView) -> Unit,
    onStartRecording: () -> Unit,
    onStopRecording: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        AndroidView(
            factory = { context ->
                PreviewView(context).apply {
                    onStartCamera(this)
                }
            },
            modifier = Modifier.fillMaxSize(),
        )
        when (state.cameraState) {
            IDLE -> {
                IconButton(
                    modifier = Modifier
                        .align(BottomCenter)
                        .padding(bottom = 32.dp),
                    onClick = { onStartRecording() },
                ) {
                    Box(
                        modifier = Modifier.background(BackbonesTheme.colors.onSurface, CircleShape),
                    ) {
                        Box(
                            modifier = Modifier
                                .background(BackbonesTheme.colors.accent, CircleShape)
                                .padding(8.dp),
                        )
                    }
                }
            }

            RECORDING -> {
                IconButton(
                    modifier = Modifier
                        .align(BottomCenter)
                        .padding(bottom = 32.dp),
                    onClick = { onStopRecording() },
                ) {
                    Box(
                        modifier = Modifier.background(BackbonesTheme.colors.onSurface, CircleShape),
                    ) {
                        Box(
                            modifier = Modifier
                                .background(BackbonesTheme.colors.accent, RectangleShape)
                                .padding(8.dp),
                        )
                    }
                }
            }

            STOPPED -> {
            }
        }
    }
}
