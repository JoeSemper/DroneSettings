package com.joesemper.dronesettings.domain.use_case

import com.joesemper.dronesettings.data.datasource.room.entity.SettingsPreset
import com.joesemper.dronesettings.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow


class CreateNewSettingsPresetUseCase(private val repository: SettingsRepository) {

    suspend operator fun invoke(): Flow<SettingsPreset> {

        val rowId = repository.createNewSettingsPreset(
            SettingsPreset(
                presetId = 0,
                name = "",
                description = "",
                date = 0
            )
        )

        return repository.getSettingsPresetByRowId(rowId)
    }

}