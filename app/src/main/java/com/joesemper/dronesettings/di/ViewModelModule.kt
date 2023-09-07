package com.joesemper.dronesettings.di

import com.joesemper.dronesettings.ui.home.HomeViewModel
import com.joesemper.dronesettings.ui.settings.SettingsViewModel
import com.joesemper.dronesettings.ui.settings.timeline.TimelineViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel() }
    viewModel { SettingsViewModel() }
    viewModel { TimelineViewModel() }
}