package com.gorczycait.backbones.presentation.main

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.airbnb.android.showkase.annotation.ShowkaseComposable
import com.gorczycait.backbones.R
import com.gorczycait.backbones.presentation.common.components.Snackbar
import com.gorczycait.backbones.presentation.common.components.SnackbarHandler
import com.gorczycait.backbones.presentation.common.components.button.PrimaryButton
import com.gorczycait.backbones.presentation.common.navigation.navigateIfResumed
import com.gorczycait.backbones.presentation.common.theme.BackbonesTheme
import com.gorczycait.backbones.presentation.paparazzi.PaparazziGroups.SCREENS
import com.gorczycait.backbones.presentation.paparazzi.PreviewScreen
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.VideoScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>(start = true)
@Composable
fun MainScreen(
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier,
    // viewModel: MainViewModel = koinViewModel(),
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
                    MainScreen(
                        onNewVideoClick = navigator.navigateIfResumed({ VideoScreenDestination() }),
                    )
                }
            },
        )
    }
}

@Composable
private fun MainScreen(
    onNewVideoClick: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Center,
    ) {
        PrimaryButton(
            label = stringResource(R.string.new_video),
            onClick = onNewVideoClick,
        )
    }
}

@Preview(name = "Main Screen", group = SCREENS, showBackground = true)
@ShowkaseComposable
@Composable
private fun MainScreenPreview() {
    PreviewScreen {
        MainScreen {}
    }
}
