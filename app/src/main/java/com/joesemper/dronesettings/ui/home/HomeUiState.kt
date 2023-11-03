package com.joesemper.dronesettings.ui.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class HomeUiState(
    val isLoading: Boolean = true,
    val searchQuery: MutableState<String> = mutableStateOf(""),
    val presets: List<PresetDataUiState> = emptyList()
) {
    val enableSearch
        get() = searchQuery.value.isNotEmpty() || presets.isNotEmpty()

    val filteredPresets
        get() = presets.filter {
            it.name.contains(searchQuery.value, ignoreCase = true) ||
                    it.description.contains(searchQuery.value, ignoreCase = true)
        }

}

data class PresetDataUiState(
    val dataId: Int = 0,
    val name: String = "",
    val description: String = "",
    val date: String = "",
    val time: String = "",
    val saved: Boolean = false,
)
