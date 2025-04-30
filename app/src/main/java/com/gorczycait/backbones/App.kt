package com.gorczycait.backbones

import android.app.Application
import com.gorczycait.backbones.BuildConfig.DEBUG
import com.gorczycait.backbones.di.appModule
import com.gorczycait.backbones.di.databaseModule
import com.gorczycait.backbones.presentation.common.components.video.ExoPlayerCache
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
        initTimber()
        initVideoCaches()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(appModule, databaseModule)
        }
    }

    private fun initTimber() {
        if (DEBUG) Timber.plant(Timber.DebugTree())
    }

    private fun initVideoCaches() {
        ExoPlayerCache.initialize(this)
    }
}
