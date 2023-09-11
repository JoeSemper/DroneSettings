package com.joesemper.dronesettings.domain.use_case

import com.joesemper.dronesettings.data.datasource.room.entity.SettingsPreset
import com.joesemper.dronesettings.domain.repository.SettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class CreateSettingsPresetUseCase(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val repository: SettingsRepository
) {

    suspend operator fun invoke() = withContext(defaultDispatcher) {
        val rowId = repository.createNewSettingsPreset(SettingsPreset())
        repository.getSettingsPresetByRowId(rowId)
    }

}