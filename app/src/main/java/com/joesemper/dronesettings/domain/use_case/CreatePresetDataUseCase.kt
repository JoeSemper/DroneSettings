package com.joesemper.dronesettings.domain.use_case

import com.joesemper.dronesettings.data.datasource.room.main.entity.PresetData
import com.joesemper.dronesettings.domain.repository.SettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Calendar
import kotlin.random.Random


class CreatePresetDataUseCase(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val repository: SettingsRepository
) {

    suspend operator fun invoke() = withContext(defaultDispatcher) {
        val cal = Calendar.getInstance()
        val rowId = repository.createNewPresetData(
            PresetData(
                name = "Preset #${(Random(cal.timeInMillis).nextFloat() * 10000).toInt()}",
                description = "No description",
                date = cal.timeInMillis
            )
        )
        repository.getPresetDataByRowId(rowId)
    }

}