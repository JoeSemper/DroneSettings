package com.joesemper.dronesettings.domain.entity.device

data class DeviceSettings(
    val delayTime: Int,
    val cockingTime: Int,
    val selfDestructionTime: Int,
    val minVoltage: Float
)