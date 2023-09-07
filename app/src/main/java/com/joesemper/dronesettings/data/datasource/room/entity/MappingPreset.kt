package com.joesemper.dronesettings.data.datasource.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MappingPreset(
    @PrimaryKey val id: Int,
    @ColumnInfo val presetId: Int,
    @ColumnInfo val relyOneState: Int,
    @ColumnInfo val relyTwoState: Int,
    @ColumnInfo val pulseOneState: Int,
    @ColumnInfo val pulseTwoState: Int,
    @ColumnInfo val pulseThreeState: Int
)