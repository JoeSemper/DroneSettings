package com.joesemper.dronesettings.ui.settings.mapping

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joesemper.dronesettings.data.datasource.room.entity.MappingPreset
import com.joesemper.dronesettings.domain.use_case.DeletePresetUseCase
import com.joesemper.dronesettings.domain.use_case.GetOrCreateMappingPresetUseCase
import com.joesemper.dronesettings.domain.use_case.UpdatePresetUseCase
import com.joesemper.dronesettings.ui.PRESET_DATA_ID_ARG
import com.joesemper.dronesettings.ui.settings.entity.SettingsUiAction
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MappingViewModel(
    savedStateHandle: SavedStateHandle,
    private val getOrCreateMappingPresetUseCase: GetOrCreateMappingPresetUseCase,
    private val deletePreset: DeletePresetUseCase,
    private val updatePreset: UpdatePresetUseCase
) : ViewModel() {

    var uiState by mutableStateOf(MappingUiState())
        private set

    private val actions = Channel<SettingsUiAction>()
    val uiActions = actions.receiveAsFlow()

    private val dataId: Int = checkNotNull(savedStateHandle[PRESET_DATA_ID_ARG])
    private var currentPreset: MappingPreset? = null

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            getOrCreateMappingPresetUseCase(dataId).collect { preset ->
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
                    actions.send(SettingsUiAction.NavigateBack)
                }
            }

            MappingUiEvent.NextButtonClick -> {
                savePresetData()
                viewModelScope.launch {
                    actions.send(SettingsUiAction.NavigateNext(dataId))
                }
            }

            MappingUiEvent.CloseClick -> {
                viewModelScope.launch {
                    deletePreset(dataId)
                    actions.send(SettingsUiAction.Close)
                }
            }
        }
    }

    private fun updateUiStateData(newPreset: MappingPreset) {
        currentPreset = newPreset
        currentPreset?.let { preset ->
            uiState = uiState.copy(
                isLoaded = true,
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