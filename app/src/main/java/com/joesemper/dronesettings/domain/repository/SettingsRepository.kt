package com.joesemper.dronesettings.domain.repository

import com.joesemper.dronesettings.data.datasource.room.entity.SettingsPreset
import com.joesemper.dronesettings.data.datasource.room.entity.TimelinePreset
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getAllSettingsPresets(): Flow<List<SettingsPreset>>
    suspend fun createNewSettingsPreset(preset: SettingsPreset): Long
    suspend fun getSettingsPresetByRowId(rowId: Long): SettingsPreset
    suspend fun createNewTimelinePreset(preset: TimelinePreset): Long
    fun getTimelinePresetByRowId(rowId: Long): Flow<TimelinePreset>
    fun getTimelinePresetBySettingsPresetId(settingsPresetId: Int): Flow<TimelinePreset>
    fun updateTimelinePreset(timelinePreset: TimelinePreset)
    suspend fun isTimelinePresetExists(settingsPresetId: Int): Boolean

}