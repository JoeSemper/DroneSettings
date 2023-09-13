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
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val setId: Int = -1,
    @ColumnInfo val date: Long = Constants.DATE_NOT_SET,
    @ColumnInfo val cockingPulseWidthHi: String = "",
    @ColumnInfo val cockingPulseWidthLo: String = "",
    @ColumnInfo val cockingPulseAmount: String = "",
    @ColumnInfo val cockingPulseInfiniteEnabled: Boolean = true,
    @ColumnInfo val activationPulseWidthHi: String = "",
    @ColumnInfo val activationPulseWidthLo: String = "",
    @ColumnInfo val activationPulseAmount: String = "",
    @ColumnInfo val activationPulseInfiniteEnabled: Boolean = true,
)