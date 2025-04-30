package com.gorczycait.vidaday

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
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorczycait.vidaday.CameraState.IDLE
import com.gorczycait.vidaday.CameraState.RECORDING
import com.gorczycait.vidaday.CameraState.STOPPED
import com.gorczycait.vidaday.VideoUiEvent.VideoSaved
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import java.time.LocalDateTime

class VideoViewModel(
    private val videoDb: Database,
    private val application: Application,
) : ViewModel() {

    private val _uiState = MutableStateFlow(VideoUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<VideoUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private var videoCapture: VideoCapture<Recorder>? = null
    private var recording: Recording? = null

    @Suppress("TooGenericExceptionCaught")
    fun startCamera(previewView: PreviewView, lifecycleOwner: LifecycleOwner) {
        _uiState.update { it.copy(cameraState = IDLE, filePath = null, timeStamp = null) }
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
            .start(ContextCompat.getMainExecutor(context)) {}
        _uiState.update { it.copy(filePath = file.path, timeStamp = LocalDateTime.now()) }
    }

    fun stopRecording() {
        recording?.stop()
        recording = null
        _uiState.update { it.copy(cameraState = STOPPED) }
    }

    fun saveVideo(description: String) {
        viewModelScope.launch {
            val filePath = _uiState.value.filePath
            val timeStamp = _uiState.value.timeStamp
            if (filePath != null && timeStamp != null) {
                videoDb.videoQueries.insertVideo(
                    file_path = filePath,
                    description = description,
                    timestamp = timeStamp.toString(),
                )
            }
            _uiEvent.emit(VideoSaved)
        }
    }

    fun redoVideo() {
        deleteVideo()
        _uiState.update { it.copy(cameraState = IDLE) }
    }

    private fun deleteVideo() {
        val filePath = _uiState.value.filePath
        if (filePath != null) {
            File(filePath).delete()
        }
    }
}

data class VideoUiState(
    val cameraState: CameraState = IDLE,
    val filePath: String? = null,
    val timeStamp: LocalDateTime? = null,
)

sealed interface VideoUiEvent {
    data object VideoSaved : VideoUiEvent
}

enum class CameraState {
    IDLE,
    RECORDING,
    STOPPED,
}
