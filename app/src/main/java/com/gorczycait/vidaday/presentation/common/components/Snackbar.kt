package com.gorczycait.vidaday.presentation.common.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.AnimationConstants
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType.Companion.LongPress
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavController.OnDestinationChangedListener
import com.gorczycait.vidaday.R
import com.gorczycait.vidaday.presentation.common.components.SnackbarVariant.ERROR
import com.gorczycait.vidaday.presentation.common.components.SnackbarVariant.INFO
import com.gorczycait.vidaday.presentation.common.components.SnackbarVariant.SUCCESS
import com.gorczycait.vidaday.presentation.common.components.SnackbarVariant.WARNING
import com.gorczycait.vidaday.presentation.common.theme.BackbonesTheme
import com.gorczycait.vidaday.presentation.common.util.noIndicationClickable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@Composable
fun Snackbar(
    navController: NavController,
    snackbarHandler: SnackbarHandler,
) {
    val context = LocalContext.current
    val hapticFeedback = LocalHapticFeedback.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var visible by remember { mutableStateOf(false) }
    var currentRoute by remember { mutableStateOf<String?>(null) }
    var snackbarConfig by remember { mutableStateOf<SnackbarConfig?>(null) }
    val currentSnackbarData = snackbarHostState.currentSnackbarData

    val destinationChangedListener = OnDestinationChangedListener { _, destination, _ ->
        currentRoute = destination.route
        snackbarHostState.currentSnackbarData?.dismiss()
        visible = false
    }

    DisposableEffect(Unit) {
        navController.addOnDestinationChangedListener(destinationChangedListener)
        onDispose { navController.removeOnDestinationChangedListener(destinationChangedListener) }
    }

    LaunchedEffect(Unit) {
        snackbarHandler.showSnackbarEvent.collect { config ->
            snackbarConfig = config
            val message = config.message ?: config.messageResId?.let(context::getString)
            message?.let { snackbarHostState.showSnackbar(it) } ?: error("Missing snackbar message")
        }
    }

    val dismissCurrentSnackbar: suspend () -> Unit = {
        currentSnackbarData?.let {
            visible = false
            delay(AnimationConstants.DefaultDurationMillis.toLong())
            it.dismiss()
        }
    }

    LaunchedEffect(currentSnackbarData) {
        if (currentSnackbarData != null) {
            val duration = calculateSnackbarDuration(currentSnackbarData.visuals.message)
            visible = true
            hapticFeedback.performHapticFeedback(LongPress)
            delay(duration)
            dismissCurrentSnackbar()
        }
    }

    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(tween(500)) { it * 2 },
        exit = slideOutVertically(tween(500)) { it * 2 },
    ) {
        currentSnackbarData?.let {
            snackbarConfig?.let { snackbarConfig ->
                Snackbar(
                    scope = scope,
                    dismissCurrentSnackbar = dismissCurrentSnackbar,
                    currentSnackbarData = currentSnackbarData,
                    snackbarConfig = snackbarConfig,
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Snackbar(
    scope: CoroutineScope,
    dismissCurrentSnackbar: suspend () -> Unit,
    currentSnackbarData: SnackbarData,
    snackbarConfig: SnackbarConfig,
) {
    val keyboardBottomPadding = WindowInsets.ime.asPaddingValues().calculateBottomPadding()
    Row(
        modifier = Modifier
            .then(
                when {
                    WindowInsets.isImeVisible && keyboardBottomPadding.value > 0 ->
                        Modifier.padding(bottom = keyboardBottomPadding)

                    else -> Modifier
                },
            )
            .padding(8.dp)
            .then(snackbarConfig.extraPadding?.let { Modifier.padding(it) } ?: Modifier)
            .fillMaxWidth()
            .background(snackbarConfig.variant.getColor())
            .noIndicationClickable(onClick = { scope.launch { dismissCurrentSnackbar() } })
            .padding(16.dp),
        verticalAlignment = CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            painter = painterResource(snackbarConfig.variant.getIcon()),
            tint = snackbarConfig.variant.getIconTint(),
            contentDescription = null,
        )
        HorizontalSpacer(8.dp)
        Text(
            text = currentSnackbarData.visuals.message,
            modifier = Modifier.fillMaxWidth(),
            color = BackbonesTheme.colors.onScrim,
            overflow = Ellipsis,
            maxLines = 3,
            style = BackbonesTheme.typography.Body_XS_Medium,
        )
    }
}

class SnackbarHandler(private val scope: CoroutineScope) {

    private val _showSnackbarEvent = MutableSharedFlow<SnackbarConfig>()
    val showSnackbarEvent = _showSnackbarEvent.asSharedFlow()

    fun showSuccessSnackbar(messageResId: Int? = null, message: String? = null, extraPadding: PaddingValues? = null) {
        scope.launch {
            _showSnackbarEvent.emit(SnackbarConfig(messageResId, message, SUCCESS, extraPadding))
        }
    }

    fun showErrorSnackbar(messageResId: Int? = null, message: String? = null, extraPadding: PaddingValues? = null) {
        scope.launch {
            _showSnackbarEvent.emit(SnackbarConfig(messageResId, message, ERROR, extraPadding))
        }
    }

    fun showInfoSnackbar(messageResId: Int? = null, message: String? = null, extraPadding: PaddingValues? = null) {
        scope.launch {
            _showSnackbarEvent.emit(SnackbarConfig(messageResId, message, INFO, extraPadding))
        }
    }

    fun showWarningSnackbar(messageResId: Int? = null, message: String? = null, extraPadding: PaddingValues? = null) {
        scope.launch {
            _showSnackbarEvent.emit(SnackbarConfig(messageResId, message, WARNING, extraPadding))
        }
    }
}

data class SnackbarConfig(
    val messageResId: Int? = null,
    val message: String? = null,
    val variant: SnackbarVariant,
    val extraPadding: PaddingValues? = null,
)

enum class SnackbarVariant {
    SUCCESS,
    ERROR,
    INFO,
    WARNING,
}

@Composable
private fun SnackbarVariant.getColor() = when (this) {
    SUCCESS -> BackbonesTheme.colors.success
    ERROR -> BackbonesTheme.colors.surfaceQuaternary
    INFO -> BackbonesTheme.colors.surfaceQuaternary
    WARNING -> BackbonesTheme.colors.surfaceQuaternary
}

@DrawableRes
private fun SnackbarVariant.getIcon() = when (this) {
    SUCCESS -> R.drawable.ic_tick_filled
    ERROR -> R.drawable.ic_error
    INFO -> R.drawable.ic_info
    WARNING -> R.drawable.ic_warning
}

@Composable
private fun SnackbarVariant.getIconTint() = when (this) {
    SUCCESS -> BackbonesTheme.colors.onScrim
    INFO, WARNING, ERROR -> Color.Unspecified
}

private fun calculateSnackbarDuration(message: String) = (message.length * 35L)
    .coerceAtLeast(2000L)
    .coerceAtMost(4000L)
