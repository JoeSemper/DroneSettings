package com.joesemper.dronesettings.ui.settings.timeline

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joesemper.dronesettings.data.datasource.room.entity.TimelinePreset
import com.joesemper.dronesettings.domain.use_case.GetOrCreateTimelinePresetUseCase
import kotlinx.coroutines.launch

class TimelineViewModel(
    savedStateHandle: SavedStateHandle,
    val getOrCreateTimelinePresetUseCase: GetOrCreateTimelinePresetUseCase
) : ViewModel() {

    var uiState by mutableStateOf(TimelineUiState())
        private set

    private val settingsPresetId: Int = checkNotNull(savedStateHandle["settingsPresetId"])

    init {
        subscribeOnPreset()
    }

    private fun subscribeOnPreset() {
        viewModelScope.launch {
            getOrCreateTimelinePresetUseCase(settingsPresetId).collect { preset ->
                updateUiStateData(preset)
            }
        }
    }

    private fun updateUiStateData(preset: TimelinePreset) {
        uiState = uiState.copy(
            delayTimeMinutes = (preset.delayTimeSec / 60).toString(),
            delayTimeSeconds = (preset.delayTimeSec % 60).toString(),
            cockingTimeMinutes = (preset.cockingTimeSec / 60).toString(),
            cockingTimeSeconds = (preset.cockingTimeSec % 60).toString(),
            isCockingTimeEnabled = preset.cockingTimeEnabled,
            selfDestructionTimeMinutes = (preset.selfDestructionTimeSec / 60).toString(),
            selfDestructionTimeSeconds = (preset.selfDestructionTimeSec % 60).toString()
        )
    }

    fun onTimelineUiEvent(event: TimelineUiEvent) {
        when (event) {
            is TimelineUiEvent.DelayTimeMinutesChange -> {
                uiState = uiState.copy(delayTimeMinutes = event.minutes)

            }

            is TimelineUiEvent.DelayTimeSecondsChange -> {
                uiState = uiState.copy(delayTimeSeconds = event.seconds)

            }

            is TimelineUiEvent.CockingTimeActivationChange -> {
                uiState = uiState.copy(isCockingTimeEnabled = event.isActivated)
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