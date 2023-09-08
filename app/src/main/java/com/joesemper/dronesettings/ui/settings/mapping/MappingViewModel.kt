package com.joesemper.dronesettings.ui.settings.mapping

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MappingViewModel(): ViewModel() {

    var uiState by mutableStateOf(MappingUiState())
        private set

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
        }
    }
}