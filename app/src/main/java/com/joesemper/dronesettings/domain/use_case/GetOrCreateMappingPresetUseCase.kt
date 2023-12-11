package com.joesemper.dronesettings.domain.use_case

import com.joesemper.dronesettings.data.datasource.room.main.entity.MappingPreset
import com.joesemper.dronesettings.domain.repository.SettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetOrCreateMappingPresetUseCase(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val repository: SettingsRepository
) {

    suspend operator fun invoke(dataId: Int) = withContext(defaultDispatcher) {
        if (repository.isMappingPresetExists(dataId)) {
            repository.getMappingPresetByDataId(dataId)
        } else {
            val rowId = repository.createNewMappingPreset(
                MappingPreset(
                    dataId = dataId
                )
            )

            repository.getMappingPresetByRowId(rowId)
        }

    }
}