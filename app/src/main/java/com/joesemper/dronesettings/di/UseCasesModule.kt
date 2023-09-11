package com.joesemper.dronesettings.di

import com.joesemper.dronesettings.domain.use_case.CreateSettingsPresetUseCase
import com.joesemper.dronesettings.domain.use_case.GetOrCreateTimelinePresetUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { CreateSettingsPresetUseCase(repository = get()) }
    factory { GetOrCreateTimelinePresetUseCase(repository = get()) }
}