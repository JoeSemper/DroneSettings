package com.joesemper.dronesettings.ui.settings.signal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joesemper.dronesettings.data.datasource.room.entity.SignalPreset
import com.joesemper.dronesettings.domain.use_case.DeletePresetUseCase
import com.joesemper.dronesettings.domain.use_case.GetOrCreateSignalPresetUseCase
import com.joesemper.dronesettings.domain.use_case.UpdatePresetUseCase
import com.joesemper.dronesettings.ui.PRESET_DATA_ID_ARG
import com.joesemper.dronesettings.ui.settings.SettingsUiAction
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SignalViewModel(
    savedStateHandle: SavedStateHandle,
    private val getOrCreateSignalPreset: GetOrCreateSignalPresetUseCase,
    private val deletePreset: DeletePresetUseCase,
    private val updatePreset: UpdatePresetUseCase
) : ViewModel() {
    var uiState by mutableStateOf(SignalUiState())
        private set

    private val actions = Channel<SettingsUiAction>()
    val uiActions = actions.receiveAsFlow()

    private val dataId: Int = checkNotNull(savedStateHandle[PRESET_DATA_ID_ARG])
    private var currentPreset: SignalPreset? = null

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            getOrCreateSignalPreset(dataId).collect { preset ->
                updateUiStateData(preset)
            }
        }
    }

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

            SignalUiEvent.BackButtonClick -> {
                savePresetData()
                viewModelScope.launch {
                    actions.send(SettingsUiAction.NavigateBack)
                }
            }

            SignalUiEvent.NextButtonClick -> {
                savePresetData()
                viewModelScope.launch {
                    actions.send(SettingsUiAction.NavigateNext(dataId))
                }
            }

            SignalUiEvent.CloseClick -> {
                viewModelScope.launch {
                    deletePreset(dataId)
                    actions.send(SettingsUiAction.Close)
                }
            }
        }
    }

    private fun updateUiStateData(newPreset: SignalPreset) {
        currentPreset = newPreset
        currentPreset?.let { preset ->
            uiState = uiState.copy(
                isLoaded = true,
                cockingPulseWidthHi = preset.cockingPulseWidthHi,
                cockingPulseWidthLo = preset.cockingPulseWidthLo,
                cockingPulseAmount = preset.cockingPulseAmount,
                infiniteCockingPulseRepeat = preset.cockingPulseInfiniteEnabled,
                activationPulseWidthHi = preset.activationPulseWidthHi,
                activationPulseWidthLo = preset.activationPulseWidthLo,
                activationPulseAmount = preset.activationPulseAmount,
                infiniteActivationPulseRepeat = preset.activationPulseInfiniteEnabled,
            )
        }
    }

    private fun savePresetData() {
        viewModelScope.launch {
            currentPreset?.let { preset ->
                updatePreset(
                    preset.copy(
                        cockingPulseWidthHi = uiState.cockingPulseWidthHi,
                        cockingPulseWidthLo = uiState.cockingPulseWidthLo,
                        cockingPulseAmount = uiState.cockingPulseAmount,
                        cockingPulseInfiniteEnabled = uiState.infiniteCockingPulseRepeat,
                        activationPulseWidthHi = uiState.activationPulseWidthHi,
                        activationPulseWidthLo = uiState.activationPulseWidthLo,
                        activationPulseAmount = uiState.activationPulseAmount,
                        activationPulseInfiniteEnabled = uiState.infiniteActivationPulseRepeat,
                    )
                )
            }
        }
    }
}