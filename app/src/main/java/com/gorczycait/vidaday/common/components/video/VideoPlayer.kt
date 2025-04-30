package com.gorczycait.vidaday.common.components.video

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.gorczycait.vidaday.common.components.video.TogglePauseMode.DISABLED
import com.gorczycait.vidaday.common.components.video.TogglePauseMode.ON_CLICK_WITH_ICON
import com.gorczycait.vidaday.common.components.video.VideoStartOption.FromPosition
import com.gorczycait.vidaday.common.components.video.VideoStartOption.FromStart
import com.gorczycait.vidaday.common.components.video.VideoStartOption.ResumeVideo

@OptIn(UnstableApi::class)
@Suppress("CyclomaticComplexMethod")
@Composable
fun VideoPlayer(
    exoPlayer: ExoPlayer,
    url: String,
    active: Boolean,
    modifier: Modifier = Modifier,
    onDispose: (exoPlayer: ExoPlayer, videoPosition: Long) -> Unit = { _, _ -> },
    togglePauseMode: TogglePauseMode = DISABLED,
    videoStartOption: VideoStartOption = ResumeVideo,
    onPlayIconClick: () -> Unit = {},
    playerResizeMode: ResizeMode = ResizeMode.RESIZE_MODE_FIXED_WIDTH,
    onIsPlayingChange: (isPlaying: Boolean) -> Unit = {},
    onPlayerLoadingChange: (isLoading: Boolean) -> Unit = {},
) {
    LaunchedEffect(url) {
        if (url != exoPlayer.currentMediaItem?.localConfiguration?.uri?.toString()) {
            exoPlayer.setMediaItem(MediaItem.fromUri(url))
        }

        if (exoPlayer.playbackState == Player.STATE_IDLE) {
            exoPlayer.prepare()
        }

        when (videoStartOption) {
            is FromStart -> exoPlayer.seekTo(0)
            is FromPosition -> exoPlayer.seekTo(videoStartOption.position)
            is ResumeVideo -> {} // Does nothing
        }
    }

    LaunchedEffect(active) {
        exoPlayer.playWhenReady = active
    }

    val lifecycleOwner = LocalLifecycleOwner.current

    val currentOnPlayerDispose by rememberUpdatedState(onDispose)
    val currentActive by rememberUpdatedState(active)
    val currentOnIsPlayingChange by rememberUpdatedState(onIsPlayingChange)
    val currentOnPlayerLoadingChange by rememberUpdatedState(onPlayerLoadingChange)

    var isVideoPlaying by remember(exoPlayer) { mutableStateOf(false) }

    DisposableEffect(Unit) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> if (exoPlayer.isPlaying.not() && currentActive) {
                    exoPlayer.play()
                }

                Lifecycle.Event.ON_PAUSE -> {
                    exoPlayer.pause()
                }

                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            exoPlayer.pause()
            // Invoke the current progress callback with the player's current position before disposal
            lifecycleOwner.lifecycle.removeObserver(observer)
            currentOnPlayerDispose(exoPlayer, exoPlayer.currentPosition)
        }
    }

    DisposableEffect(Unit) {
        val playerListener = object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                currentOnPlayerLoadingChange(playbackState == Player.STATE_BUFFERING)
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                isVideoPlaying = isPlaying
                currentOnIsPlayingChange(isPlaying)
            }
        }

        exoPlayer.addListener(playerListener)

        onDispose {
            exoPlayer.removeListener(playerListener)
        }
    }

    Box(
        modifier = modifier
            .then(
                if (togglePauseMode != DISABLED) {
                    Modifier.pointerInput(Unit) {
                        var playOnRelease = false
                        detectTapGestures(
                            onLongPress = {
                                playOnRelease = exoPlayer.isPlaying
                                exoPlayer.pause()
                            },
                            onPress = {
                                val released = tryAwaitRelease()
                                if (released) {
                                    if (playOnRelease) exoPlayer.play()
                                    playOnRelease = false
                                }
                            },
                            onTap = {
                                if (exoPlayer.isPlaying) {
                                    exoPlayer.pause()
                                } else {
                                    onPlayIconClick()
                                    exoPlayer.play()
                                }
                            },
                        )
                    }
                } else {
                    Modifier
                },
            ),
    ) {
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    layoutParams = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                    )
                    useController = false
                    resizeMode = playerResizeMode.toExoplayerResizeMode()
                    player = exoPlayer
                }
            },
        )
        if (!isVideoPlaying && togglePauseMode == ON_CLICK_WITH_ICON) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = null,
                modifier = Modifier
                    .size(128.dp)
                    .align(Alignment.Center),
            )
        }
    }
}

sealed interface VideoStartOption {
    data object FromStart : VideoStartOption
    data object ResumeVideo : VideoStartOption
    data class FromPosition(val position: Long) : VideoStartOption
}

enum class TogglePauseMode {
    DISABLED,
    ON_CLICK,
    ON_CLICK_WITH_ICON,
}

enum class ResizeMode {
    RESIZE_MODE_FIT,
    RESIZE_MODE_FIXED_WIDTH,
    RESIZE_MODE_FIXED_HEIGHT,
    RESIZE_MODE_FILL,
    RESIZE_MODE_ZOOM,
}

@OptIn(UnstableApi::class)
private fun ResizeMode.toExoplayerResizeMode() = when (this) {
    ResizeMode.RESIZE_MODE_FIT -> AspectRatioFrameLayout.RESIZE_MODE_FIT
    ResizeMode.RESIZE_MODE_FIXED_WIDTH -> AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH
    ResizeMode.RESIZE_MODE_FIXED_HEIGHT -> AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT
    ResizeMode.RESIZE_MODE_FILL -> AspectRatioFrameLayout.RESIZE_MODE_FILL
    ResizeMode.RESIZE_MODE_ZOOM -> AspectRatioFrameLayout.RESIZE_MODE_ZOOM
}
