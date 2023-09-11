package com.joesemper.dronesettings.data.datasource.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SettingsPreset(
    @PrimaryKey(autoGenerate = true) val presetId: Int = 0,
    @ColumnInfo val name: String = "",
    @ColumnInfo val description: String = "",
    @ColumnInfo val date: Long = 0
)