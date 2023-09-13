package com.joesemper.dronesettings.domain.use_case

import com.joesemper.dronesettings.data.datasource.room.entity.TimelinePreset
import com.joesemper.dronesettings.domain.repository.SettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetOrCreateTimelinePresetUseCase(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(settingsSetId: Int) =
        withContext(defaultDispatcher) {
            if (repository.isTimelinePresetExists(settingsSetId)) {
                repository.getTimelinePresetBySettingsSetId(settingsSetId)
            } else {
                val rowId = repository.createNewTimelinePreset(
                    TimelinePreset(
                        setId = settingsSetId
                    )
                )

                repository.getTimelinePresetByRowId(rowId)
            }
        }
}