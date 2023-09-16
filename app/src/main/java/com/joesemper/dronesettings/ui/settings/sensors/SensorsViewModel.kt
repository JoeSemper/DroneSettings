package com.joesemper.dronesettings.ui.settings.sensors

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joesemper.dronesettings.data.datasource.room.entity.SensorsPreset
import com.joesemper.dronesettings.domain.use_case.DeleteSettingsSetUseCase
import com.joesemper.dronesettings.domain.use_case.GetOrCreateSensorsPresetUseCase
import com.joesemper.dronesettings.domain.use_case.UpdatePresetUseCase
import com.joesemper.dronesettings.ui.SETTINGS_SET_ID_ARG
import com.joesemper.dronesettings.ui.settings.SettingsUiAction
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SensorsViewModel(
    savedStateHandle: SavedStateHandle,
    private val getOrCreateSensorsPreset: GetOrCreateSensorsPresetUseCase,
    private val deleteSettingsSet: DeleteSettingsSetUseCase,
    private val updatePreset: UpdatePresetUseCase
) : ViewModel() {

    var uiState by mutableStateOf(SensorsUiState())
        private set

    private val actions = Channel<SettingsUiAction>()
    val uiActions = actions.receiveAsFlow()

    private val settingsSetId: Int = checkNotNull(savedStateHandle[SETTINGS_SET_ID_ARG])
    private var currentPreset: SensorsPreset? = null

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            getOrCreateSensorsPreset(settingsSetId).collect { preset ->
                updateUiStateData(preset)
            }
        }
    }

    fun onSensorsUiEvent(event: SensorsUiEvent) {
        when (event) {
            is SensorsUiEvent.TargetDistanceChange -> {
                uiState = uiState.copy(targetDistance = event.distance)
            }

            is SensorsUiEvent.MinVoltageChange -> {
                uiState = uiState.copy(minVoltage = event.voltage)
            }

            is SensorsUiEvent.BatteryActivationChange -> {
                uiState = uiState.copy(isBatteryActivationEnabled = event.isEnabled)
            }

            is SensorsUiEvent.OverloadActivationChange -> {
                uiState = uiState.copy(isOverloadActivationEnabled = event.isEnabled)
            }

            is SensorsUiEvent.DeadTimeActivationChange -> {
                uiState = uiState.copy(isDeadTimeActivationEnabled = event.isEnabled)
            }

            is SensorsUiEvent.AverageAccelerationChange -> {
                uiState = uiState.copy(averageAcceleration = event.acceleration)
            }

            is SensorsUiEvent.AverageDeviationChange -> {
                uiState = uiState.copy(averageDeviation = event.deviation)
            }


            is SensorsUiEvent.DeadTimeChange -> {
                uiState = uiState.copy(deadTime = event.deadTime)
            }

            is SensorsUiEvent.DeviationCoefficientChange -> {
                uiState = uiState.copy(deviationCoefficient = event.coefficient)
            }

            SensorsUiEvent.BackButtonClick -> {
                savePresetData()
                viewModelScope.launch {
                    actions.send(SettingsUiAction.NavigateBack)
                }
            }

            SensorsUiEvent.NextButtonClick -> {
                savePresetData()
                viewModelScope.launch {
                    actions.send(SettingsUiAction.NavigateNext(settingsSetId))
                }
            }

            SensorsUiEvent.CloseClick -> {
                viewModelScope.launch {
                    deleteSettingsSet(settingsSetId)
                    actions.send(SettingsUiAction.Close)
                }
            }
        }
    }

    private fun updateUiStateData(newPreset: SensorsPreset) {
        currentPreset = newPreset
        currentPreset?.let { preset ->
            uiState = uiState.copy(
                isLoaded = true,
                targetDistance = preset.targetDistance,
                minVoltage = preset.minVoltage,
                isOverloadActivationEnabled = preset.overloadActivationEnabled,
                isDeadTimeActivationEnabled = preset.deadTimeActivationEnabled,
                averageAcceleration = preset.averageAcceleration,
                averageDeviation = preset.averageDeviation,
                deviationCoefficient = preset.deviationCoefficient,
                deadTime = preset.deadTime
            )
        }
    }

    private fun savePresetData() {
        viewModelScope.launch {
            currentPreset?.let { preset ->
                updatePreset(
                    preset.copy(
                        targetDistance = uiState.targetDistance,
                        minVoltage = uiState.minVoltage,
                        averageAcceleration = uiState.averageAcceleration,
                        averageDeviation = uiState.averageDeviation,
                        deviationCoefficient = uiState.deviationCoefficient,
                        deadTime = uiState.deadTime,
                        overloadActivationEnabled = uiState.isOverloadActivationEnabled,
                        deadTimeActivationEnabled = uiState.isDeadTimeActivationEnabled
                    )
                )
            }
        }
    }
}