package com.joesemper.dronesettings.domain.use_case

import com.joesemper.dronesettings.data.datasource.room.main.entity.SensorsPreset
import com.joesemper.dronesettings.domain.repository.SettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetOrCreateSensorsPresetUseCase(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val repository: SettingsRepository
) {

    suspend operator fun invoke(dataId: Int) = withContext(defaultDispatcher) {
        if (repository.isSensorsPresetExists(dataId)) {
            repository.getSensorsPresetByDataId(dataId)
        } else {
            val rowId = repository.createNewSensorsPreset(
                SensorsPreset(
                    dataId = dataId
                )
            )

            repository.getSensorsPresetByRowId(rowId)
        }

    }
}