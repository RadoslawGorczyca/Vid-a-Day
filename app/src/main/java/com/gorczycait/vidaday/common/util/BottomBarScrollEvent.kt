package com.gorczycait.vidaday.common.util

import kotlinx.coroutines.flow.MutableSharedFlow

class BottomBarScrollEvent {

    private val onScrollEvent = MutableSharedFlow<Boolean>(extraBufferCapacity = 1)

    fun onScroll(isScrollingUp: Boolean) {
        onScrollEvent.tryEmit(isScrollingUp)
    }

    suspend fun collectOnScroll(onScrollingUp: (Boolean) -> Unit) {
        onScrollEvent.collect(onScrollingUp)
    }
}
