package com.joesemper.dronesettings.domain.use_case

import com.joesemper.dronesettings.data.datasource.room.main.entity.PresetData
import com.joesemper.dronesettings.domain.repository.SettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpdatePresetDataUseCase(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(data: PresetData) = withContext(defaultDispatcher) {
        repository.updatePresetData(data)
    }
}