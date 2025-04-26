package com.gorczycait.backbones.di

import com.gorczycait.backbones.presentation.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // viewModels
    viewModel { MainViewModel() }
}
