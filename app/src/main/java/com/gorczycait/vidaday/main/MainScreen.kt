package com.gorczycait.vidaday.main

import android.media.ThumbnailUtils
import android.util.Size
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle.Event.ON_RESUME
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.gorczycait.vidaday.R
import com.gorczycait.vidaday.common.components.AppSettingsSheet
import com.gorczycait.vidaday.common.components.HorizontalSpacer
import com.gorczycait.vidaday.common.components.MaxWidthDivider
import com.gorczycait.vidaday.common.components.PermissionType.CAMERA
import com.gorczycait.vidaday.common.components.Snackbar
import com.gorczycait.vidaday.common.components.SnackbarHandler
import com.gorczycait.vidaday.common.components.button.PrimaryButton
import com.gorczycait.vidaday.common.navigation.navigateIfResumed
import com.gorczycait.vidaday.common.theme.BackbonesTheme
import com.gorczycait.vidaday.infrastructure.permissions.rememberVideoRecordingPermissionsState
import com.gorczycait.vidaday.infrastructure.util.formatTimestamp
import com.gorczycait.vidaday.infrastructure.util.openAppSystemSettings
import com.gorczycait.vidaday.infrastructure.util.openVideoPlayer
import com.gorczycait.vidaday.main.MainUiEvent.NavigateToVideoScreen
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.VideoScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import java.io.File

@OptIn(ExperimentalPermissionsApi::class)
@Destination<RootGraph>(start = true)
@Composable
fun MainScreen(
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = koinViewModel(),
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val snackbarHandler = remember(scope) { SnackbarHandler(scope) }
    val navigateToVideoScreen = navigator.navigateIfResumed({ VideoScreenDestination })
    var showAppSettingsSheet by remember { mutableStateOf(false) }
    val permissionsState = rememberVideoRecordingPermissionsState(
        onAllPermissionGrant = viewModel::navigateToVideoScreen,
        onPermissionsDeny = {
            snackbarHandler.showWarningSnackbar(R.string.permissions_needed_for_video_capturing)
        },
    )
    LaunchedEffect(Unit) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                NavigateToVideoScreen -> navigateToVideoScreen()
            }
        }
    }
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == ON_RESUME) {
                viewModel.refresh()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    BackbonesTheme {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            snackbarHost = {
                Snackbar(
                    navController = navController,
                    snackbarHandler = snackbarHandler,
                )
            },
            content = { innerPadding ->
                val focusManager = LocalFocusManager.current
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) }
                        .consumeWindowInsets(innerPadding),
                ) {
                    MainScreen(
                        state = viewModel.uiState.collectAsStateWithLifecycle().value,
                        onVideoClick = { path -> context.openVideoPlayer(path) },
                        onNewVideoClick = {
                            when {
                                permissionsState.allPermissionsGranted -> navigateToVideoScreen()
                                permissionsState.shouldShowRationale -> showAppSettingsSheet = true
                                else -> permissionsState.launchMultiplePermissionRequest()
                            }
                        },
                    )
                }
            },
        )
    }
    if (showAppSettingsSheet) {
        AppSettingsSheet(CAMERA) { shouldNavigate ->
            showAppSettingsSheet = false
            if (shouldNavigate) context.openAppSystemSettings()
        }
    }
}

@Composable
private fun MainScreen(
    state: MainUiState,
    onVideoClick: (String) -> Unit,
    onNewVideoClick: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        LazyColumn(
            contentPadding = PaddingValues(vertical = 32.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
        ) {
            items(state.videos) { video ->
                VideoListItem(
                    video = video,
                    onClick = { onVideoClick(video.path) },
                )
                MaxWidthDivider()
            }
        }
        PrimaryButton(
            modifier = Modifier
                .align(BottomCenter)
                .padding(bottom = 32.dp),
            label = stringResource(R.string.new_video),
            onClick = onNewVideoClick,
        )
    }
}

@Composable
fun VideoListItem(
    video: VideoItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp),
    ) {
        ThumbnailFromFile(filePath = video.path)
        HorizontalSpacer(12.dp)
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = video.description,
                style = BackbonesTheme.typography.Body_L_Medium,
            )
            Text(
                text = video.timestamp.formatTimestamp(),
                style = BackbonesTheme.typography.Body_M_Medium,
            )
        }
    }
}

@Composable
fun ThumbnailFromFile(
    filePath: String,
    modifier: Modifier = Modifier,
) {
    val thumbnail = remember(filePath) {
        ThumbnailUtils.createVideoThumbnail(File(filePath), Size(100, 100), null)
    }
    Image(
        bitmap = thumbnail.asImageBitmap(),
        contentDescription = "Video thumbnail",
        modifier = modifier
            .size(100.dp)
            .clip(RoundedCornerShape(8.dp)),
        contentScale = ContentScale.Crop,
    )
}
