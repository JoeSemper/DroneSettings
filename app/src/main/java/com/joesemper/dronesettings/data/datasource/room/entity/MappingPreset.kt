package com.joesemper.dronesettings.data.datasource.room.entity

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
data class MappingPreset(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val dataId: Int = -1,
    @ColumnInfo val date: Long = Constants.DATE_NOT_SET,
    @ColumnInfo val relayOneState: Int = 0,
    @ColumnInfo val relayTwoState: Int = 0,
    @ColumnInfo val pulseOneState: Int = 0,
    @ColumnInfo val pulseTwoState: Int = 0,
    @ColumnInfo val pulseThreeState: Int = 0
)