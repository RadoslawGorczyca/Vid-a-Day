package com.gorczycait.backbones.presentation.main

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.generated.NavGraphs

class MainActivity : ComponentActivity() {

    // private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setOnExitAnimation()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DestinationsNavHost(navGraph = NavGraphs.root)
        }
    }

    private fun SplashScreen.setOnExitAnimation() {
        setOnExitAnimationListener { screen ->

            val slideUp = ObjectAnimator.ofFloat(
                screen.view,
                View.TRANSLATION_Y,
                0f,
                -screen.view.height.toFloat(),
            )

            val animationDuration = screen.iconAnimationDurationMillis
            val animationStart = screen.iconAnimationStartMillis
            val now = System.currentTimeMillis()
            val remainingDuration = (animationDuration - (now - animationStart))

            slideUp.interpolator = LinearInterpolator()
            slideUp.startDelay = remainingDuration
            slideUp.duration = 300L
            slideUp.doOnEnd { screen.remove() }
            slideUp.start()
        }
    }
}
