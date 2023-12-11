package com.joesemper.dronesettings.data.datasource.room.prepopulated.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(
    tableName = "Commands"
)
@Parcelize
data class Command (
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int? = 0,
    @ColumnInfo(name = "command") val command: String? = "",
    @ColumnInfo(name = "response") val response: String? = "",
    @ColumnInfo(name = "description") val description: String? = ""
): Parcelable