package com.joesemper.dronesettings.domain.entity

import com.joesemper.dronesettings.utils.Constants

data class SettingsPreset(
    val dataId: Int = 0,
    val name: String = "",
    val description: String = "",
    val date: Long = Constants.DATE_NOT_SET,
    val saved: Boolean = false,
    val settings: Settings = Settings()
)