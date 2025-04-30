package com.gorczycait.vidaday.presentation.video

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gorczycait.vidaday.R
import com.gorczycait.vidaday.presentation.common.components.SnackbarHandler
import com.gorczycait.vidaday.presentation.common.theme.BackbonesTheme
import com.gorczycait.vidaday.presentation.video.CameraState.IDLE
import com.gorczycait.vidaday.presentation.video.CameraState.RECORDING
import com.gorczycait.vidaday.presentation.video.CameraState.STOPPED
import com.gorczycait.vidaday.presentation.video.VideoUiEvent.VideoSaved
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@RequiresPermission(allOf = [CAMERA, RECORD_AUDIO])
@Destination<RootGraph>
@Composable
fun VideoScreen(
    navigator: DestinationsNavigator,
    viewModel: VideoViewModel = koinViewModel(),
) {
    val scope = rememberCoroutineScope()
    val snackbarHandler = remember(scope) { SnackbarHandler(scope) }
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                VideoSaved -> {
                    snackbarHandler.showSuccessSnackbar(R.string.video_saved)
                    navigator.navigateUp()
                }
            }
        }
    }
    VideoScreen(
        state = viewModel.uiState.collectAsStateWithLifecycle().value,
        onStartCamera = { previewView ->
            viewModel.startCamera(previewView, lifecycleOwner)
        },
        onStartRecording = {
            viewModel.startRecording(context)
        },
        onStopRecording = viewModel::stopRecording,
        onSaveVideo = viewModel::saveVideo,
        onRedoVideo = viewModel::redoVideo,
    )
}

@Composable
private fun VideoScreen(
    state: VideoUiState,
    onStartCamera: (PreviewView) -> Unit,
    onStartRecording: () -> Unit,
    onStopRecording: () -> Unit,
    onSaveVideo: (String) -> Unit,
    onRedoVideo: () -> Unit,
) {
    var showSaveVideoSheet by remember { mutableStateOf(false) }
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
                showSaveVideoSheet = true
            }
        }
    }
    if (showSaveVideoSheet) {
        SaveVideoSheet(
            shouldSave = { shouldSave, description ->
                if (shouldSave) {
                    onSaveVideo(description.orEmpty())
                    showSaveVideoSheet = false
                } else {
                    onRedoVideo()
                    showSaveVideoSheet = false
                }
            },
        )
    }
}
