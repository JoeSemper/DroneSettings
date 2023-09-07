package com.joesemper.dronesettings.ui.settings.sensors

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SensorsViewModel(): ViewModel() {

    var uiState by mutableStateOf(SensorsUiState())
        private set

    fun onSensorsUiEvent(event: SensorsUiEvent) {
        when (event) {
            is SensorsUiEvent.TargetDistanceChange -> {
                uiState = uiState.copy(targetDistance = event.distance)
            }

            is SensorsUiEvent.MinVoltageChange -> {
                uiState = uiState.copy(minVoltage = event.voltage)
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
        }
    }
}