package com.gorczycait.vidaday.common.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle.State.RESUMED
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.dropUnlessResumed
import com.ramcosta.composedestinations.navigation.DestinationsNavOptionsBuilder
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultRecipient
import com.ramcosta.composedestinations.result.getOr
import com.ramcosta.composedestinations.spec.Direction
import com.ramcosta.composedestinations.spec.TypedDestinationSpec

fun DestinationsNavigator.popBackStack(
    direction: Direction,
    alternativeDirection: Direction,
    inclusive: Boolean,
) {
    val poppedFirst = popBackStack(direction, inclusive)
    if (!poppedFirst) {
        popBackStack(alternativeDirection, inclusive)
    }
}

fun DestinationsNavigator.navigateTo(
    lifecycleOwner: LifecycleOwner?,
    direction: Direction,
    builder: DestinationsNavOptionsBuilder.() -> Unit = {},
) {
    if (lifecycleOwner == null || lifecycleOwner.lifecycle.currentState.isAtLeast(RESUMED)) {
        navigate(
            direction = direction,
            builder = builder,
        )
    }
}

@Composable
fun <D : TypedDestinationSpec<*>, R> ResultRecipient<D, R>.onNonNullNavResult(onResult: (R) -> Unit) {
    onNavResult { it.getOr { null }?.let(onResult) }
}

@Composable
fun DestinationsNavigator.navigateIfResumed(
    direction: () -> Direction,
    builder: DestinationsNavOptionsBuilder.() -> Unit = {},
) = dropUnlessResumed {
    navigate(direction(), builder)
}

@Composable
fun <T> DestinationsNavigator.navigateIfResumed(
    direction: (T) -> Direction,
    builder: DestinationsNavOptionsBuilder.() -> Unit = {},
): (T) -> Unit = dropUnlessResumed { p ->
    navigate(direction(p), builder)
}

@Composable
fun <T1, T2> DestinationsNavigator.navigateIfResumed(
    direction: (T1, T2) -> Direction,
    builder: DestinationsNavOptionsBuilder.() -> Unit = {},
): (T1, T2) -> Unit = dropUnlessResumed { p1, p2 ->
    navigate(direction(p1, p2), builder)
}

@Composable
fun <T1, T2, T3> DestinationsNavigator.navigateIfResumed(
    direction: (T1, T2, T3) -> Direction,
    builder: DestinationsNavOptionsBuilder.() -> Unit = {},
): (T1, T2, T3) -> Unit = dropUnlessResumed { p1, p2, p3 ->
    navigate(direction(p1, p2, p3), builder)
}

@Composable
fun <T> dropUnlessResumed(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    block: (T) -> Unit,
): (T) -> Unit = { p ->
    if (lifecycleOwner.lifecycle.currentState.isAtLeast(RESUMED)) {
        block(p)
    }
}

@Composable
private fun <T1, T2> dropUnlessResumed(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    block: (T1, T2) -> Unit,
): (T1, T2) -> Unit = { p1, p2 ->
    if (lifecycleOwner.lifecycle.currentState.isAtLeast(RESUMED)) {
        block(p1, p2)
    }
}

@Composable
private fun <T1, T2, T3> dropUnlessResumed(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    block: (T1, T2, T3) -> Unit,
): (T1, T2, T3) -> Unit = { p1, p2, p3 ->
    if (lifecycleOwner.lifecycle.currentState.isAtLeast(RESUMED)) {
        block(p1, p2, p3)
    }
}
