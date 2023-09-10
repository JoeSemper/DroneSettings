package com.joesemper.dronesettings.data.datasource.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SignalPreset(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo val presetId: Int,
    @ColumnInfo val cockingPulseWidthHi: Int,
    @ColumnInfo val cockingPulseWidthLo: Int,
    @ColumnInfo val cockingPulseAmount: Int,
    @ColumnInfo val cockingPulseInfiniteEnabled: Boolean,
    @ColumnInfo val activationPulseWidthHi: Int,
    @ColumnInfo val activationPulseWidthLo: Int,
    @ColumnInfo val activationPulseAmount: Int,
    @ColumnInfo val activationPulseInfiniteEnabled: Boolean,
)