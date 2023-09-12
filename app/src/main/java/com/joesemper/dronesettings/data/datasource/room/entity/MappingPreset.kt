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
data class MappingPreset(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo val setId: Int,
    @ColumnInfo val date: Long = Constants.DATE_NOT_SET,
    @ColumnInfo val relyOneState: Int,
    @ColumnInfo val relyTwoState: Int,
    @ColumnInfo val pulseOneState: Int,
    @ColumnInfo val pulseTwoState: Int,
    @ColumnInfo val pulseThreeState: Int
)