package com.gorczycait.backbones.presentation.video

import android.Manifest.permission.RECORD_AUDIO
import android.app.Application
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.camera.core.CameraSelector.DEFAULT_FRONT_CAMERA
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.FileOutputOptions
import androidx.camera.video.Quality.HIGHEST
import androidx.camera.video.QualitySelector
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.camera.video.VideoRecordEvent
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.gorczycait.backbones.presentation.video.CameraState.IDLE
import com.gorczycait.backbones.presentation.video.CameraState.RECORDING
import com.gorczycait.backbones.presentation.video.CameraState.STOPPED
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber
import java.io.File

class VideoViewModel(
    private val application: Application,
) : ViewModel() {

    private val _uiState = MutableStateFlow(VideoUiState())
    val uiState = _uiState.asStateFlow()

    private var videoCapture: VideoCapture<Recorder>? = null
    private var recording: Recording? = null

    @Suppress("TooGenericExceptionCaught")
    fun startCamera(previewView: PreviewView, lifecycleOwner: LifecycleOwner) {
        _uiState.update { it.copy(cameraState = IDLE) }
        val context = application
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener(
            {
                val cameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder().build().also {
                    it.surfaceProvider = previewView.surfaceProvider
                }
                val recorder = Recorder.Builder()
                    .setQualitySelector(QualitySelector.from(HIGHEST))
                    .build()
                val cameraSelector = DEFAULT_FRONT_CAMERA
                videoCapture = VideoCapture.withOutput(recorder)

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        videoCapture,
                    )
                } catch (e: Exception) {
                    Timber.tag("CameraViewModel").e(e, "Use case binding failed")
                }
            },
            ContextCompat.getMainExecutor(context),
        )
    }

    @RequiresPermission(RECORD_AUDIO)
    fun startRecording(context: Context) {
        _uiState.update { it.copy(cameraState = RECORDING) }
        val videoCapture = videoCapture ?: return

        val name = "video_${System.currentTimeMillis()}.mp4"
        val file = File(context.getExternalFilesDir(null), name)
        val outputOptions = FileOutputOptions.Builder(file).build()

        recording = videoCapture.output
            .prepareRecording(context, outputOptions)
            .withAudioEnabled()
            .start(ContextCompat.getMainExecutor(context)) { event ->
                if (event is VideoRecordEvent.Finalize) {
                    Timber.tag("CameraViewModel").d("Saved video: ${event.outputResults.outputUri}")
                }
            }
    }

    fun stopRecording() {
        _uiState.update { it.copy(cameraState = STOPPED) }
        recording?.stop()
        recording = null
    }
}

data class VideoUiState(
    val cameraState: CameraState = IDLE,
)

enum class CameraState {
    IDLE,
    RECORDING,
    STOPPED,
}
