package com.joesemper.dronesettings.ui.settings.timeline

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class TimelineViewModel(): ViewModel() {

    var uiState by mutableStateOf(TimelineUiState())
        private set

    fun onTimelineUiEvent(event: TimelineUiEvent) {
        when (event) {
            is TimelineUiEvent.DelayTimeMinutesChange -> {
                uiState = uiState.copy(delayTimeMinutes = event.minutes)

            }

            is TimelineUiEvent.DelayTimeSecondsChange -> {
                uiState = uiState.copy(delayTimeSeconds = event.seconds)

            }

            is TimelineUiEvent.CockingTimeActivationChange -> {
                uiState = uiState.copy(isCockingTimeActivated = event.isActivated)
            }

            is TimelineUiEvent.CockingTimeMinutesChange -> {
                uiState = uiState.copy(cockingTimeMinutes = event.minutes)
            }

            is TimelineUiEvent.CockingTimeSecondsChange -> {
                uiState = uiState.copy(cockingTimeSeconds = event.seconds)
            }

            is TimelineUiEvent.SelfDestructionTimeMinutesChange -> {
                uiState = uiState.copy(selfDestructionTimeMinutes = event.minutes)
            }

            is TimelineUiEvent.SelfDestructionTimeSecondsChange -> {
                uiState = uiState.copy(selfDestructionTimeSeconds = event.seconds)
            }
        }
    }
}