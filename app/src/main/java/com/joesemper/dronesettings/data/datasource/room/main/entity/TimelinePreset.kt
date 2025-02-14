package com.joesemper.dronesettings.data.datasource.room.main.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.joesemper.dronesettings.utils.Constants

@Entity(
    foreignKeys = [ForeignKey(
        entity = PresetData::class,
        childColumns = ["dataId"],
        parentColumns = ["dataId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class TimelinePreset(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val dataId: Int = -1,
    @ColumnInfo val date: Long = Constants.DATE_NOT_SET,
    @ColumnInfo val delayTimeSec: String = "",
    @ColumnInfo val delayTimeMin: String = "",
    @ColumnInfo val cockingTimeSec: String = "",
    @ColumnInfo val cockingTimeMin: String = "",
    @ColumnInfo val selfDestructionTimeMin: String = "",
    @ColumnInfo val minBatteryVoltage: String = ""
)