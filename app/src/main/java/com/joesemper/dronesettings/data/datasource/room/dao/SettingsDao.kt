package com.joesemper.dronesettings.data.datasource.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.joesemper.dronesettings.data.datasource.room.entity.MappingPreset
import com.joesemper.dronesettings.data.datasource.room.entity.SensorsPreset
import com.joesemper.dronesettings.data.datasource.room.entity.SettingsPreset
import com.joesemper.dronesettings.data.datasource.room.entity.SignalPreset
import com.joesemper.dronesettings.data.datasource.room.entity.TimelinePreset
import kotlinx.coroutines.flow.Flow


@Dao
interface SettingsDao {

    //Settings

    @Query("SELECT * FROM SettingsPreset")
    fun getAllSettingsPresets(): Flow<List<SettingsPreset>>

    @Insert
    suspend fun createNewSettingsPreset(preset: SettingsPreset): Long

    @Query("SELECT * FROM SettingsPreset WHERE rowId = :rowId")
    fun getSettingsPresetByRowId(rowId: Long): SettingsPreset

    @Update
    suspend fun updateSettingsPreset(settingsPreset: SettingsPreset)

    @Query ("DELETE FROM SettingsPreset WHERE presetId = :presetId")
    suspend fun deleteSettingsPreset(presetId: Int)

    //Timeline

    @Insert
    suspend fun createNewTimelinePreset(preset: TimelinePreset): Long

    @Query("SELECT * FROM TimelinePreset WHERE rowId = :rowId")
    fun getTimelinePresetByRowId(rowId: Long): Flow<TimelinePreset>

    @Query("SELECT * FROM TimelinePreset WHERE presetId = :settingsPresetId")
    fun getTimelinePresetBySettingsPresetId(settingsPresetId: Int): Flow<TimelinePreset>

    @Update
    suspend fun updateTimelinePreset(timelinePreset: TimelinePreset)

    @Query("SELECT EXISTS(SELECT * FROM TimelinePreset WHERE id = :id)")
    suspend fun isTimelineRowWithPresetIdExists(id: Int): Boolean

    @Query ("DELETE FROM TimelinePreset WHERE presetId = :presetId")
    suspend fun deleteTimelinePreset(presetId: Int)

    //Sensors

    @Insert
    suspend fun createNewSensorsPreset(preset: SensorsPreset): Long

    @Query("SELECT * FROM SensorsPreset WHERE rowId = :rowId")
    fun getSensorsPresetByRowId(rowId: Long): Flow<SensorsPreset>

    @Query("SELECT * FROM SensorsPreset WHERE presetId = :settingsPresetId")
    fun getSensorsPresetBySettingsPresetId(settingsPresetId: Int): Flow<SensorsPreset>

    @Update
    suspend fun updateSensorsPreset(sensorsPreset: SensorsPreset)

    @Query("SELECT EXISTS(SELECT * FROM SensorsPreset WHERE id = :id)")
    suspend fun isSensorsRowWithPresetIdExists(id: Int): Boolean

    @Query ("DELETE FROM SensorsPreset WHERE presetId = :presetId")
    suspend fun deleteSensorsPreset(presetId: Int)

    //Mapping

    @Insert
    suspend fun createNewMappingPreset(preset: MappingPreset): Long

    @Query("SELECT * FROM MappingPreset WHERE rowId = :rowId")
    fun getMappingPresetByRowId(rowId: Long): Flow<MappingPreset>

    @Query("SELECT * FROM MappingPreset WHERE presetId = :settingsPresetId")
    fun getMappingPresetBySettingsPresetId(settingsPresetId: Int): Flow<MappingPreset>

    @Update
    suspend fun updateMappingPreset(mappingPreset: MappingPreset)

    @Query("SELECT EXISTS(SELECT * FROM MappingPreset WHERE id = :id)")
    suspend fun isMappingRowWithPresetIdExists(id: Int): Boolean

    @Query ("DELETE FROM MappingPreset WHERE presetId = :presetId")
    suspend fun deleteMappingPreset(presetId: Int)

    //Signal

    @Insert
    suspend fun createNewSignalPreset(preset: SignalPreset): Long

    @Query("SELECT * FROM SignalPreset WHERE rowId = :rowId")
    fun getSignalPresetByRowId(rowId: Long): Flow<SignalPreset>

    @Query("SELECT * FROM SignalPreset WHERE presetId = :settingsPresetId")
    fun getSignalPresetBySettingsPresetId(settingsPresetId: Int): Flow<SignalPreset>

    @Update
    suspend fun updateSignalPreset(signalPreset: SignalPreset)

    @Query("SELECT EXISTS(SELECT * FROM SignalPreset WHERE id = :id)")
    suspend fun isSignalRowWithPresetIdExists(id: Int): Boolean

    @Query ("DELETE FROM SignalPreset WHERE presetId = :presetId")
    suspend fun deleteSignalPreset(presetId: Int)

}