package com.joesemper.dronesettings.data.datasource.room.prepopulated.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(
    tableName = "Variables"
)
@Parcelize
data class Variable(
    @PrimaryKey()
    @ColumnInfo(name = "id") val id: Int? = 0,
    @ColumnInfo(name = "name") val name: String? = "",
    @ColumnInfo(name = "description") val description: String? = ""
) : Parcelable
