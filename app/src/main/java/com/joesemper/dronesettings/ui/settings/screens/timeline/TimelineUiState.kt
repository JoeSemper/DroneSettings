package com.joesemper.dronesettings.ui.settings.screens.timeline

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.joesemper.dronesettings.R
import com.joesemper.dronesettings.data.constants.SettingsConstants
import com.joesemper.dronesettings.ui.settings.screens.timeline.state.DelayTimeFieldState
import com.joesemper.dronesettings.ui.settings.state.Limits
import com.joesemper.dronesettings.ui.settings.state.TimeFieldState
import com.joesemper.dronesettings.ui.settings.state.TimeState
import com.joesemper.dronesettings.ui.settings.state.errors.SettingsError
import com.joesemper.dronesettings.ui.settings.state.errors.SettingsParameterError
import com.joesemper.dronesettings.ui.settings.state.errors.TimeInputError
import com.joesemper.dronesettings.utils.Constants.Companion.SECONDS_IN_MINUTE

data class TimelineUiState(
    val isLoaded: Boolean = false,
    val showErrors: Boolean = false,
    val delayTimeState: DelayTimeUiState = DelayTimeUiState(),
    val cockingTimeState: CockingTimeUiState = CockingTimeUiState(),
    val selfDestructionTimeState: SelfDestructionTimeUiState = SelfDestructionTimeUiState(),
    val minBatteryVoltageUiState: MinBatteryVoltageUiState = MinBatteryVoltageUiState()
) {
    val isError = delayTimeState.isError ||
            cockingTimeState.isError ||
            selfDestructionTimeState.isError ||
            minBatteryVoltageUiState.isError
}

data class DelayTimeUiState(
    val time: TimeState = TimeState(),
    val timeLimits: Limits = Limits(
        minValue = SettingsConstants.MIN_TIME_DELAY  / SECONDS_IN_MINUTE,
        maxValue = SettingsConstants.MAX_TIME_DELAY  / SECONDS_IN_MINUTE
    ),
    val error: SettingsParameterError = SettingsParameterError.None
) {
    val isError: Boolean
        get() = error !is SettingsParameterError.None

    @Composable
    fun getErrorMassage(): String {
        return when (error) {
            SettingsParameterError.EmptyField -> stringResource(R.string.fields_must_not_be_empty)
            SettingsParameterError.FieldConflict -> stringResource(R.string.field_conflict)
            SettingsParameterError.None -> stringResource(R.string.empty_string)
            SettingsParameterError.OutOfRange -> stringResource(R.string.value_is_out_of_range)
        }
    }
}

data class CockingTimeUiState(
    val time: TimeState = TimeState(),
    val timeLimits: Limits = Limits(
        minValue = SettingsConstants.MIN_COCKING_TIME / SECONDS_IN_MINUTE,
        maxValue = SettingsConstants.MAX_COCKING_TIME  / SECONDS_IN_MINUTE
    ),
    val error: SettingsParameterError = SettingsParameterError.None
) {
    val isError: Boolean
        get() = error !is SettingsParameterError.None

    @Composable
    fun getErrorMassage(): String {
        return when (error) {
            SettingsParameterError.EmptyField -> stringResource(R.string.fields_must_not_be_empty)
            SettingsParameterError.FieldConflict -> stringResource(R.string.cocking_time_field_conflict)
            SettingsParameterError.None -> stringResource(R.string.empty_string)
            SettingsParameterError.OutOfRange -> stringResource(R.string.value_is_out_of_range)
        }
    }
}

data class SelfDestructionTimeUiState(
    val minutes: String = "",
    val timeLimits: Limits = Limits(
        minValue = SettingsConstants.MIN_SELF_DESTRUCTION_TIME / SECONDS_IN_MINUTE,
        maxValue = SettingsConstants.MAX_SELF_DESTRUCTION_TIME / SECONDS_IN_MINUTE
    ),
    val error: SettingsParameterError = SettingsParameterError.None
){
    val isError: Boolean
        get() = error !is SettingsParameterError.None

    @Composable
    fun getErrorMassage(): String {
        return when (error) {
            SettingsParameterError.EmptyField -> stringResource(R.string.fields_must_not_be_empty)
            SettingsParameterError.FieldConflict -> stringResource(R.string.maximum_time_field_conflict)
            SettingsParameterError.None -> stringResource(R.string.empty_string)
            SettingsParameterError.OutOfRange -> stringResource(R.string.value_is_out_of_range)
        }
    }
}

data class MinBatteryVoltageUiState(
    val voltage: String = "",
    val timeLimits: Limits = Limits(
        minValue = SettingsConstants.MIN_VOLTAGE_VALUE,
        maxValue = SettingsConstants.MAX_VOLTAGE_VALUE
    ),
    val error: SettingsParameterError = SettingsParameterError.None
){
    val isError: Boolean
        get() = error !is SettingsParameterError.None

    @Composable
    fun getErrorMassage(): String {
        return when (error) {
            SettingsParameterError.EmptyField -> stringResource(R.string.fields_must_not_be_empty)
            SettingsParameterError.FieldConflict -> stringResource(R.string.field_conflict)
            SettingsParameterError.None -> stringResource(R.string.empty_string)
            SettingsParameterError.OutOfRange -> stringResource(R.string.value_is_out_of_range)
        }
    }
}