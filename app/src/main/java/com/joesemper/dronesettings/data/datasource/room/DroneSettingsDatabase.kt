package com.joesemper.dronesettings.data.datasource.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.joesemper.dronesettings.data.datasource.room.dao.SettingsDao
import com.joesemper.dronesettings.data.datasource.room.entity.TimelinePreset

@Database(entities = [TimelinePreset::class], version = 1)
abstract class DroneSettingsDatabase(): RoomDatabase() {
    abstract fun settingsDao(): SettingsDao
}
