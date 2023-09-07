package com.joesemper.dronesettings.data.datasource.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.joesemper.dronesettings.data.datasource.room.entity.SettingsPreset
import kotlinx.coroutines.flow.Flow


@Dao
interface SettingsDao {
    @Query("SELECT * FROM SettingsPreset")
    fun getAllSettingsPresets(): Flow<List<SettingsPreset>>

}