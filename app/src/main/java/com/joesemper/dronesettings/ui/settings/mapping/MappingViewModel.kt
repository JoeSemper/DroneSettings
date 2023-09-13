package com.joesemper.dronesettings.ui.settings.mapping

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joesemper.dronesettings.data.datasource.room.entity.MappingPreset
import com.joesemper.dronesettings.domain.use_case.DeleteSettingsSetUseCase
import com.joesemper.dronesettings.domain.use_case.GetOrCreateMappingPresetUseCase
import com.joesemper.dronesettings.domain.use_case.UpdatePresetUseCase
import com.joesemper.dronesettings.ui.SETTINGS_SET_ID_ARG
import com.joesemper.dronesettings.ui.settings.PresetUiAction
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MappingViewModel(
    savedStateHandle: SavedStateHandle,
    private val getOrCreateMappingPresetUseCase: GetOrCreateMappingPresetUseCase,
    private val deleteSettingsSet: DeleteSettingsSetUseCase,
    private val updatePreset: UpdatePresetUseCase
) : ViewModel() {

    var uiState by mutableStateOf(MappingUiState())
        private set

    private val actions = Channel<PresetUiAction>()
    val uiActions = actions.receiveAsFlow()

    private val settingsSetId: Int = checkNotNull(savedStateHandle[SETTINGS_SET_ID_ARG])
    private var currentPreset: MappingPreset? = null

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            getOrCreateMappingPresetUseCase(settingsSetId).collect { preset ->
                updateUiStateData(preset)
            }
        }
    }

    fun onMappingUiEvent(event: MappingUiEvent) {
        when (event) {
            is MappingUiEvent.PulseOneStateChange -> {
                uiState = uiState.copy(pulseOneState = event.selected)
            }

            is MappingUiEvent.PulseThreeStateChange -> {
                uiState = uiState.copy(pulseThreeState = event.selected)
            }

            is MappingUiEvent.PulseTwoStateChange -> {
                uiState = uiState.copy(pulseTwoState = event.selected)
            }

            is MappingUiEvent.RelayOneStateChange -> {
                uiState = uiState.copy(relayOneState = event.selected)
            }

            is MappingUiEvent.RelayTwoStateChange -> {
                uiState = uiState.copy(relayTwoState = event.selected)
            }

            MappingUiEvent.BackButtonClick -> {
                savePresetData()
                viewModelScope.launch {
                    actions.send(PresetUiAction.NavigateBack)
                }
            }

            MappingUiEvent.NextButtonClick -> {
                savePresetData()
                viewModelScope.launch {
                    actions.send(PresetUiAction.NavigateNext(settingsSetId))
                }
            }

            MappingUiEvent.CloseClick -> {
                viewModelScope.launch {
                    deleteSettingsSet(settingsSetId)
                    actions.send(PresetUiAction.Close)
                }
            }
        }
    }

    private fun updateUiStateData(newPreset: MappingPreset) {
        currentPreset = newPreset
        currentPreset?.let { preset ->
            uiState = uiState.copy(
                relayOneState = preset.relayOneState.asMappingSelectOption(),
                relayTwoState = preset.relayTwoState.asMappingSelectOption(),
                pulseOneState = preset.pulseOneState.asMappingSelectOption(),
                pulseTwoState = preset.pulseTwoState.asMappingSelectOption(),
                pulseThreeState = preset.pulseThreeState.asMappingSelectOption()
            )
        }
    }

    private fun savePresetData() {
        viewModelScope.launch {
            currentPreset?.let { preset ->
                updatePreset(
                    preset.copy(
                        relayOneState = uiState.relayOneState.toInt(),
                        relayTwoState = uiState.relayTwoState.toInt(),
                        pulseOneState = uiState.pulseOneState.toInt(),
                        pulseTwoState = uiState.pulseTwoState.toInt(),
                        pulseThreeState = uiState.pulseThreeState.toInt()
                    )
                )
            }
        }
    }

}