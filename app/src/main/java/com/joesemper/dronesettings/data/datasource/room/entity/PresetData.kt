package com.joesemper.dronesettings.data.datasource.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.joesemper.dronesettings.utils.Constants

@Entity
data class PresetData(
    @PrimaryKey(autoGenerate = true) val dataId: Int = 0,
    @ColumnInfo val name: String = "",
    @ColumnInfo val description: String = "",
    @ColumnInfo val date: Long = Constants.DATE_NOT_SET,
    @ColumnInfo val saved: Boolean = false,
)