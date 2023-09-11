package com.joesemper.dronesettings.ui.settings.timeline

data class TimelineUiState(
    val delayTimeMinutes: String = "",
    val delayTimeSeconds: String = "",
    val cockingTimeMinutes: String = "",
    val cockingTimeSeconds: String = "",
    val isCockingTimeEnabled: Boolean = true,
    val selfDestructionTimeMinutes: String = "",
    val selfDestructionTimeSeconds: String = "",
)