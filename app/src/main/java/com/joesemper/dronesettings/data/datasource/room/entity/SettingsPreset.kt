package com.joesemper.dronesettings.data.datasource.room.entity

import androidx.room.Embedded
import androidx.room.Relation

data class SettingsPreset(
    @Embedded val settingsSet: SettingsSet = SettingsSet(),
    @Relation(
        parentColumn = "setId",
        entityColumn = "setId",
        entity = TimelinePreset::class
    )
    val  timelinePreset: TimelinePreset = TimelinePreset(),
    @Relation(
        parentColumn = "setId",
        entityColumn = "setId",
        entity = SensorsPreset::class
    )
    val  sensorsPreset: SensorsPreset = SensorsPreset(),
    @Relation(
        parentColumn = "setId",
        entityColumn = "setId",
        entity = MappingPreset::class
    )
    val  mappingPreset: MappingPreset = MappingPreset(),
    @Relation(
        parentColumn = "setId",
        entityColumn = "setId",
        entity = SignalPreset::class
    )
    val  signalPreset: SignalPreset = SignalPreset(),
)
