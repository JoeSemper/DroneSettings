package com.joesemper.dronesettings.ui.settings.screens.timeline

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joesemper.dronesettings.data.datasource.room.entity.TimelinePreset
import com.joesemper.dronesettings.domain.use_case.DeletePresetUseCase
import com.joesemper.dronesettings.domain.use_case.GetOrCreateTimelinePresetUseCase
import com.joesemper.dronesettings.domain.use_case.UpdatePresetUseCase
import com.joesemper.dronesettings.domain.use_case.validation.ValidateTimeInputUseCase
import com.joesemper.dronesettings.navigation.home.HomeDestinations.HOME_PRESET_ID_KEY
import com.joesemper.dronesettings.ui.settings.state.SettingsUiAction
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TimelineViewModel(
    savedStateHandle: SavedStateHandle,
    private val getOrCreateTimelinePreset: GetOrCreateTimelinePresetUseCase,
    private val validateTimeInput: ValidateTimeInputUseCase,
    private val deletePreset: DeletePresetUseCase,
    private val updatePreset: UpdatePresetUseCase
) : ViewModel() {

    var uiState by mutableStateOf(TimelineUiState())
        private set

    private val actions = Channel<SettingsUiAction>()
    val uiActions = actions.receiveAsFlow()

    private val dataId: Int = checkNotNull(savedStateHandle[HOME_PRESET_ID_KEY])
    private var currentPreset: TimelinePreset? = null

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            getOrCreateTimelinePreset(dataId).collect { preset ->
                updateUiStateData(preset)
            }
        }
    }


    fun onTimelineUiEvent(event: TimelineUiEvent) {
        when (event) {
            is TimelineUiEvent.DelayTimeMinutesChange -> {
                uiState = uiState.copy(
                    delayTimeState = uiState.delayTimeState.copy(
                        time = uiState.delayTimeState.time.copy(minutes = event.minutes)
                    )
                )
            }

            is TimelineUiEvent.DelayTimeSecondsChange -> {
                uiState = uiState.copy(
                    delayTimeState = uiState.delayTimeState.copy(
                        time = uiState.delayTimeState.time.copy(seconds = event.seconds)
                    )
                )
            }

            is TimelineUiEvent.CockingTimeActivationChange -> {
                uiState = uiState.copy(
                    cockingTimeState = uiState.cockingTimeState.copy(
                        enabled = event.isEnabled
                    )
                )
            }

            is TimelineUiEvent.CockingTimeMinutesChange -> {
                uiState = uiState.copy(
                    cockingTimeState = uiState.cockingTimeState.copy(
                        time = uiState.cockingTimeState.time.copy(minutes = event.minutes)
                    )
                )
            }

            is TimelineUiEvent.CockingTimeSecondsChange -> {
                uiState = uiState.copy(
                    cockingTimeState = uiState.cockingTimeState.copy(
                        time = uiState.cockingTimeState.time.copy(seconds = event.seconds)
                    )
                )
            }

            is TimelineUiEvent.SelfDestructionTimeMinutesChange -> {
                uiState = uiState.copy(
                    selfDestructionTimeState = uiState.selfDestructionTimeState.copy(
                        time = uiState.selfDestructionTimeState.time.copy(minutes = event.minutes)
                    )
                )
            }

            is TimelineUiEvent.SelfDestructionTimeSecondsChange -> {
                uiState = uiState.copy(
                    selfDestructionTimeState = uiState.selfDestructionTimeState.copy(
                        time = uiState.selfDestructionTimeState.time.copy(seconds = event.seconds)
                    )
                )
            }

            TimelineUiEvent.NextButtonClick -> {
                uiState = uiState.copy(
                    delayTimeState = uiState.delayTimeState.copy(
                        error = validateTimeInput(
                            uiState.delayTimeState.time,
                            uiState.delayTimeState.timeLimits
                        )
                    )
                )
                uiState = uiState.copy(
                    cockingTimeState = uiState.cockingTimeState.copy(
                        error = validateTimeInput(
                            uiState.cockingTimeState.time,
                            uiState.cockingTimeState.timeLimits
                        )
                    )
                )
                uiState = uiState.copy(
                    selfDestructionTimeState = uiState.selfDestructionTimeState.copy(
                        error = validateTimeInput(
                            uiState.cockingTimeState.time,
                            uiState.cockingTimeState.timeLimits
                        )
                    )
                )

                if (!uiState.isError) {
                    savePresetData()
                    viewModelScope.launch {
                        actions.send(SettingsUiAction.NavigateNext(dataId))
                    }
                }

            }

            TimelineUiEvent.CloseClick -> {
                viewModelScope.launch {
                    deletePreset(dataId)
                    actions.send(SettingsUiAction.Close)
                }
            }

        }
    }

    private fun updateUiStateData(newPreset: TimelinePreset) {
        currentPreset = newPreset
        currentPreset?.let { preset ->
            uiState = uiState.copy(
                isLoaded = true,
                delayTimeState = uiState.delayTimeState.copy(
                    time = uiState.delayTimeState.time.copy(
                        minutes = preset.delayTimeMin,
                        seconds = preset.delayTimeSec
                    )
                ),
                cockingTimeState = uiState.cockingTimeState.copy(
                    time = uiState.cockingTimeState.time.copy(
                        minutes = preset.cockingTimeMin,
                        seconds = preset.cockingTimeSec
                    ),
                    enabled = preset.cockingTimeEnabled
                ),
                selfDestructionTimeState = uiState.selfDestructionTimeState.copy(
                    time = uiState.selfDestructionTimeState.time.copy(
                        minutes = preset.selfDestructionTimeMin,
                        seconds = preset.selfDestructionTimeSec
                    )
                ),
            )
        }
    }

    private fun savePresetData() {
        viewModelScope.launch {
            currentPreset?.let { preset ->
                updatePreset(
                    preset.copy(
                        delayTimeMin = uiState.delayTimeState.time.minutes,
                        delayTimeSec = uiState.delayTimeState.time.seconds,
                        cockingTimeMin = uiState.cockingTimeState.time.minutes,
                        cockingTimeSec = uiState.cockingTimeState.time.seconds,
                        cockingTimeEnabled = uiState.cockingTimeState.enabled,
                        selfDestructionTimeMin = uiState.selfDestructionTimeState.time.minutes,
                        selfDestructionTimeSec = uiState.selfDestructionTimeState.time.seconds,
                    )
                )
            }
        }
    }


}