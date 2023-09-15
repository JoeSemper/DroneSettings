package com.joesemper.dronesettings.domain.repository

import com.joesemper.dronesettings.data.datasource.room.entity.MappingPreset
import com.joesemper.dronesettings.data.datasource.room.entity.SensorsPreset
import com.joesemper.dronesettings.data.datasource.room.entity.SettingsPreset
import com.joesemper.dronesettings.data.datasource.room.entity.SettingsSet
import com.joesemper.dronesettings.data.datasource.room.entity.SignalPreset
import com.joesemper.dronesettings.data.datasource.room.entity.TimelinePreset
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getAllSettingsPresets(): Flow<List<SettingsSet>>
    suspend fun createNewSettingsSet(set: SettingsSet): Long
    suspend fun getSettingsSetByRowId(rowId: Long): SettingsSet
    suspend fun deleteSettingsSet(setId: Int)
    suspend fun createNewTimelinePreset(preset: TimelinePreset): Long
    fun getTimelinePresetByRowId(rowId: Long): Flow<TimelinePreset>
    fun getTimelinePresetBySettingsSetId(settingsSetId: Int): Flow<TimelinePreset>
    suspend fun updateTimelinePreset(timelinePreset: TimelinePreset)
    suspend fun isTimelinePresetExists(settingsPresetId: Int): Boolean
    suspend fun createNewSensorsPreset(preset: SensorsPreset): Long
    fun getSensorsPresetByRowId(rowId: Long): Flow<SensorsPreset>
    fun getSensorsPresetBySettingsSetId(settingsSetId: Int): Flow<SensorsPreset>
    suspend fun updateSensorsPreset(sensorsPreset: SensorsPreset)
    suspend fun isSensorsPresetExists(settingsPresetId: Int): Boolean
    suspend fun createNewMappingPreset(preset: MappingPreset): Long
    fun getMappingPresetByRowId(rowId: Long): Flow<MappingPreset>
    fun getMappingPresetBySettingsSetId(settingsSetId: Int): Flow<MappingPreset>
    suspend fun updateMappingPreset(mappingPreset: MappingPreset)
    suspend fun isMappingPresetExists(settingsPresetId: Int): Boolean
    suspend fun createNewSignalPreset(preset: SignalPreset): Long
    fun getSignalPresetByRowId(rowId: Long): Flow<SignalPreset>
    fun getSignalPresetBySettingsSetId(settingsSetId: Int): Flow<SignalPreset>
    suspend fun updateSignalPreset(signalPreset: SignalPreset)
    suspend fun isSignalPresetExists(settingsPresetId: Int): Boolean
    fun getSettingsPreset(settingsSetId: Int): Flow<SettingsPreset>
}