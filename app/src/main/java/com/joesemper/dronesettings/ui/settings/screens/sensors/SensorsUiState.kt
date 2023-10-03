package com.joesemper.dronesettings.ui.settings.screens.sensors

data class SensorsUiState(
    val isLoaded: Boolean = false,
    val targetDistance: Float = 0f,
    val minVoltage: String = "",
    val isBatteryActivationEnabled: Boolean = true,
    val isOverloadActivationEnabled: Boolean = true,
    val isDeadTimeActivationEnabled: Boolean = true,
    val averageAcceleration: String = "",
    val averageDeviation: String = "",
    val deviationCoefficient: String = "",
    val deadTime: String = ""
)