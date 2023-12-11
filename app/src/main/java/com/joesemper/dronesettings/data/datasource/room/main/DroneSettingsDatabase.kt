package com.joesemper.dronesettings.data.datasource.room.main

import androidx.room.Database
import androidx.room.RoomDatabase
import com.joesemper.dronesettings.data.datasource.room.main.dao.SettingsDao
import com.joesemper.dronesettings.data.datasource.room.main.entity.MappingPreset
import com.joesemper.dronesettings.data.datasource.room.main.entity.SensorsPreset
import com.joesemper.dronesettings.data.datasource.room.main.entity.PresetData
import com.joesemper.dronesettings.data.datasource.room.main.entity.SignalPreset
import com.joesemper.dronesettings.data.datasource.room.main.entity.TimelinePreset

@Database(
    entities = [
        PresetData::class,
        TimelinePreset::class,
        SensorsPreset::class,
        MappingPreset::class,
        SignalPreset::class
    ],
    version = 1
)
abstract class DroneSettingsDatabase() : RoomDatabase() {
    abstract fun settingsDao(): SettingsDao
}
