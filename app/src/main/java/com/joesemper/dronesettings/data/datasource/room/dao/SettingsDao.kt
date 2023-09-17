package com.joesemper.dronesettings.data.datasource.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.joesemper.dronesettings.data.datasource.room.entity.MappingPreset
import com.joesemper.dronesettings.data.datasource.room.entity.SensorsPreset
import com.joesemper.dronesettings.data.datasource.room.entity.SettingsPreset
import com.joesemper.dronesettings.data.datasource.room.entity.SettingsSet
import com.joesemper.dronesettings.data.datasource.room.entity.SignalPreset
import com.joesemper.dronesettings.data.datasource.room.entity.TimelinePreset
import kotlinx.coroutines.flow.Flow


@Dao
interface SettingsDao {

    //Settings

    @Query("SELECT * FROM SettingsSet")
    fun getAllSettingsSets(): Flow<List<SettingsSet>>

    @Insert
    suspend fun createNewSettingsSet(preset: SettingsSet): Long

    @Query("SELECT * FROM SettingsSet WHERE rowId = :rowId")
    fun getSettingsPresetByRowId(rowId: Long): SettingsSet

    @Update
    suspend fun updateSettingsSet(settingsSet: SettingsSet)

    @Query ("DELETE FROM SettingsSet WHERE setId = :presetId")
    suspend fun deleteSettingsSet(presetId: Int)

    //Timeline

    @Insert
    suspend fun createNewTimelinePreset(preset: TimelinePreset): Long

    @Query("SELECT * FROM TimelinePreset WHERE rowId = :rowId")
    fun getTimelinePresetByRowId(rowId: Long): Flow<TimelinePreset>

    @Query("SELECT * FROM TimelinePreset WHERE setId = :settingsSetId")
    fun getTimelinePresetBySettingsSetId(settingsSetId: Int): Flow<TimelinePreset>

    @Update
    suspend fun updateTimelinePreset(timelinePreset: TimelinePreset)

    @Query("SELECT EXISTS(SELECT * FROM TimelinePreset WHERE id = :id)")
    suspend fun isTimelineRowWithPresetIdExists(id: Int): Boolean

    @Query ("DELETE FROM TimelinePreset WHERE setId = :presetId")
    suspend fun deleteTimelinePreset(presetId: Int)

    //Sensors

    @Insert
    suspend fun createNewSensorsPreset(preset: SensorsPreset): Long

    @Query("SELECT * FROM SensorsPreset WHERE rowId = :rowId")
    fun getSensorsPresetByRowId(rowId: Long): Flow<SensorsPreset>

    @Query("SELECT * FROM SensorsPreset WHERE setId = :settingsSetId")
    fun getSensorsPresetBySettingsSetId(settingsSetId: Int): Flow<SensorsPreset>

    @Update
    suspend fun updateSensorsPreset(sensorsPreset: SensorsPreset)

    @Query("SELECT EXISTS(SELECT * FROM SensorsPreset WHERE id = :id)")
    suspend fun isSensorsRowWithPresetIdExists(id: Int): Boolean

    @Query ("DELETE FROM SensorsPreset WHERE setId = :presetId")
    suspend fun deleteSensorsPreset(presetId: Int)

    //Mapping

    @Insert
    suspend fun createNewMappingPreset(preset: MappingPreset): Long

    @Query("SELECT * FROM MappingPreset WHERE rowId = :rowId")
    fun getMappingPresetByRowId(rowId: Long): Flow<MappingPreset>

    @Query("SELECT * FROM MappingPreset WHERE setId = :settingsSetId")
    fun getMappingPresetBySettingsSetId(settingsSetId: Int): Flow<MappingPreset>

    @Update
    suspend fun updateMappingPreset(mappingPreset: MappingPreset)

    @Query("SELECT EXISTS(SELECT * FROM MappingPreset WHERE id = :id)")
    suspend fun isMappingRowWithPresetIdExists(id: Int): Boolean

    @Query ("DELETE FROM MappingPreset WHERE setId = :presetId")
    suspend fun deleteMappingPreset(presetId: Int)

    //Signal

    @Insert
    suspend fun createNewSignalPreset(preset: SignalPreset): Long

    @Query("SELECT * FROM SignalPreset WHERE rowId = :rowId")
    fun getSignalPresetByRowId(rowId: Long): Flow<SignalPreset>

    @Query("SELECT * FROM SignalPreset WHERE setId = :settingsSetId")
    fun getSignalPresetBySettingsSetId(settingsSetId: Int): Flow<SignalPreset>

    @Update
    suspend fun updateSignalPreset(signalPreset: SignalPreset)

    @Query("SELECT EXISTS(SELECT * FROM SignalPreset WHERE id = :id)")
    suspend fun isSignalRowWithPresetIdExists(id: Int): Boolean

    @Query ("DELETE FROM SignalPreset WHERE setId = :presetId")
    suspend fun deleteSignalPreset(presetId: Int)

    @Transaction
    @Query("SELECT * FROM SettingsSet WHERE setId = :settingsSetId")
    fun getSettingsPreset(settingsSetId: Int): Flow<SettingsPreset?>
}