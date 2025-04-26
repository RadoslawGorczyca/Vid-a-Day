package com.gorczycait.backbones.presentation.common.components.video

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.C
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import com.gorczycait.backbones.presentation.common.components.video.ExoPlayerCache.provideCache
import java.io.File

@OptIn(UnstableApi::class)
object ExoPlayerCache {

    private const val CACHE_SIZE = 50L * 1024 * 1024

    private var instance: SimpleCache? = null

    fun initialize(applicationContext: Context) {
        val databaseProvider = StandaloneDatabaseProvider(applicationContext)
        SimpleCache(
            File(applicationContext.cacheDir.absolutePath + "/video_cache"),
            LeastRecentlyUsedCacheEvictor(CACHE_SIZE),
            databaseProvider,
        ).also { instance = it }
    }

    internal fun ExoPlayer.Builder.provideCache(context: Context): ExoPlayer.Builder =
        instance?.let {
            val defaultDataSourceFactory = DefaultDataSource.Factory(context)
            val cacheDataSourceFactory = CacheDataSource.Factory()
                .setCache(it)
                .setUpstreamDataSourceFactory(defaultDataSourceFactory)
            setMediaSourceFactory(DefaultMediaSourceFactory(cacheDataSourceFactory))
        } ?: this
}

@OptIn(UnstableApi::class)
fun exoPlayerInstanceFactory(
    context: Context,
    playerRepeatMode: Int = Player.REPEAT_MODE_ALL,
    playerAudioEnabled: Boolean = false,
) = ExoPlayer.Builder(context)
    .setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING)
    .provideCache(context = context)
    .build()
    .apply {
        repeatMode = playerRepeatMode
        trackSelectionParameters = trackSelectionParameters
            .buildUpon()
            .setTrackTypeDisabled(C.TRACK_TYPE_AUDIO, !playerAudioEnabled)
            .setMaxVideoSizeSd()
            .build()
    }
