package com.joesemper.dronesettings.data.datasource.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.joesemper.dronesettings.data.datasource.room.dao.SettingsDao
import com.joesemper.dronesettings.data.datasource.room.entity.MappingPreset
import com.joesemper.dronesettings.data.datasource.room.entity.SensorsPreset
import com.joesemper.dronesettings.data.datasource.room.entity.SettingsSet
import com.joesemper.dronesettings.data.datasource.room.entity.SignalPreset
import com.joesemper.dronesettings.data.datasource.room.entity.TimelinePreset

@Database(
    entities = [
        SettingsSet::class,
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
