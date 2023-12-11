package com.joesemper.dronesettings.data.datasource.room.main.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.joesemper.dronesettings.data.datasource.room.main.entity.MappingPreset
import com.joesemper.dronesettings.data.datasource.room.main.entity.PresetData
import com.joesemper.dronesettings.data.datasource.room.main.entity.SensorsPreset
import com.joesemper.dronesettings.data.datasource.room.main.entity.SettingsPreset
import com.joesemper.dronesettings.data.datasource.room.main.entity.SignalPreset
import com.joesemper.dronesettings.data.datasource.room.main.entity.TimelinePreset
import kotlinx.coroutines.flow.Flow


@Dao
interface SettingsDao {

    //Preset data

    @Query("SELECT * FROM PresetData")
    fun getAllPresetsData(): Flow<List<PresetData>>

    @Insert
    suspend fun createNewPresetData(data: PresetData): Long

    @Query("SELECT * FROM PresetData WHERE rowId = :rowId")
    fun getPresetDataByRowId(rowId: Long): PresetData

    @Update
    suspend fun updateSettingsSet(presetData: PresetData)

    @Query ("DELETE FROM PresetData WHERE dataId = :presetId")
    suspend fun deletePresetData(presetId: Int)

    //Timeline

    @Insert
    suspend fun createNewTimelinePreset(preset: TimelinePreset): Long

    @Query("SELECT * FROM TimelinePreset WHERE rowId = :rowId")
    fun getTimelinePresetByRowId(rowId: Long): Flow<TimelinePreset>

    @Query("SELECT * FROM TimelinePreset WHERE dataId = :dataId")
    fun getTimelinePresetByDataId(dataId: Int): Flow<TimelinePreset>

    @Update
    suspend fun updateTimelinePreset(timelinePreset: TimelinePreset)

    @Query("SELECT EXISTS(SELECT * FROM TimelinePreset WHERE id = :id)")
    suspend fun isTimelineExists(id: Int): Boolean

    @Query ("DELETE FROM TimelinePreset WHERE dataId = :dataId")
    suspend fun deleteTimelinePreset(dataId: Int)

    //Sensors

    @Insert
    suspend fun createNewSensorsPreset(preset: SensorsPreset): Long

    @Query("SELECT * FROM SensorsPreset WHERE rowId = :rowId")
    fun getSensorsPresetByRowId(rowId: Long): Flow<SensorsPreset>

    @Query("SELECT * FROM SensorsPreset WHERE dataId = :dataId")
    fun getSensorsPresetByDataId(dataId: Int): Flow<SensorsPreset>

    @Update
    suspend fun updateSensorsPreset(sensorsPreset: SensorsPreset)

    @Query("SELECT EXISTS(SELECT * FROM SensorsPreset WHERE id = :id)")
    suspend fun isSensorsRowExists(id: Int): Boolean

    @Query ("DELETE FROM SensorsPreset WHERE dataId = :dataId")
    suspend fun deleteSensorsPreset(dataId: Int)

    //Mapping

    @Insert
    suspend fun createNewMappingPreset(preset: MappingPreset): Long

    @Query("SELECT * FROM MappingPreset WHERE rowId = :rowId")
    fun getMappingPresetByRowId(rowId: Long): Flow<MappingPreset>

    @Query("SELECT * FROM MappingPreset WHERE dataId = :dataId")
    fun getMappingPresetByDataId(dataId: Int): Flow<MappingPreset>

    @Update
    suspend fun updateMappingPreset(mappingPreset: MappingPreset)

    @Query("SELECT EXISTS(SELECT * FROM MappingPreset WHERE id = :id)")
    suspend fun isMappingExists(id: Int): Boolean

    @Query ("DELETE FROM MappingPreset WHERE dataId = :dataId")
    suspend fun deleteMappingPreset(dataId: Int)

    //Signal

    @Insert
    suspend fun createNewSignalPreset(preset: SignalPreset): Long

    @Query("SELECT * FROM SignalPreset WHERE rowId = :rowId")
    fun getSignalPresetByRowId(rowId: Long): Flow<SignalPreset>

    @Query("SELECT * FROM SignalPreset WHERE dataId = :dataId")
    fun getSignalPresetByDataId(dataId: Int): Flow<SignalPreset>

    @Update
    suspend fun updateSignalPreset(signalPreset: SignalPreset)

    @Query("SELECT EXISTS(SELECT * FROM SignalPreset WHERE id = :id)")
    suspend fun isSignalRowExists(id: Int): Boolean

    @Query ("DELETE FROM SignalPreset WHERE dataId = :dataId")
    suspend fun deleteSignalPreset(dataId: Int)

    @Transaction
    @Query("SELECT * FROM PresetData WHERE dataId = :dataId")
    fun getSettingsPreset(dataId: Int): Flow<SettingsPreset?>
}