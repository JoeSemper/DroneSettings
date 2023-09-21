package com.joesemper.dronesettings.domain.use_case

import com.joesemper.dronesettings.data.datasource.room.entity.SignalPreset
import com.joesemper.dronesettings.domain.repository.SettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetOrCreateSignalPresetUseCase (
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val repository: SettingsRepository
) {

    suspend operator fun invoke(dataId: Int) = withContext(defaultDispatcher) {
        if (repository.isSignalPresetExists(dataId)) {
            repository.getSignalPresetByDataId(dataId)
        } else {
            val rowId = repository.createNewSignalPreset(
                SignalPreset(
                    dataId = dataId
                )
            )

            repository.getSignalPresetByRowId(rowId)
        }

    }
}