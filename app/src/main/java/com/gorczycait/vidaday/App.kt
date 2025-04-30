package com.gorczycait.vidaday

import android.app.Application
import com.gorczycait.vidaday.BuildConfig.DEBUG
import com.gorczycait.vidaday.di.appModule
import com.gorczycait.vidaday.di.databaseModule
import com.gorczycait.vidaday.presentation.common.components.video.ExoPlayerCache
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
