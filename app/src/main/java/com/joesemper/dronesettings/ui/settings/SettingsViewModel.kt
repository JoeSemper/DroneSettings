package com.joesemper.dronesettings.ui.settings

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.joesemper.dronesettings.domain.entity.SensorsConstants
import com.joesemper.dronesettings.domain.entity.TimelineConstants

class SettingsViewModel : ViewModel() {

    var uiState by mutableStateOf(SettingsUiState())
        private set

    fun onDelayTimeMinutesChange(newValue: String) {
        uiState.timelineScreenState.delayTimeMinutes.value = newValue
    }

    fun onDelayTimeSecondsChange(newValue: String) {
        uiState.timelineScreenState.delayTimeSeconds.value = newValue
    }

    fun onCockingTimeMinutesChange(newValue: String) {
        uiState.timelineScreenState.cockingTimeMinutes.value = newValue
    }

    fun onCockingTimeSecondsChange(newValue: String) {
        uiState.timelineScreenState.cockingTimeSeconds.value = newValue
    }

    fun onActivateCockingTimeChange(newValue: Boolean) {
        uiState.timelineScreenState.isCockingTimeActivated.value = newValue
    }

    fun onSelfDestructionTimeMinutesChange(newValue: String) {
        uiState.timelineScreenState.selfDestructionTimeMinutes.value = newValue
    }

    fun onSelfDestructionTimeSecondsChange(newValue: String) {
        uiState.timelineScreenState.selfDestructionTimeSeconds.value = newValue
    }

    fun onTargetDistanceChange(newValue: Float) {
        uiState.sensorsScreenState.targetDistance.value = newValue
    }
    fun onMinVoltageChange(newValue: String) {
        uiState.sensorsScreenState.minVoltage.value = newValue
    }

    fun onOverloadActivationChange(newValue: Boolean) {
        uiState.sensorsScreenState.isOverloadActivationEnabled.value = newValue
    }

    fun onDeadTimeActivationChange(newValue: Boolean) {
        uiState.sensorsScreenState.isDeadTimeActivationEnabled.value = newValue
    }

    fun onAverageAccelerationChange(newValue: String) {
        uiState.sensorsScreenState.averageAcceleration.value = newValue
    }
    fun onAverageDeviationChange(newValue: String) {
        uiState.sensorsScreenState.averageDeviation.value = newValue
    }
    fun onDeviationCoefficientChange(newValue: String) {
        uiState.sensorsScreenState.deviationCoefficient.value = newValue
    }
    fun onDeadTimeChange(newValue: String) {
        uiState.sensorsScreenState.deadTime.value = newValue
    }

}

data class SettingsUiState(
    val timelineScreenState: TimelineScreenState = TimelineScreenState(),
    val sensorsScreenState: SensorsScreenState = SensorsScreenState()
)

data class TimelineScreenState(
    val constants: TimelineConstants = TimelineConstants(),
    val delayTimeMinutes: MutableState<String> = mutableStateOf(""),
    val delayTimeSeconds: MutableState<String> = mutableStateOf(""),
    val cockingTimeMinutes: MutableState<String> = mutableStateOf(""),
    val cockingTimeSeconds: MutableState<String> = mutableStateOf(""),
    val isCockingTimeActivated: MutableState<Boolean> = mutableStateOf(true),
    val selfDestructionTimeMinutes: MutableState<String> = mutableStateOf(""),
    val selfDestructionTimeSeconds: MutableState<String> = mutableStateOf(""),
)

data class SensorsScreenState(
    val constants: SensorsConstants = SensorsConstants(),
    val targetDistance: MutableState<Float> = mutableStateOf(0.5f),
    val minVoltage: MutableState<String> = mutableStateOf(""),
    val isOverloadActivationEnabled: MutableState<Boolean> = mutableStateOf(true),
    val isDeadTimeActivationEnabled: MutableState<Boolean> = mutableStateOf(true),
    val averageAcceleration: MutableState<String> = mutableStateOf(""),
    val averageDeviation: MutableState<String> = mutableStateOf(""),
    val deviationCoefficient: MutableState<String> = mutableStateOf(""),
    val deadTime: MutableState<String> = mutableStateOf("")
)
