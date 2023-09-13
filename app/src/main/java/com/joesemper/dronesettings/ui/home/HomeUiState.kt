package com.joesemper.dronesettings.ui.home

data class HomeUiState(
    val settingsSetList: List<SettingsSetUiState>
)

data class SettingsSetUiState(
    val setId: Int = 0,
    val name: String = "",
    val description: String = "",
    val date: String = "",
    val time: String = "",
    val saved: Boolean = false,
)
