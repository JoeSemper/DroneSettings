package com.joesemper.dronesettings.ui.home

data class HomeUiState(
    val isLoading: Boolean = true,
    val presets: Map<String, List<PresetDataUiState>> = emptyMap()
)

data class PresetDataUiState(
    val dataId: Int = 0,
    val name: String = "",
    val description: String = "",
    val date: String = "",
    val time: String = "",
    val saved: Boolean = false,
)
