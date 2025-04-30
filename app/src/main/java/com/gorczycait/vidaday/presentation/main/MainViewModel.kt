package com.gorczycait.vidaday.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorczycait.vidaday.Database
import com.gorczycait.vidaday.presentation.main.MainUiEvent.NavigateToVideoScreen
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Suppress("LongParameterList")
class MainViewModel(
    private val videoDb: Database,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<MainUiEvent>(BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        loadVideos()
    }

    private fun loadVideos() {
        _uiState.update { state ->
            state.copy(
                videos = videoDb.videoQueries.selectAll().executeAsList().map {
                    VideoItem(
                        path = it.file_path,
                        description = it.description,
                        timestamp = it.timestamp,
                    )
                },
            )
        }
    }

    fun refresh() {
        loadVideos()
    }

    fun navigateToVideoScreen() {
        viewModelScope.launch {
            _uiEvent.send(NavigateToVideoScreen)
        }
    }
}

data class MainUiState(
    val videos: List<VideoItem> = listOf(),
)

sealed interface MainUiEvent {
    data object NavigateToVideoScreen : MainUiEvent
}

data class VideoItem(
    val path: String,
    val description: String,
    val timestamp: String,
)
