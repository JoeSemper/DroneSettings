package com.joesemper.dronesettings.domain.use_case

import com.joesemper.dronesettings.data.datasource.room.main.entity.TimelinePreset
import com.joesemper.dronesettings.domain.repository.SettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetOrCreateTimelinePresetUseCase(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(dataId: Int) =
        withContext(defaultDispatcher) {
            if (repository.isTimelinePresetExists(dataId)) {
                repository.getTimelinePresetByDataId(dataId)
            } else {
                val rowId = repository.createNewTimelinePreset(
                    TimelinePreset(
                        dataId = dataId
                    )
                )

                repository.getTimelinePresetByRowId(rowId)
            }
        }
}