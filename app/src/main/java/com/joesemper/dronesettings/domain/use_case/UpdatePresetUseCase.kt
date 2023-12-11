package com.joesemper.dronesettings.domain.use_case

import com.joesemper.dronesettings.data.datasource.room.main.entity.MappingPreset
import com.joesemper.dronesettings.data.datasource.room.main.entity.SensorsPreset
import com.joesemper.dronesettings.data.datasource.room.main.entity.SignalPreset
import com.joesemper.dronesettings.data.datasource.room.main.entity.TimelinePreset
import com.joesemper.dronesettings.domain.repository.SettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpdatePresetUseCase(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val repository: SettingsRepository
) {
    suspend operator fun <T> invoke(preset: T) = withContext(defaultDispatcher) {
        when (preset) {
            is TimelinePreset -> {
                repository.updateTimelinePreset(preset)
            }

            is SensorsPreset -> {
                repository.updateSensorsPreset(preset)
            }

            is MappingPreset -> {
                repository.updateMappingPreset(preset)
            }

            is SignalPreset -> {
                repository.updateSignalPreset(preset)
            }
        }
    }
}
