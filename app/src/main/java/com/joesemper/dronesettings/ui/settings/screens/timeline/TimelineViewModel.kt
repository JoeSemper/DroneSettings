package com.joesemper.dronesettings.ui.settings.screens.timeline

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joesemper.dronesettings.data.datasource.room.main.entity.TimelinePreset
import com.joesemper.dronesettings.domain.entity.Settings
import com.joesemper.dronesettings.domain.use_case.DeletePresetUseCase
import com.joesemper.dronesettings.domain.use_case.GetOrCreateTimelinePresetUseCase
import com.joesemper.dronesettings.domain.use_case.UpdatePresetUseCase
import com.joesemper.dronesettings.domain.use_case.validation.ValidateSettingsInputUseCase
import com.joesemper.dronesettings.domain.use_case.validation.ValidateTimeInputUseCase
import com.joesemper.dronesettings.navigation.home.HomeDestinations.HOME_PRESET_ID_KEY
import com.joesemper.dronesettings.ui.settings.state.SettingsUiAction
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TimelineViewModel(
    savedStateHandle: SavedStateHandle,
    private val getOrCreateTimelinePreset: GetOrCreateTimelinePresetUseCase,
    private val validateSettings: ValidateSettingsInputUseCase,
    private val deletePreset: DeletePresetUseCase,
    private val updatePreset: UpdatePresetUseCase
) : ViewModel() {

    var uiState by mutableStateOf(TimelineUiState())
        private set

    private val actions = Channel<SettingsUiAction>()
    val uiActions = actions.receiveAsFlow()

    val dataId: Int = checkNotNull(savedStateHandle[HOME_PRESET_ID_KEY])
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
            is TimelineUiEvent.DelayTimeChange -> {
                uiState = uiState.copy(
                    delayTimeState = uiState.delayTimeState.copy(
                        time = uiState.delayTimeState.time.copy(
                            minutes = event.minutes,
                            seconds = event.seconds
                        )
                    )
                )
                checkErrors()
            }

            is TimelineUiEvent.CockingTimeChange -> {
                uiState = uiState.copy(
                    cockingTimeState = uiState.cockingTimeState.copy(
                        time = uiState.cockingTimeState.time.copy(
                            minutes = event.minutes,
                            seconds = event.seconds
                        )
                    )
                )
                checkErrors()
            }

            is TimelineUiEvent.SelfDestructionTimeMinutesChange -> {
                uiState = uiState.copy(
                    selfDestructionTimeState = uiState.selfDestructionTimeState.copy(
                        minutes = event.minutes
                    )
                )
                checkErrors()
            }

            is TimelineUiEvent.MinBatteryVoltageChange -> {
                uiState = uiState.copy(
                    minBatteryVoltageUiState = uiState.minBatteryVoltageUiState.copy(
                        voltage = event.voltage
                    )
                )
                checkErrors()
            }

            TimelineUiEvent.NextButtonClick -> {
                uiState = uiState.copy(
                    showErrors = true
                )

                checkErrors()

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

    private fun checkErrors() {
        if(!uiState.showErrors) return

        val errors = validateSettings(
            Settings(
                delayTimeMin = uiState.delayTimeState.time.minutes,
                delayTimeSec = uiState.delayTimeState.time.seconds,
                cockingTimeMin = uiState.cockingTimeState.time.minutes,
                cockingTimeSec = uiState.cockingTimeState.time.seconds,
                selfDestructionTimeMin = uiState.selfDestructionTimeState.minutes,
                minBatteryVoltage = uiState.minBatteryVoltageUiState.voltage
            )
        )


        uiState = uiState.copy(
            delayTimeState = uiState.delayTimeState.copy(
                error = errors.delayTimeError
            )
        )
        uiState = uiState.copy(
            cockingTimeState = uiState.cockingTimeState.copy(
                error = errors.cockingTimeError
            )
        )
        uiState = uiState.copy(
            selfDestructionTimeState = uiState.selfDestructionTimeState.copy(
                error = errors.maximumTimeError
            )
        )

        uiState = uiState.copy(
            minBatteryVoltageUiState = uiState.minBatteryVoltageUiState.copy(
                error = errors.batteryVoltageError
            )
        )
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
                ),
                selfDestructionTimeState = uiState.selfDestructionTimeState.copy(
                    minutes = preset.selfDestructionTimeMin
                ),
                minBatteryVoltageUiState = uiState.minBatteryVoltageUiState.copy(
                    voltage = preset.minBatteryVoltage
                )
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
                        selfDestructionTimeMin = uiState.selfDestructionTimeState.minutes,
                        minBatteryVoltage = uiState.minBatteryVoltageUiState.voltage
                    )
                )
            }
        }
    }


}