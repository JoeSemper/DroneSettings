package com.joesemper.dronesettings.ui.settings.sensors

sealed class SensorsUiEvent {
    class TargetDistanceChange(val distance: Float) : SensorsUiEvent()
    class MinVoltageChange(val voltage: String) : SensorsUiEvent()

    class BatteryActivationChange(val isEnabled: Boolean): SensorsUiEvent()
    class OverloadActivationChange(val isEnabled: Boolean) : SensorsUiEvent()
    class DeadTimeActivationChange(val isEnabled: Boolean) : SensorsUiEvent()
    class AverageAccelerationChange(val acceleration: String): SensorsUiEvent()
    class AverageDeviationChange(val deviation: String): SensorsUiEvent()
    class DeviationCoefficientChange(val coefficient: String): SensorsUiEvent()
    class DeadTimeChange(val deadTime: String): SensorsUiEvent()
    object NextButtonClick: SensorsUiEvent()
    object BackButtonClick: SensorsUiEvent()
    object CloseClick: SensorsUiEvent()
}