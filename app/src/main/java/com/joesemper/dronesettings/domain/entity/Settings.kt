package com.joesemper.dronesettings.domain.entity

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.joesemper.dronesettings.utils.Constants

class Settings(
    val delayTimeSec: String = "",
    val delayTimeMin: String = "",
    val cockingTimeSec: String = "",
    val cockingTimeMin: String = "",
    val selfDestructionTimeMin: String = "",
    val minBatteryVoltage: String = ""
)