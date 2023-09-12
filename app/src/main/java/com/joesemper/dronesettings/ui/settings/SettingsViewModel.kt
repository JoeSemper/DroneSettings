package com.joesemper.dronesettings.ui.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.joesemper.dronesettings.ui.settings.mapping.MappingUiEvent
import com.joesemper.dronesettings.ui.settings.mapping.MappingUiState
import com.joesemper.dronesettings.ui.settings.sensors.SensorsUiEvent
import com.joesemper.dronesettings.ui.settings.sensors.SensorsUiState
import com.joesemper.dronesettings.ui.settings.signal.SignalUiEvent
import com.joesemper.dronesettings.ui.settings.signal.SignalUiState
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
                    timelineState = uiState.timelineState.copy(isCockingTimeEnabled = event.isActivated)
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

            TimelineUiEvent.BackButtonClick -> TODO()
            TimelineUiEvent.CloseClick -> TODO()
            TimelineUiEvent.NextButtonClick -> TODO()
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

    fun onMappingUiEvent(event: MappingUiEvent) {
        when (event) {
            is MappingUiEvent.PulseOneStateChange -> {
                uiState = uiState.copy(
                    mappingUiState = uiState.mappingUiState.copy(pulseOneState = event.selected)
                )
            }
            is MappingUiEvent.PulseThreeStateChange -> {
                uiState = uiState.copy(
                    mappingUiState = uiState.mappingUiState.copy(pulseThreeState = event.selected)
                )
            }
            is MappingUiEvent.PulseTwoStateChange -> {
                uiState = uiState.copy(
                    mappingUiState = uiState.mappingUiState.copy(pulseTwoState = event.selected)
                )
            }
            is MappingUiEvent.RelayOneStateChange -> {
                uiState = uiState.copy(
                    mappingUiState = uiState.mappingUiState.copy(relayOneState = event.selected)
                )
            }
            is MappingUiEvent.RelayTwoStateChange -> {
                uiState = uiState.copy(
                    mappingUiState = uiState.mappingUiState.copy(relayTwoState = event.selected)
                )
            }
        }
    }

    fun onSignalUiEvent(event: SignalUiEvent) {
        when (event) {
            is SignalUiEvent.ActivationPulseAmountChange -> {
                uiState = uiState.copy(
                    signalState = uiState.signalState.copy(activationPulseAmount = event.amount)
                )
            }

            is SignalUiEvent.ActivationPulseWidthHiChange -> {
                uiState = uiState.copy(
                    signalState = uiState.signalState.copy(activationPulseWidthHi = event.width)
                )
            }

            is SignalUiEvent.ActivationPulseWidthLoChange -> {
                uiState = uiState.copy(
                    signalState = uiState.signalState.copy(activationPulseWidthLo = event.width)
                )
            }

            is SignalUiEvent.CockingPulseAmountChange -> {
                uiState = uiState.copy(
                    signalState = uiState.signalState.copy(cockingPulseAmount = event.amount)
                )
            }

            is SignalUiEvent.CockingPulseWidthHiChange -> {
                uiState = uiState.copy(
                    signalState = uiState.signalState.copy(cockingPulseWidthHi = event.width)
                )
            }

            is SignalUiEvent.CockingPulseWidthLoChange -> {
                uiState = uiState.copy(
                    signalState = uiState.signalState.copy(activationPulseWidthLo = event.width)
                )
            }

            is SignalUiEvent.InfiniteActivationPulseRepeatChange -> {
                uiState = uiState.copy(
                    signalState = uiState.signalState.copy(infiniteActivationPulseRepeat = event.enabled)
                )
            }

            is SignalUiEvent.InfiniteCockingPulseRepeatChange -> {
                uiState = uiState.copy(
                    signalState = uiState.signalState.copy(infiniteCockingPulseRepeat = event.enabled)
                )
            }
        }
    }

}

data class SettingsUiState(
    val timelineState: TimelineUiState = TimelineUiState(),
    val sensorsState: SensorsUiState = SensorsUiState(),
    val mappingUiState: MappingUiState = MappingUiState(),
    val signalState: SignalUiState = SignalUiState()
)