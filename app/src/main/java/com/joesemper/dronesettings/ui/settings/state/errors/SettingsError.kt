package com.joesemper.dronesettings.ui.settings.state.errors

data class SettingsError(
    val delayTimeError: SettingsParameterError = SettingsParameterError.None,
    val cockingTimeError: SettingsParameterError = SettingsParameterError.None,
    val maximumTimeError: SettingsParameterError = SettingsParameterError.None,
    val batteryVoltageError: SettingsParameterError = SettingsParameterError.None,
)

sealed class SettingsParameterError(){
    object None:SettingsParameterError()
    object EmptyField:SettingsParameterError()
    object FieldConflict:SettingsParameterError()
    object OutOfRange:SettingsParameterError()
}