package com.gorczycait.backbones.presentation.common.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry
import com.ramcosta.composedestinations.animations.NavHostAnimatedDestinationStyle

const val TRANSITION_DURATION = 500

class NavigationTransitions(
    bottomBarScreens: List<String>,
    screensWithCloseAction: List<String>,
) : NavHostAnimatedDestinationStyle() {

    override val enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
        when (targetState.destination.route) {
            in bottomBarScreens -> EnterTransition.None
            in screensWithCloseAction -> when (initialState.destination.route) {
                in screensWithCloseAction -> slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(TRANSITION_DURATION),
                )

                else -> slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Up,
                    animationSpec = tween(TRANSITION_DURATION),
                )
            }

            else -> slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(TRANSITION_DURATION),
            )
        }
    }

    override val exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
        when (initialState.destination.route) {
            in bottomBarScreens -> ExitTransition.None
            else -> when (targetState.destination.route) {
                in screensWithCloseAction -> ExitTransition.None
                else -> slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(TRANSITION_DURATION),
                    targetOffset = { it / 3 },
                )
            }
        }
    }

    override val popEnterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
        when (targetState.destination.route) {
            in bottomBarScreens -> EnterTransition.None
            else -> when (initialState.destination.route) {
                in screensWithCloseAction -> EnterTransition.None
                else -> slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(TRANSITION_DURATION),
                    initialOffset = { it / 3 },
                )
            }
        }
    }

    override val popExitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
        when (initialState.destination.route) {
            in bottomBarScreens -> ExitTransition.None
            in screensWithCloseAction -> slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Down,
                animationSpec = tween(TRANSITION_DURATION),
            )

            else -> slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.End,
                animationSpec = tween(TRANSITION_DURATION),
            )
        }
    }
}
