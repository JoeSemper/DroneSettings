package com.joesemper.dronesettings.data.datasource.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SensorsPreset(
    @PrimaryKey val id: Int,
    @ColumnInfo val presetId: Int,
    @ColumnInfo val targetDistance: Int,
    @ColumnInfo val minVoltage: Int,
    @ColumnInfo val averageAcceleration: Int,
    @ColumnInfo val averageDeviation: Int,
    @ColumnInfo val deviationCoefficient: Int,
    @ColumnInfo val deadTime: Int,
    @ColumnInfo val overloadActivationEnabled: Boolean,
    @ColumnInfo val deadTimeEnabled: Boolean
)
