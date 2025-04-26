package com.gorczycait.backbones

import android.app.Application
import com.gorczycait.backbones.BuildConfig.DEBUG
import com.gorczycait.backbones.presentation.common.components.video.ExoPlayerCache
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
        initVideoCaches()
    }

    private fun initTimber() {
        if (DEBUG) Timber.plant(Timber.DebugTree())
    }

    private fun initVideoCaches() {
        ExoPlayerCache.initialize(this)
    }
}
