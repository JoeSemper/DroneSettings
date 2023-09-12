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
data class SensorsPreset(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo val setId: Int,
    @ColumnInfo val date: Long = Constants.DATE_NOT_SET,
    @ColumnInfo val targetDistance: Int,
    @ColumnInfo val minVoltage: Int,
    @ColumnInfo val averageAcceleration: Int,
    @ColumnInfo val averageDeviation: Int,
    @ColumnInfo val deviationCoefficient: Int,
    @ColumnInfo val deadTime: Int,
    @ColumnInfo val overloadActivationEnabled: Boolean,
    @ColumnInfo val deadTimeEnabled: Boolean
)
