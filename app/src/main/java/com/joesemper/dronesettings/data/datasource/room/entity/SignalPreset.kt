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
data class SignalPreset(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo val setId: Int,
    @ColumnInfo val date: Long = Constants.DATE_NOT_SET,
    @ColumnInfo val cockingPulseWidthHi: Int,
    @ColumnInfo val cockingPulseWidthLo: Int,
    @ColumnInfo val cockingPulseAmount: Int,
    @ColumnInfo val cockingPulseInfiniteEnabled: Boolean,
    @ColumnInfo val activationPulseWidthHi: Int,
    @ColumnInfo val activationPulseWidthLo: Int,
    @ColumnInfo val activationPulseAmount: Int,
    @ColumnInfo val activationPulseInfiniteEnabled: Boolean,
)