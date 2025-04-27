package com.gorczycait.backbones.di

import com.gorczycait.backbones.presentation.main.MainViewModel
import com.gorczycait.backbones.presentation.video.VideoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // viewModels
    viewModel { MainViewModel() }
    viewModel { VideoViewModel(get()) }
}
