package com.joesemper.dronesettings.data.repository

import com.joesemper.dronesettings.data.datasource.room.dao.SettingsDao
import com.joesemper.dronesettings.data.datasource.room.entity.SettingsPreset
import com.joesemper.dronesettings.data.datasource.room.entity.TimelinePreset
import com.joesemper.dronesettings.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class SettingsRepositoryImpl(private val dao: SettingsDao) : SettingsRepository {

    override fun getAllSettingsPresets(): Flow<List<SettingsPreset>> {
        return dao.getAllSettingsPresets()
    }

    override suspend fun createNewSettingsPreset(preset: SettingsPreset): Long {
        return dao.createNewSettingsPreset(preset)
    }

    override suspend fun getSettingsPresetByRowId(rowId: Long): SettingsPreset {
        return dao.getSettingsPresetByRowId(rowId)
    }

    override fun getTimelinePresetBySettingsPresetId(settingsPresetId: Int): Flow<TimelinePreset> {
        return dao.getTimelinePresetBySettingsPresetId(settingsPresetId)
    }

    override suspend fun createNewTimelinePreset(preset: TimelinePreset): Long {
        return dao.createNewTimelinePreset(preset)
    }

    override fun getTimelinePresetByRowId(rowId: Long): Flow<TimelinePreset> {
        return dao.getTimelinePresetByRowId(rowId)
    }

    override fun updateTimelinePreset(timelinePreset: TimelinePreset) {
        dao.updateTimelinePreset(timelinePreset)
    }

    override suspend fun isTimelinePresetExists(settingsPresetId: Int): Boolean {
        return dao.isTimelineRowWithPresetIdExists(settingsPresetId)
    }

}