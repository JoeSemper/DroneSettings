package com.joesemper.dronesettings.data.repository

import com.joesemper.dronesettings.data.datasource.room.dao.SettingsDao
import com.joesemper.dronesettings.data.datasource.room.entity.MappingPreset
import com.joesemper.dronesettings.data.datasource.room.entity.SensorsPreset
import com.joesemper.dronesettings.data.datasource.room.entity.SettingsPreset
import com.joesemper.dronesettings.data.datasource.room.entity.SignalPreset
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

    override suspend fun deleteSettingsPreset(presetId: Int) {
        dao.deleteSettingsPreset(presetId)
        dao.deleteTimelinePreset(presetId)
        dao.deleteSensorsPreset(presetId)
        dao.deleteMappingPreset(presetId)
        dao.deleteSignalPreset(presetId)
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

    override fun getSensorsPresetBySettingsPresetId(settingsPresetId: Int): Flow<SensorsPreset> {
        return dao.getSensorsPresetBySettingsPresetId(settingsPresetId)
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

    override fun getMappingPresetBySettingsPresetId(settingsPresetId: Int): Flow<MappingPreset> {
        return dao.getMappingPresetBySettingsPresetId(settingsPresetId)
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

    override fun getSignalPresetBySettingsPresetId(settingsPresetId: Int): Flow<SignalPreset> {
        return dao.getSignalPresetBySettingsPresetId(settingsPresetId)
    }

    override suspend fun updateSignalPreset(signalPreset: SignalPreset) {
        dao.updateSignalPreset(signalPreset)
    }

    override suspend fun isSignalPresetExists(settingsPresetId: Int): Boolean {
        return dao.isSignalRowWithPresetIdExists(settingsPresetId)
    }

}