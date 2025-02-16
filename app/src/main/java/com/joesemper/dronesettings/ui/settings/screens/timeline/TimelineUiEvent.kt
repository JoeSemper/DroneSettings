package com.joesemper.dronesettings.ui.settings.screens.timeline

sealed class TimelineUiEvent() {
    class DelayTimeChange(val minutes: String, val seconds :String) : TimelineUiEvent()
    class CockingTimeChange(val minutes: String, val seconds :String) : TimelineUiEvent()
    class SelfDestructionTimeMinutesChange(val minutes: String) : TimelineUiEvent()
    class MinBatteryVoltageChange(val voltage: String) : TimelineUiEvent()
    object NextButtonClick : TimelineUiEvent()
    object CloseClick : TimelineUiEvent()

}