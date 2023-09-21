package com.joesemper.dronesettings.domain.repository

import com.joesemper.dronesettings.data.datasource.room.entity.MappingPreset
import com.joesemper.dronesettings.data.datasource.room.entity.PresetData
import com.joesemper.dronesettings.data.datasource.room.entity.SensorsPreset
import com.joesemper.dronesettings.data.datasource.room.entity.SettingsPreset
import com.joesemper.dronesettings.data.datasource.room.entity.SignalPreset
import com.joesemper.dronesettings.data.datasource.room.entity.TimelinePreset
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getAllSettingsPresets(): Flow<List<PresetData>>
    suspend fun createNewPresetData(data: PresetData): Long
    suspend fun getPresetDataByRowId(rowId: Long): PresetData
    suspend fun deletePreset(dataId: Int)
    suspend fun updatePresetData(data: PresetData)
    suspend fun createNewTimelinePreset(preset: TimelinePreset): Long
    fun getTimelinePresetByRowId(rowId: Long): Flow<TimelinePreset>
    fun getTimelinePresetByDataId(dataId: Int): Flow<TimelinePreset>
    suspend fun updateTimelinePreset(timelinePreset: TimelinePreset)
    suspend fun isTimelinePresetExists(dataId: Int): Boolean
    suspend fun createNewSensorsPreset(preset: SensorsPreset): Long
    fun getSensorsPresetByRowId(rowId: Long): Flow<SensorsPreset>
    fun getSensorsPresetByDataId(dataId: Int): Flow<SensorsPreset>
    suspend fun updateSensorsPreset(sensorsPreset: SensorsPreset)
    suspend fun isSensorsPresetExists(dataId: Int): Boolean
    suspend fun createNewMappingPreset(preset: MappingPreset): Long
    fun getMappingPresetByRowId(rowId: Long): Flow<MappingPreset>
    fun getMappingPresetByDataId(dataId: Int): Flow<MappingPreset>
    suspend fun updateMappingPreset(mappingPreset: MappingPreset)
    suspend fun isMappingPresetExists(dataId: Int): Boolean
    suspend fun createNewSignalPreset(preset: SignalPreset): Long
    fun getSignalPresetByRowId(rowId: Long): Flow<SignalPreset>
    fun getSignalPresetByDataId(dataId: Int): Flow<SignalPreset>
    suspend fun updateSignalPreset(signalPreset: SignalPreset)
    suspend fun isSignalPresetExists(dataId: Int): Boolean
    fun getSettingsPreset(dataId: Int): Flow<SettingsPreset?>
}