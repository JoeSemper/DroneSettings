package com.joesemper.dronesettings.di

import com.joesemper.dronesettings.ui.home.HomeViewModel
import com.joesemper.dronesettings.ui.settings.SettingsViewModel
import com.joesemper.dronesettings.ui.settings.mapping.MappingViewModel
import com.joesemper.dronesettings.ui.settings.sensors.SensorsViewModel
import com.joesemper.dronesettings.ui.settings.signal.SignalViewModel
import com.joesemper.dronesettings.ui.settings.timeline.TimelineViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { SettingsViewModel() }
    viewModel { TimelineViewModel(get(), get(), get(), get()) }
    viewModel { SensorsViewModel(get(), get(), get()) }
    viewModel { MappingViewModel() }
    viewModel { SignalViewModel() }
}