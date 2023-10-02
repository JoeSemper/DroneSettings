package com.joesemper.dronesettings.ui.settings.timeline

import com.joesemper.dronesettings.ui.settings.entity.errors.TimeInputError

data class TimelineUiState(
    val isLoaded: Boolean = false,
    val delayTimeState: DelayTimeUiState = DelayTimeUiState(),
    val cockingTimeState: CockingTimeUiState = CockingTimeUiState(),
    val selfDestructionTimeState: SelfDestructionTimeUiState = SelfDestructionTimeUiState(),
)

data class TimeUiState(
    val minutes: String = "",
    val seconds: String = "",
)

data class DelayTimeUiState(
    val time: TimeUiState = TimeUiState(),
    val error: TimeInputError = TimeInputError.None
)

data class CockingTimeUiState(
    val time: TimeUiState = TimeUiState(),
    val error: TimeInputError = TimeInputError.None,
    val enabled: Boolean = false
)

data class SelfDestructionTimeUiState(
    val time: TimeUiState = TimeUiState(),
    val error: TimeInputError = TimeInputError.None
)