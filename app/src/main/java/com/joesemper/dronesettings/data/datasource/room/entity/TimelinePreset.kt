package com.joesemper.dronesettings.data.datasource.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TimelinePreset(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val presetId: Int = 0,
    @ColumnInfo val delayTimeSec: Int = 0,
    @ColumnInfo val cockingTimeSec: Int = 0,
    @ColumnInfo val cockingTimeEnabled: Boolean = true,
    @ColumnInfo val selfDestructionTimeSec: Int = 0
)