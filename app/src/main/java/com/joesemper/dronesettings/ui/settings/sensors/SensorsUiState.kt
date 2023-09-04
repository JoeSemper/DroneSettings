package com.joesemper.dronesettings.ui.settings.sensors

data class SensorsUiState(
    val targetDistance: Float = 0f,
    val minVoltage: String = "",
    val isOverloadActivationEnabled: Boolean = true,
    val isDeadTimeActivationEnabled: Boolean = true,
    val averageAcceleration: String = "",
    val averageDeviation: String = "",
    val deviationCoefficient: String = "",
    val deadTime: String = ""
)