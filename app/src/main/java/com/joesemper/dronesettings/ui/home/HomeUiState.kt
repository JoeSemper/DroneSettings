package com.joesemper.dronesettings.ui.home

sealed class HomeUiState() {
    object Loading: HomeUiState()
    class Loaded(val settingsSetList: Map<String, List<SettingsSetUiState>>): HomeUiState()
}

data class SettingsSetUiState(
    val setId: Int = 0,
    val name: String = "",
    val description: String = "",
    val date: String = "",
    val time: String = "",
    val saved: Boolean = false,
)
