package com.gorczycait.backbones.presentation.main

import android.annotation.SuppressLint
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.compose.rememberNavController
import com.gorczycait.backbones.presentation.common.components.Snackbar
import com.gorczycait.backbones.presentation.common.components.SnackbarHandler
import com.gorczycait.backbones.presentation.common.theme.BackbonesTheme
@SuppressLint("RestrictedApi")
@Composable
fun MainScreen(
    // viewModel: MainViewModel,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val snackbarHandler = remember(scope) { SnackbarHandler(scope) }

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
                }
            },
        )
    }
}
