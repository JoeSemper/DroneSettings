package com.joesemper.dronesettings.ui.settings

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.joesemper.dronesettings.domain.entity.SensorsConstants
import com.joesemper.dronesettings.domain.entity.SignalConstants
import com.joesemper.dronesettings.domain.entity.TimelineConstants

class SettingsViewModel : ViewModel() {

    var uiState by mutableStateOf(SettingsUiState())
        private set

    fun onDelayTimeMinutesChange(newValue: String) {
        uiState.timelineState.delayTimeMinutes.value = newValue
    }

    fun onDelayTimeSecondsChange(newValue: String) {
        uiState.timelineState.delayTimeSeconds.value = newValue
    }

    fun onCockingTimeMinutesChange(newValue: String) {
        uiState.timelineState.cockingTimeMinutes.value = newValue
    }

    fun onCockingTimeSecondsChange(newValue: String) {
        uiState.timelineState.cockingTimeSeconds.value = newValue
    }

    fun onActivateCockingTimeChange(newValue: Boolean) {
        uiState.timelineState.isCockingTimeActivated.value = newValue
    }

    fun onSelfDestructionTimeMinutesChange(newValue: String) {
        uiState.timelineState.selfDestructionTimeMinutes.value = newValue
    }

    fun onSelfDestructionTimeSecondsChange(newValue: String) {
        uiState.timelineState.selfDestructionTimeSeconds.value = newValue
    }

    fun onTargetDistanceChange(newValue: Float) {
        uiState.sensorsState.targetDistance.value = newValue
    }

    fun onMinVoltageChange(newValue: String) {
        uiState.sensorsState.minVoltage.value = newValue
    }

    fun onOverloadActivationChange(newValue: Boolean) {
        uiState.sensorsState.isOverloadActivationEnabled.value = newValue
    }

    fun onDeadTimeActivationChange(newValue: Boolean) {
        uiState.sensorsState.isDeadTimeActivationEnabled.value = newValue
    }

    fun onAverageAccelerationChange(newValue: String) {
        uiState.sensorsState.averageAcceleration.value = newValue
    }

    fun onAverageDeviationChange(newValue: String) {
        uiState.sensorsState.averageDeviation.value = newValue
    }

    fun onDeviationCoefficientChange(newValue: String) {
        uiState.sensorsState.deviationCoefficient.value = newValue
    }

    fun onDeadTimeChange(newValue: String) {
        uiState.sensorsState.deadTime.value = newValue
    }

    fun onCockingPulseWidthHiChange(newValue: String) {
        uiState.signalState.cockingPulseWidthHi.value = newValue
    }

    fun onCockingPulseWidthLoChange(newValue: String) {
        uiState.signalState.cockingPulseWidthLo.value = newValue
    }

    fun onCockingPulseAmountChange(newValue: String) {
        uiState.signalState.cockingPulseAmount.value = newValue
    }

    fun onInfiniteCockingPulseRepeatChange(newValue: Boolean) {
        uiState.signalState.infiniteCockingPulseRepeat.value = newValue
    }

    fun onActivationPulseWidthHiChange(newValue: String) {
        uiState.signalState.activationPulseWidthHi.value = newValue
    }

    fun onActivationPulseWidthLoChange(newValue: String) {
        uiState.signalState.activationPulseWidthLo.value = newValue
    }

    fun onActivationPulseAmountChange(newValue: String) {
        uiState.signalState.cockingPulseAmount.value = newValue
    }

    fun onInfiniteActivationPulseRepeatChange(newValue: Boolean) {
        uiState.signalState.infiniteActivationPulseRepeat.value = newValue
    }

}

data class SettingsUiState(
    val timelineState: TimelineState = TimelineState(),
    val sensorsState: SensorsState = SensorsState(),
    val signalState: SignalSettingsState = SignalSettingsState()
)

data class TimelineState(
    val constants: TimelineConstants = TimelineConstants(),
    val delayTimeMinutes: MutableState<String> = mutableStateOf(""),
    val delayTimeSeconds: MutableState<String> = mutableStateOf(""),
    val cockingTimeMinutes: MutableState<String> = mutableStateOf(""),
    val cockingTimeSeconds: MutableState<String> = mutableStateOf(""),
    val isCockingTimeActivated: MutableState<Boolean> = mutableStateOf(true),
    val selfDestructionTimeMinutes: MutableState<String> = mutableStateOf(""),
    val selfDestructionTimeSeconds: MutableState<String> = mutableStateOf(""),
)

data class SensorsState(
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

data class SignalSettingsState(
    val constants: SignalConstants = SignalConstants(),
    val cockingPulseWidthHi: MutableState<String> = mutableStateOf(""),
    val cockingPulseWidthLo: MutableState<String> = mutableStateOf(""),
    val cockingPulseAmount: MutableState<String> = mutableStateOf("0"),
    val infiniteCockingPulseRepeat: MutableState<Boolean> = mutableStateOf(true),
    val activationPulseWidthHi: MutableState<String> = mutableStateOf(""),
    val activationPulseWidthLo: MutableState<String> = mutableStateOf(""),
    val infiniteActivationPulseRepeat: MutableState<Boolean> = mutableStateOf(true),
    val activationPulseAmount: MutableState<String> = mutableStateOf("0"),
)
