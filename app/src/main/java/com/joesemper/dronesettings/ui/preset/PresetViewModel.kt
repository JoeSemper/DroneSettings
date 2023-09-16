package com.joesemper.dronesettings.ui.preset

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joesemper.dronesettings.data.datasource.room.entity.SettingsPreset
import com.joesemper.dronesettings.domain.use_case.GetSettingsPresetUseCase
import com.joesemper.dronesettings.domain.use_case.UpdateSettingsSetUseCase
import com.joesemper.dronesettings.ui.SETTINGS_SET_ID_ARG
import kotlinx.coroutines.launch

class PresetViewModel(
    savedStateHandle: SavedStateHandle,
    private val getSettingsPreset: GetSettingsPresetUseCase,
    private val updateSettingsSet: UpdateSettingsSetUseCase
) : ViewModel() {

    val uiState = mutableStateOf(PresetUiState())

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

    fun onSavePreset(
        newName: String,
        newDescription: String
    ) {
        viewModelScope.launch {
            updateSettingsSet(
                uiState.value.settingsPreset.settingsSet.copy(
                    name = newName,
                    description = newDescription,
                    saved = true
                )
            )
        }
    }

    private fun updateUiData(preset: SettingsPreset) {
        uiState.value = uiState.value.copy(
            isLoading = false,
            settingsPreset = preset
        )
    }

}

data class PresetUiState(
    val isLoading: Boolean = true,
    val settingsPreset: SettingsPreset = SettingsPreset(),
    )

