package com.joesemper.dronesettings.di

import com.joesemper.dronesettings.domain.use_case.CreateNewSettingsPresetUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { CreateNewSettingsPresetUseCase(get()) }
}