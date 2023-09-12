package com.joesemper.dronesettings.data.datasource.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.joesemper.dronesettings.utils.Constants

@Entity(
    foreignKeys = [ForeignKey(
        entity = SettingsSet::class,
        childColumns = ["setId"],
        parentColumns = ["setId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class TimelinePreset(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val setId: Int = 0,
    @ColumnInfo val date: Long = Constants.DATE_NOT_SET,
    @ColumnInfo val delayTimeSec: Int = 0,
    @ColumnInfo val cockingTimeSec: Int = 0,
    @ColumnInfo val cockingTimeEnabled: Boolean = true,
    @ColumnInfo val selfDestructionTimeSec: Int = 0
)