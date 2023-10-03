package com.joesemper.dronesettings.ui.settings.screens.timeline

sealed class TimelineUiEvent() {
    class DelayTimeMinutesChange(val minutes: String) : TimelineUiEvent()
    class DelayTimeSecondsChange(val seconds: String) : TimelineUiEvent()
    class CockingTimeMinutesChange(val minutes: String) : TimelineUiEvent()
    class CockingTimeSecondsChange(val seconds: String) : TimelineUiEvent()
    class CockingTimeActivationChange(val isEnabled: Boolean) : TimelineUiEvent()
    class SelfDestructionTimeMinutesChange(val minutes: String) : TimelineUiEvent()
    class SelfDestructionTimeSecondsChange(val seconds: String) : TimelineUiEvent()
    object NextButtonClick : TimelineUiEvent()
    object CloseClick : TimelineUiEvent()

}