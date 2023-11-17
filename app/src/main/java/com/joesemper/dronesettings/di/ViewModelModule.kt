package com.joesemper.dronesettings.di

import com.joesemper.dronesettings.ui.home.HomeViewModel
import com.joesemper.dronesettings.ui.preset.PresetViewModel
import com.joesemper.dronesettings.ui.settings.screens.mapping.MappingViewModel
import com.joesemper.dronesettings.ui.settings.screens.sensors.SensorsViewModel
import com.joesemper.dronesettings.ui.settings.screens.signal.SignalViewModel
import com.joesemper.dronesettings.ui.settings.screens.timeline.TimelineViewModel
import com.joesemper.dronesettings.ui.terminal.TerminalViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { TimelineViewModel(get(), get(), get(), get(), get()) }
    viewModel { SensorsViewModel(get(), get(), get(), get()) }
    viewModel { MappingViewModel(get(), get(), get(), get()) }
    viewModel { SignalViewModel(get(), get(), get(), get()) }
    viewModel { PresetViewModel(get(), get(), get(), get()) }
    viewModel { TerminalViewModel() }
}