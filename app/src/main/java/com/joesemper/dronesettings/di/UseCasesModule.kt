package com.joesemper.dronesettings.di

import com.joesemper.dronesettings.domain.use_case.CreateSettingsSetUseCase
import com.joesemper.dronesettings.domain.use_case.DeleteSettingsSetUseCase
import com.joesemper.dronesettings.domain.use_case.GetAllSettingsSetsUseCase
import com.joesemper.dronesettings.domain.use_case.GetOrCreateTimelinePresetUseCase
import com.joesemper.dronesettings.domain.use_case.UpdatePresetUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { CreateSettingsSetUseCase(repository = get()) }
    factory { GetOrCreateTimelinePresetUseCase(repository = get()) }
    factory { GetAllSettingsSetsUseCase(repository = get()) }
    factory { UpdatePresetUseCase(repository = get()) }
    factory { DeleteSettingsSetUseCase(repository = get()) }
}