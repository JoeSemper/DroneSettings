package com.joesemper.dronesettings.ui.settings.screens.timeline

import com.joesemper.dronesettings.data.constants.SettingsConstants
import com.joesemper.dronesettings.ui.settings.screens.timeline.state.DelayTimeFieldState
import com.joesemper.dronesettings.ui.settings.state.Limits
import com.joesemper.dronesettings.ui.settings.state.TimeFieldState
import com.joesemper.dronesettings.ui.settings.state.TimeState
import com.joesemper.dronesettings.ui.settings.state.errors.TimeInputError

data class TimelineUiState(
    val isLoaded: Boolean = false,
    val delayTimeState: DelayTimeUiState = DelayTimeUiState(),
    val cockingTimeState: CockingTimeUiState = CockingTimeUiState(),
    val selfDestructionTimeState: SelfDestructionTimeUiState = SelfDestructionTimeUiState(),
    val minBatteryVoltageUiState: MinBatteryVoltageUiState = MinBatteryVoltageUiState()
) {
    val isError =
        !((delayTimeState.error is TimeInputError.None) and
                (cockingTimeState.error is TimeInputError.None) and
                (selfDestructionTimeState.error is TimeInputError.None))
}

data class DelayTimeUiState(
    val time: TimeState = TimeState(),
    val timeLimits: Limits = Limits(
        minValue = SettingsConstants.MIN_TIME_DELAY,
        maxValue = SettingsConstants.MAX_TIME_DELAY
    ),
    val error: TimeInputError = TimeInputError.None
)

data class CockingTimeUiState(
    val time: TimeState = TimeState(),
    val timeLimits: Limits = Limits(
        minValue = SettingsConstants.MIN_COCKING_TIME,
        maxValue = SettingsConstants.MAX_COCKING_TIME
    ),
    val error: TimeInputError = TimeInputError.None,
)

data class SelfDestructionTimeUiState(
    val minutes: String = "00",
    val timeLimits: Limits = Limits(
        minValue = SettingsConstants.MIN_SELF_DESTRUCTION_TIME,
        maxValue = SettingsConstants.MAX_SELF_DESTRUCTION_TIME
    ),
    val error: TimeInputError = TimeInputError.None
)

data class MinBatteryVoltageUiState(
    val voltage: String = "0",
    val timeLimits: Limits = Limits(
        minValue = SettingsConstants.MIN_VOLTAGE_VALUE,
        maxValue = SettingsConstants.MAX_VOLTAGE_VALUE
    ),
    val error: TimeInputError = TimeInputError.None
)