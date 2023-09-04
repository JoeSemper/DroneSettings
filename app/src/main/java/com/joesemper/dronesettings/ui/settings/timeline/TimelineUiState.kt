package com.joesemper.dronesettings.ui.settings.timeline

data class TimelineUiState(
    val delayTimeMinutes: String = "",
    val delayTimeSeconds: String = "",
    val cockingTimeMinutes: String = "",
    val cockingTimeSeconds: String = "",
    val isCockingTimeActivated: Boolean = true,
    val selfDestructionTimeMinutes: String = "",
    val selfDestructionTimeSeconds: String = "",
)