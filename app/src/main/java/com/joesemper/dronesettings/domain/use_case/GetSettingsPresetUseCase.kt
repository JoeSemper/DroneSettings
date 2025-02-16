package com.joesemper.dronesettings.domain.use_case

import com.joesemper.dronesettings.domain.entity.Settings
import com.joesemper.dronesettings.domain.entity.SettingsPreset
import com.joesemper.dronesettings.domain.repository.SettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class GetSettingsPresetUseCase(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(dataId: Int) = withContext(defaultDispatcher) {
        repository.getSettingsPreset(dataId).map { it ->
            it?.let {
                SettingsPreset(
                    dataId = it.presetData.dataId,
                    name = it.presetData.name,
                    description = it.presetData.description,
                    date = it.presetData.date,
                    saved = it.presetData.saved,
                    settings = Settings(
                        delayTimeSec = it.timelinePreset.delayTimeSec,
                        delayTimeMin = it.timelinePreset.delayTimeMin,
                        cockingTimeSec = it.timelinePreset.cockingTimeSec,
                        cockingTimeMin = it.timelinePreset.cockingTimeMin,
                        selfDestructionTimeMin = it.timelinePreset.selfDestructionTimeMin,
                        minBatteryVoltage = it.timelinePreset.minBatteryVoltage
                    )
                )
            }
        }
    }
}