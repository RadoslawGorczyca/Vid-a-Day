package com.gorczycait.backbones.presentation.video

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph

@Destination<RootGraph>
@Composable
fun VideoScreen() {
    VideoScreen(
        test = "test",
    )
}

@Composable
private fun VideoScreen(
    test: String,
) {
    Box {
        Text(text = test)
    }
}
