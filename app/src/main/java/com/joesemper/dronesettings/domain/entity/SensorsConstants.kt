package com.joesemper.dronesettings.domain.entity

import com.joesemper.dronesettings.data.constants.SettingsConstants

data class SensorsConstants (
    val minTargetDistance: Int = SettingsConstants.MIN_TARGET_DISTANCE,
    val maxTargetDistance: Int = SettingsConstants.MAX_TARGET_DISTANCE,
    val minVoltageDefault: Int = SettingsConstants.MIN_VOLTAGE_DEFAULT_VALUE,
    val averageAccelerationDefault: Int = SettingsConstants.AVERAGE_ACCELERATION_DEFAULT_VALUE,
    val averageDeviationDefault: Int = SettingsConstants.AVERAGE_DEVIATION_DEFAULT_VALUE,
    val deviationCoefficientDefault: Int = SettingsConstants.DEVIATION_COEFFICIENT_DEFAULT_VALUE,
    val deadTimeDefault: Int = SettingsConstants.DEAD_TIME_DEFAULT_VALUE
)