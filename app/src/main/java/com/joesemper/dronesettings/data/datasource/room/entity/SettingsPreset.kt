package com.joesemper.dronesettings.data.datasource.room.entity

import androidx.room.Embedded
import androidx.room.Relation

data class SettingsPreset(
    @Embedded val settingsSet: SettingsSet,
    @Relation(
        parentColumn = "setId",
        entityColumn = "setId",
        entity = TimelinePreset::class
    )
    val  timelinePreset: TimelinePreset,
    @Relation(
        parentColumn = "setId",
        entityColumn = "setId",
        entity = SensorsPreset::class
    )
    val  sensorsPreset: SensorsPreset,
    @Relation(
        parentColumn = "setId",
        entityColumn = "setId",
        entity = MappingPreset::class
    )
    val  mappingPreset: MappingPreset,
    @Relation(
        parentColumn = "setId",
        entityColumn = "setId",
        entity = SignalPreset::class
    )
    val  signalPreset: SignalPreset,
)
