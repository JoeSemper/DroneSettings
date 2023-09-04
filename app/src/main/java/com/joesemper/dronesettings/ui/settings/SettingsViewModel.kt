package com.joesemper.dronesettings.ui.settings

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.joesemper.dronesettings.domain.entity.SignalConstants
import com.joesemper.dronesettings.ui.settings.sensors.SensorsUiEvent
import com.joesemper.dronesettings.ui.settings.sensors.SensorsUiState
import com.joesemper.dronesettings.ui.settings.timeline.TimelineUiEvent
import com.joesemper.dronesettings.ui.settings.timeline.TimelineUiState

class SettingsViewModel : ViewModel() {

    var uiState by mutableStateOf(SettingsUiState())
        private set

    fun onTimelineUiEvent(event: TimelineUiEvent) {
        when (event) {
            is TimelineUiEvent.DelayTimeMinutesChange -> {
                uiState = uiState.copy(
                    timelineState = uiState.timelineState.copy(delayTimeMinutes = event.minutes)
                )
            }

            is TimelineUiEvent.DelayTimeSecondsChange -> {
                uiState = uiState.copy(
                    timelineState = uiState.timelineState.copy(delayTimeSeconds = event.seconds)
                )

            }

            is TimelineUiEvent.CockingTimeActivationChange -> {
                uiState = uiState.copy(
                    timelineState = uiState.timelineState.copy(isCockingTimeActivated = event.isActivated)
                )
            }

            is TimelineUiEvent.CockingTimeMinutesChange -> {
                uiState = uiState.copy(
                    timelineState = uiState.timelineState.copy(cockingTimeMinutes = event.minutes)
                )
            }

            is TimelineUiEvent.CockingTimeSecondsChange -> {
                uiState = uiState.copy(
                    timelineState = uiState.timelineState.copy(cockingTimeSeconds = event.seconds)
                )
            }

            is TimelineUiEvent.SelfDestructionTimeMinutesChange -> {
                uiState = uiState.copy(
                    timelineState = uiState.timelineState.copy(selfDestructionTimeMinutes = event.minutes)
                )
            }

            is TimelineUiEvent.SelfDestructionTimeSecondsChange -> {
                uiState = uiState.copy(
                    timelineState = uiState.timelineState.copy(selfDestructionTimeSeconds = event.seconds)
                )
            }
        }
    }

    fun onSensorsUiEvent(event: SensorsUiEvent) {
        when (event) {
            is SensorsUiEvent.TargetDistanceChange -> {
                uiState = uiState.copy(
                    sensorsState = uiState.sensorsState.copy(targetDistance = event.distance)
                )
            }

            is SensorsUiEvent.MinVoltageChange -> {
                uiState = uiState.copy(
                    sensorsState = uiState.sensorsState.copy(minVoltage = event.voltage)
                )
            }

            is SensorsUiEvent.OverloadActivationChange -> {
                uiState = uiState.copy(
                    sensorsState = uiState.sensorsState.copy(isOverloadActivationEnabled = event.isEnabled)
                )
            }

            is SensorsUiEvent.DeadTimeActivationChange -> {
                uiState = uiState.copy(
                    sensorsState = uiState.sensorsState.copy(isDeadTimeActivationEnabled = event.isEnabled)
                )
            }

            is SensorsUiEvent.AverageAccelerationChange -> {
                uiState = uiState.copy(
                    sensorsState = uiState.sensorsState.copy(averageAcceleration = event.acceleration)
                )
            }

            is SensorsUiEvent.AverageDeviationChange -> {
                uiState = uiState.copy(
                    sensorsState = uiState.sensorsState.copy(averageDeviation = event.deviation)
                )
            }


            is SensorsUiEvent.DeadTimeChange -> {
                uiState = uiState.copy(
                    sensorsState = uiState.sensorsState.copy(deadTime = event.deadTime)
                )
            }

            is SensorsUiEvent.DeviationCoefficientChange -> {
                uiState = uiState.copy(
                    sensorsState = uiState.sensorsState.copy(deviationCoefficient = event.coefficient)
                )
            }
        }
    }


//    fun onTargetDistanceChange(newValue: Float) {
//        uiState.sensorsState.targetDistance.value = newValue
//    }
//
//    fun onMinVoltageChange(newValue: String) {
//        uiState.sensorsState.minVoltage.value = newValue
//    }
//
//    fun onOverloadActivationChange(newValue: Boolean) {
//        uiState.sensorsState.isOverloadActivationEnabled.value = newValue
//    }
//
//    fun onDeadTimeActivationChange(newValue: Boolean) {
//        uiState.sensorsState.isDeadTimeActivationEnabled.value = newValue
//    }
//
//    fun onAverageAccelerationChange(newValue: String) {
//        uiState.sensorsState.averageAcceleration.value = newValue
//    }
//
//    fun onAverageDeviationChange(newValue: String) {
//        uiState.sensorsState.averageDeviation.value = newValue
//    }
//
//    fun onDeviationCoefficientChange(newValue: String) {
//        uiState.sensorsState.deviationCoefficient.value = newValue
//    }
//
//    fun onDeadTimeChange(newValue: String) {
//        uiState.sensorsState.deadTime.value = newValue
//    }

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
    val timelineState: TimelineUiState = TimelineUiState(),
    val sensorsState: SensorsUiState = SensorsUiState(),
    val signalState: SignalSettingsState = SignalSettingsState()
)

//data class SensorsState(
//    val constants: SensorsConstants = SensorsConstants(),
//    val targetDistance: MutableState<Float> = mutableStateOf(0.5f),
//    val minVoltage: MutableState<String> = mutableStateOf(""),
//    val isOverloadActivationEnabled: MutableState<Boolean> = mutableStateOf(true),
//    val isDeadTimeActivationEnabled: MutableState<Boolean> = mutableStateOf(true),
//    val averageAcceleration: MutableState<String> = mutableStateOf(""),
//    val averageDeviation: MutableState<String> = mutableStateOf(""),
//    val deviationCoefficient: MutableState<String> = mutableStateOf(""),
//    val deadTime: MutableState<String> = mutableStateOf("")
//)

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
