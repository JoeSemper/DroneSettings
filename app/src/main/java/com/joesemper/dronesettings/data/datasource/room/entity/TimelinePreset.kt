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
    @ColumnInfo val setId: Int = -1,
    @ColumnInfo val date: Long = Constants.DATE_NOT_SET,
    @ColumnInfo val delayTimeSec: String = "",
    @ColumnInfo val delayTimeMin: String = "",
    @ColumnInfo val cockingTimeSec: String = "",
    @ColumnInfo val cockingTimeMin: String = "",
    @ColumnInfo val cockingTimeEnabled: Boolean = true,
    @ColumnInfo val selfDestructionTimeSec: String = "",
    @ColumnInfo val selfDestructionTimeMin: String = ""
)