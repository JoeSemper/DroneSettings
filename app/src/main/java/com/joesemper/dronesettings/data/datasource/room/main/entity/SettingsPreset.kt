package com.joesemper.dronesettings.data.datasource.room.main.entity

import androidx.room.Embedded
import androidx.room.Relation

data class SettingsPreset(
    @Embedded val presetData: PresetData = PresetData(),
    @Relation(
        parentColumn = "dataId",
        entityColumn = "dataId",
        entity = TimelinePreset::class
    )
    val  timelinePreset: TimelinePreset = TimelinePreset(),
//    @Relation(
//        parentColumn = "dataId",
//        entityColumn = "dataId",
//        entity = SensorsPreset::class
//    )
//    val  sensorsPreset: SensorsPreset = SensorsPreset(),
//    @Relation(
//        parentColumn = "dataId",
//        entityColumn = "dataId",
//        entity = MappingPreset::class
//    )
//    val  mappingPreset: MappingPreset = MappingPreset(),
//    @Relation(
//        parentColumn = "dataId",
//        entityColumn = "dataId",
//        entity = SignalPreset::class
//    )
//    val  signalPreset: SignalPreset = SignalPreset(),
)
