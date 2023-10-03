package com.joesemper.dronesettings.di

import com.joesemper.dronesettings.domain.use_case.CreatePresetDataUseCase
import com.joesemper.dronesettings.domain.use_case.DeletePresetUseCase
import com.joesemper.dronesettings.domain.use_case.GetAllPresetsUseCase
import com.joesemper.dronesettings.domain.use_case.GetOrCreateMappingPresetUseCase
import com.joesemper.dronesettings.domain.use_case.GetOrCreateSensorsPresetUseCase
import com.joesemper.dronesettings.domain.use_case.GetOrCreateSignalPresetUseCase
import com.joesemper.dronesettings.domain.use_case.GetOrCreateTimelinePresetUseCase
import com.joesemper.dronesettings.domain.use_case.GetSettingsPresetUseCase
import com.joesemper.dronesettings.domain.use_case.UpdatePresetDataUseCase
import com.joesemper.dronesettings.domain.use_case.UpdatePresetUseCase
import com.joesemper.dronesettings.domain.use_case.validation.ValidateTimeInputUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { CreatePresetDataUseCase(repository = get()) }
    factory { GetAllPresetsUseCase(repository = get()) }
    factory { UpdatePresetUseCase(repository = get()) }
    factory { DeletePresetUseCase(repository = get()) }
    factory { GetOrCreateTimelinePresetUseCase(repository = get()) }
    factory { GetOrCreateSensorsPresetUseCase(repository = get()) }
    factory { GetOrCreateMappingPresetUseCase(repository = get()) }
    factory { GetOrCreateSignalPresetUseCase(repository = get()) }
    factory { GetSettingsPresetUseCase(repository = get()) }
    factory { UpdatePresetDataUseCase(repository = get()) }
    factory { ValidateTimeInputUseCase() }
}