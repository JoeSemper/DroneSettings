package com.joesemper.dronesettings.ui.preset

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joesemper.dronesettings.data.datasource.room.entity.SettingsPreset
import com.joesemper.dronesettings.domain.use_case.GetSettingsPresetUseCase
import com.joesemper.dronesettings.ui.SETTINGS_SET_ID_ARG
import kotlinx.coroutines.launch

class PresetViewModel(
    savedStateHandle: SavedStateHandle,
    private val getSettingsPreset: GetSettingsPresetUseCase
) : ViewModel() {

    val uiState = mutableStateOf<PresetUiState>(PresetUiState.Loading)

    private val settingsSetId: Int = checkNotNull(savedStateHandle[SETTINGS_SET_ID_ARG])

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            getSettingsPreset(settingsSetId).collect {
                updateUiData(it)
            }
        }
    }

    private fun updateUiData(preset: SettingsPreset) {
        uiState.value = PresetUiState.Loaded(preset)
    }


}

sealed class PresetUiState() {
    object Loading : PresetUiState()
    class Loaded(val settingsPreset: SettingsPreset) : PresetUiState()
}