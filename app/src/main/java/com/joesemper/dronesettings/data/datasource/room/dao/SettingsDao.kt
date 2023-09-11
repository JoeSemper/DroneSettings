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

    //Timeline

    @Insert
    suspend fun createNewTimelinePreset(preset: TimelinePreset): Long

    @Query("SELECT * FROM TimelinePreset WHERE rowId = :rowId")
    fun getTimelinePresetByRowId(rowId: Long): Flow<TimelinePreset>

    @Query("SELECT * FROM TimelinePreset WHERE presetId = :settingsPresetId")
    fun getTimelinePresetBySettingsPresetId(settingsPresetId: Int): Flow<TimelinePreset>

    @Update
    fun updateTimelinePreset(timelinePreset: TimelinePreset)

    @Query("SELECT EXISTS(SELECT * FROM TimelinePreset WHERE id = :id)")
    suspend fun isTimelineRowWithPresetIdExists(id: Int): Boolean

    //Sensors

    @Insert
    suspend fun createNewSensorsPreset(preset: SensorsPreset): Long

    @Query("SELECT * FROM SensorsPreset WHERE rowId = :rowId")
    fun getSensorsPresetByRowId(rowId: Long): Flow<SensorsPreset>

    //Mapping

    @Insert
    suspend fun createNewMappingPreset(preset: MappingPreset): Long

    @Query("SELECT * FROM MappingPreset WHERE rowId = :rowId")
    fun getMappingPresetByRowId(rowId: Long): Flow<MappingPreset>

    //Signal

    @Insert
    suspend fun createNewSignalPreset(preset: SignalPreset): Long

    @Query("SELECT * FROM SignalPreset WHERE rowId = :rowId")
    fun getSignalPresetByRowId(rowId: Long): Flow<SignalPreset>

}