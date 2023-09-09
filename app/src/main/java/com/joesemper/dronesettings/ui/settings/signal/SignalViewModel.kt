package com.joesemper.dronesettings.ui.settings.signal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SignalViewModel(): ViewModel() {
    var uiState by mutableStateOf(SignalUiState())
        private set

    fun onSignalUiEvent(event: SignalUiEvent) {
        when (event) {
            is SignalUiEvent.ActivationPulseAmountChange -> {
                uiState = uiState.copy(activationPulseAmount = event.amount)
            }

            is SignalUiEvent.ActivationPulseWidthHiChange -> {
                uiState = uiState.copy(activationPulseWidthHi = event.width)
            }

            is SignalUiEvent.ActivationPulseWidthLoChange -> {
                uiState = uiState.copy(activationPulseWidthLo = event.width)
            }

            is SignalUiEvent.CockingPulseAmountChange -> {
                uiState = uiState.copy(cockingPulseAmount = event.amount)
            }

            is SignalUiEvent.CockingPulseWidthHiChange -> {
                uiState = uiState.copy(cockingPulseWidthHi = event.width)
            }

            is SignalUiEvent.CockingPulseWidthLoChange -> {
                uiState = uiState.copy(cockingPulseWidthLo = event.width)
            }

            is SignalUiEvent.InfiniteActivationPulseRepeatChange -> {
                uiState = uiState.copy(infiniteActivationPulseRepeat = event.enabled)
            }

            is SignalUiEvent.InfiniteCockingPulseRepeatChange -> {
                uiState = uiState.copy(infiniteCockingPulseRepeat = event.enabled)
            }
        }
    }
}