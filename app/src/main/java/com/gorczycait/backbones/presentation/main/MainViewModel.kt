package com.gorczycait.backbones.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorczycait.backbones.presentation.main.MainUiEvent.NavigateToVideoScreen
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@Suppress("LongParameterList")
class MainViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<MainUiEvent>(BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    fun navigateToVideoScreen() {
        viewModelScope.launch {
            _uiEvent.send(NavigateToVideoScreen)
        }
    }
}

data class MainUiState(
    val exampleState: Boolean = false,
)

sealed interface MainUiEvent {
    data object NavigateToVideoScreen : MainUiEvent
}
