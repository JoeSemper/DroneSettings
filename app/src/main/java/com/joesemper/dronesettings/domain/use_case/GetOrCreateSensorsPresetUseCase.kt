package com.joesemper.dronesettings.domain.use_case

import com.joesemper.dronesettings.data.datasource.room.entity.SensorsPreset
import com.joesemper.dronesettings.domain.repository.SettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetOrCreateSensorsPresetUseCase(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val repository: SettingsRepository
) {

    suspend operator fun invoke(settingsSetId: Int) = withContext(defaultDispatcher) {
        if (repository.isSensorsPresetExists(settingsSetId)) {
            repository.getSensorsPresetBySettingsSetId(settingsSetId)
        } else {
            val rowId = repository.createNewSensorsPreset(
                SensorsPreset(
                    setId = settingsSetId
                )
            )

            repository.getSensorsPresetByRowId(rowId)
        }

    }
}