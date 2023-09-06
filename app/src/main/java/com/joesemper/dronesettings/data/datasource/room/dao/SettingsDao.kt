package com.joesemper.dronesettings.data.datasource.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.joesemper.dronesettings.data.datasource.room.entity.TimelinePreset

@Dao
interface SettingsDao {
    @Query("SELECT * FROM TimelinePreset")
    fun getAll(): TimelinePreset
}