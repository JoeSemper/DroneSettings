package com.joesemper.dronesettings.data.repository

import com.joesemper.dronesettings.data.datasource.room.dao.SettingsDao
import com.joesemper.dronesettings.data.datasource.room.entity.MappingPreset
import com.joesemper.dronesettings.data.datasource.room.entity.SensorsPreset
import com.joesemper.dronesettings.data.datasource.room.entity.SettingsPreset
import com.joesemper.dronesettings.data.datasource.room.entity.PresetData
import com.joesemper.dronesettings.data.datasource.room.entity.SignalPreset
import com.joesemper.dronesettings.data.datasource.room.entity.TimelinePreset
import com.joesemper.dronesettings.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class SettingsRepositoryImpl(private val dao: SettingsDao) : SettingsRepository {

    override fun getAllSettingsPresets(): Flow<List<PresetData>> {
        return dao.getAllPresetsData()
    }

    override suspend fun createNewPresetData(data: PresetData): Long {
        return dao.createNewPresetData(data)
    }

    override suspend fun updatePresetData(data: PresetData) {
        dao.updateSettingsSet(data)
    }

    override suspend fun getPresetDataByRowId(rowId: Long): PresetData {
        return dao.getPresetDataByRowId(rowId)
    }

    override suspend fun deletePreset(dataId: Int) {
        dao.deletePresetData(dataId)
    }

    override fun getTimelinePresetByDataId(dataId: Int): Flow<TimelinePreset> {
        return dao.getTimelinePresetByDataId(dataId)
    }

    override suspend fun createNewTimelinePreset(preset: TimelinePreset): Long {
        return dao.createNewTimelinePreset(preset)
    }

    override fun getTimelinePresetByRowId(rowId: Long): Flow<TimelinePreset> {
        return dao.getTimelinePresetByRowId(rowId)
    }

    override suspend fun updateTimelinePreset(timelinePreset: TimelinePreset) {
        dao.updateTimelinePreset(timelinePreset)
    }

    override suspend fun isTimelinePresetExists(dataId: Int): Boolean {
        return dao.isTimelineExists(dataId)
    }

    override suspend fun createNewSensorsPreset(preset: SensorsPreset): Long {
        return dao.createNewSensorsPreset(preset)
    }

    override fun getSensorsPresetByRowId(rowId: Long): Flow<SensorsPreset> {
        return dao.getSensorsPresetByRowId(rowId)
    }

    override fun getSensorsPresetByDataId(dataId: Int): Flow<SensorsPreset> {
        return dao.getSensorsPresetByDataId(dataId)
    }

    override suspend fun updateSensorsPreset(sensorsPreset: SensorsPreset) {
        dao.updateSensorsPreset(sensorsPreset)
    }

    override suspend fun isSensorsPresetExists(dataId: Int): Boolean {
        return dao.isSensorsRowExists(dataId)
    }

    override suspend fun createNewMappingPreset(preset: MappingPreset): Long {
        return dao.createNewMappingPreset(preset)
    }

    override fun getMappingPresetByRowId(rowId: Long): Flow<MappingPreset> {
        return dao.getMappingPresetByRowId(rowId)
    }

    override fun getMappingPresetByDataId(dataId: Int): Flow<MappingPreset> {
        return dao.getMappingPresetByDataId(dataId)
    }

    override suspend fun updateMappingPreset(mappingPreset: MappingPreset) {
        dao.updateMappingPreset(mappingPreset)
    }

    override suspend fun isMappingPresetExists(dataId: Int): Boolean {
        return dao.isMappingExists(dataId)
    }

    override suspend fun createNewSignalPreset(preset: SignalPreset): Long {
        return dao.createNewSignalPreset(preset)
    }

    override fun getSignalPresetByRowId(rowId: Long): Flow<SignalPreset> {
        return dao.getSignalPresetByRowId(rowId)
    }

    override fun getSignalPresetByDataId(dataId: Int): Flow<SignalPreset> {
        return dao.getSignalPresetByDataId(dataId)
    }

    override suspend fun updateSignalPreset(signalPreset: SignalPreset) {
        dao.updateSignalPreset(signalPreset)
    }

    override suspend fun isSignalPresetExists(dataId: Int): Boolean {
        return dao.isSignalRowExists(dataId)
    }

    override fun getSettingsPreset(dataId: Int): Flow<SettingsPreset?> {
        return dao.getSettingsPreset(dataId)
    }

}