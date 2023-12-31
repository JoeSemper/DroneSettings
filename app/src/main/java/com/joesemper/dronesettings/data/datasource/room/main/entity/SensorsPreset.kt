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
data class SensorsPreset(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val dataId: Int = -1,
    @ColumnInfo val date: Long = Constants.DATE_NOT_SET,
    @ColumnInfo val targetDistance: Float = 0f,
    @ColumnInfo val minVoltage: String = "",
    @ColumnInfo val batteryActivationEnabled: Boolean = true,
    @ColumnInfo val averageAcceleration: String = "",
    @ColumnInfo val averageDeviation: String = "",
    @ColumnInfo val deviationCoefficient: String = "",
    @ColumnInfo val deadTime: String = "",
    @ColumnInfo val overloadActivationEnabled: Boolean = true,
    @ColumnInfo val deadTimeActivationEnabled: Boolean = true
)
