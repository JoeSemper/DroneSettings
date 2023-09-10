package com.joesemper.dronesettings.domain.repository

import com.joesemper.dronesettings.data.datasource.room.entity.SettingsPreset
import com.joesemper.dronesettings.data.datasource.room.entity.TimelinePreset
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getAllSettingsPresets(): Flow<List<SettingsPreset>>
    suspend fun createNewSettingsPreset(preset: SettingsPreset): Long
    fun getSettingsPresetByRowId(rowId: Long): Flow<SettingsPreset>
    suspend fun createNewTimelinePreset(preset: TimelinePreset): Long
    fun getTimelinePresetByRowId(rowId: Long): Flow<TimelinePreset>
    fun updateTimelinePreset(timelinePreset: TimelinePreset)

}