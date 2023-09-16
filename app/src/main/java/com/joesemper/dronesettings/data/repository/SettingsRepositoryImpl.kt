package com.joesemper.dronesettings.data.repository

import com.joesemper.dronesettings.data.datasource.room.dao.SettingsDao
import com.joesemper.dronesettings.data.datasource.room.entity.MappingPreset
import com.joesemper.dronesettings.data.datasource.room.entity.SensorsPreset
import com.joesemper.dronesettings.data.datasource.room.entity.SettingsPreset
import com.joesemper.dronesettings.data.datasource.room.entity.SettingsSet
import com.joesemper.dronesettings.data.datasource.room.entity.SignalPreset
import com.joesemper.dronesettings.data.datasource.room.entity.TimelinePreset
import com.joesemper.dronesettings.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class SettingsRepositoryImpl(private val dao: SettingsDao) : SettingsRepository {

    override fun getAllSettingsPresets(): Flow<List<SettingsSet>> {
        return dao.getAllSettingsSets()
    }

    override suspend fun createNewSettingsSet(set: SettingsSet): Long {
        return dao.createNewSettingsSet(set)
    }

    override suspend fun updateSettingsSet(set: SettingsSet) {
        dao.updateSettingsSet(set)
    }

    override suspend fun getSettingsSetByRowId(rowId: Long): SettingsSet {
        return dao.getSettingsPresetByRowId(rowId)
    }

    override suspend fun deleteSettingsSet(setId: Int) {
        dao.deleteSettingsSet(setId)
    }

    override fun getTimelinePresetBySettingsSetId(settingsSetId: Int): Flow<TimelinePreset> {
        return dao.getTimelinePresetBySettingsSetId(settingsSetId)
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

    override suspend fun isTimelinePresetExists(settingsPresetId: Int): Boolean {
        return dao.isTimelineRowWithPresetIdExists(settingsPresetId)
    }

    override suspend fun createNewSensorsPreset(preset: SensorsPreset): Long {
        return dao.createNewSensorsPreset(preset)
    }

    override fun getSensorsPresetByRowId(rowId: Long): Flow<SensorsPreset> {
        return dao.getSensorsPresetByRowId(rowId)
    }

    override fun getSensorsPresetBySettingsSetId(settingsSetId: Int): Flow<SensorsPreset> {
        return dao.getSensorsPresetBySettingsSetId(settingsSetId)
    }

    override suspend fun updateSensorsPreset(sensorsPreset: SensorsPreset) {
        dao.updateSensorsPreset(sensorsPreset)
    }

    override suspend fun isSensorsPresetExists(settingsPresetId: Int): Boolean {
        return dao.isSensorsRowWithPresetIdExists(settingsPresetId)
    }

    override suspend fun createNewMappingPreset(preset: MappingPreset): Long {
        return dao.createNewMappingPreset(preset)
    }

    override fun getMappingPresetByRowId(rowId: Long): Flow<MappingPreset> {
        return dao.getMappingPresetByRowId(rowId)
    }

    override fun getMappingPresetBySettingsSetId(settingsSetId: Int): Flow<MappingPreset> {
        return dao.getMappingPresetBySettingsSetId(settingsSetId)
    }

    override suspend fun updateMappingPreset(mappingPreset: MappingPreset) {
        dao.updateMappingPreset(mappingPreset)
    }

    override suspend fun isMappingPresetExists(settingsPresetId: Int): Boolean {
        return dao.isMappingRowWithPresetIdExists(settingsPresetId)
    }

    override suspend fun createNewSignalPreset(preset: SignalPreset): Long {
        return dao.createNewSignalPreset(preset)
    }

    override fun getSignalPresetByRowId(rowId: Long): Flow<SignalPreset> {
        return dao.getSignalPresetByRowId(rowId)
    }

    override fun getSignalPresetBySettingsSetId(settingsSetId: Int): Flow<SignalPreset> {
        return dao.getSignalPresetBySettingsSetId(settingsSetId)
    }

    override suspend fun updateSignalPreset(signalPreset: SignalPreset) {
        dao.updateSignalPreset(signalPreset)
    }

    override suspend fun isSignalPresetExists(settingsPresetId: Int): Boolean {
        return dao.isSignalRowWithPresetIdExists(settingsPresetId)
    }

    override fun getSettingsPreset(settingsSetId: Int): Flow<SettingsPreset> {
        return dao.getSettingsPreset(settingsSetId)
    }

}