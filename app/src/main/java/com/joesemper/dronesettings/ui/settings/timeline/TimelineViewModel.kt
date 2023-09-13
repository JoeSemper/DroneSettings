package com.joesemper.dronesettings.ui.settings.timeline

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joesemper.dronesettings.data.datasource.room.entity.TimelinePreset
import com.joesemper.dronesettings.domain.use_case.DeleteSettingsSetUseCase
import com.joesemper.dronesettings.domain.use_case.GetOrCreateTimelinePresetUseCase
import com.joesemper.dronesettings.domain.use_case.UpdatePresetUseCase
import com.joesemper.dronesettings.ui.SETTINGS_SET_ID_ARG
import com.joesemper.dronesettings.ui.settings.PresetUiAction
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TimelineViewModel(
    savedStateHandle: SavedStateHandle,
    private val getOrCreateTimelinePreset: GetOrCreateTimelinePresetUseCase,
    private val deleteSettingsSet: DeleteSettingsSetUseCase,
    private val updatePreset: UpdatePresetUseCase
) : ViewModel() {

    var uiState by mutableStateOf(TimelineUiState())
        private set

    private val actions = Channel<PresetUiAction>()
    val uiActions = actions.receiveAsFlow()

    private val settingsSetId: Int = checkNotNull(savedStateHandle[SETTINGS_SET_ID_ARG])
    private var currentPreset: TimelinePreset? = null

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            getOrCreateTimelinePreset(settingsSetId).collect { preset ->
                updateUiStateData(preset)
            }
        }
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

            TimelineUiEvent.BackButtonClick -> {
                viewModelScope.launch {
                    deleteSettingsSet(settingsSetId)
                    actions.send(PresetUiAction.NavigateBack)
                }
            }

            TimelineUiEvent.NextButtonClick -> {
                savePresetData()
                viewModelScope.launch {
                    actions.send(PresetUiAction.NavigateNext(settingsSetId))
                }
            }

            TimelineUiEvent.CloseClick -> {
                viewModelScope.launch {
                    deleteSettingsSet(settingsSetId)
                    actions.send(PresetUiAction.Close)
                }
            }
        }
    }

    private fun updateUiStateData(newPreset: TimelinePreset) {
        currentPreset = newPreset
        currentPreset?.let { preset ->
            uiState = uiState.copy(
                isLoaded = true,
                delayTimeMinutes = preset.delayTimeMin,
                delayTimeSeconds = preset.delayTimeSec,
                cockingTimeMinutes = preset.cockingTimeMin,
                cockingTimeSeconds = preset.cockingTimeSec,
                isCockingTimeEnabled = preset.cockingTimeEnabled,
                selfDestructionTimeMinutes = preset.selfDestructionTimeMin,
                selfDestructionTimeSeconds = preset.selfDestructionTimeSec
            )
        }
    }

    private fun savePresetData() {
        viewModelScope.launch {
            currentPreset?.let { preset ->
                updatePreset(
                    preset.copy(
                        delayTimeSec = uiState.delayTimeSeconds,
                        delayTimeMin = uiState.delayTimeMinutes,
                        cockingTimeSec = uiState.cockingTimeSeconds,
                        cockingTimeMin = uiState.cockingTimeMinutes,
                        cockingTimeEnabled = uiState.isCockingTimeEnabled,
                        selfDestructionTimeSec = uiState.selfDestructionTimeSeconds,
                        selfDestructionTimeMin = uiState.selfDestructionTimeMinutes,
                    )
                )
            }
        }
    }


}