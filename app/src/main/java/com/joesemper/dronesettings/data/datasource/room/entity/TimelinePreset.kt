package com.joesemper.dronesettings.data.datasource.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TimelinePreset(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo val presetId: Int,
    @ColumnInfo val delayTimeSec: Int,
    @ColumnInfo val cockingTimeSec: Int,
    @ColumnInfo val cockingTimeEnabled: Boolean,
    @ColumnInfo val selfDestructionTimeSec: Int
)