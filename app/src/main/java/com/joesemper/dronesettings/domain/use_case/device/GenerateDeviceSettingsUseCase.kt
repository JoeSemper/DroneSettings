package com.joesemper.dronesettings.domain.use_case.device

import com.joesemper.dronesettings.domain.entity.Settings
import com.joesemper.dronesettings.domain.entity.device.DeviceSettings
import com.joesemper.dronesettings.utils.Constants.Companion.MILLISECONDS_IN_MINUTE
import com.joesemper.dronesettings.utils.Constants.Companion.MILLISECONDS_IN_SECOND

class GenerateDeviceSettingsUseCase {
    operator fun invoke(settings: Settings): DeviceSettings {
        return DeviceSettings(
            delayTime = getDelayTimeValue(settings),
            cockingTime = getCockingTimeValue(settings),
            selfDestructionTime = getSelfDestructionTimeValue(settings),
            minVoltage = getMinVoltageTimeValue(settings)
        )
    }

    private fun getDelayTimeValue(settings: Settings): Int {
        return (settings.delayTimeMin.toInt() * MILLISECONDS_IN_MINUTE +
                settings.delayTimeSec.toInt() * MILLISECONDS_IN_SECOND)
    }

    private fun getCockingTimeValue(settings: Settings): Int {
        return (settings.cockingTimeMin.toInt() * MILLISECONDS_IN_MINUTE +
                settings.cockingTimeSec.toInt() * MILLISECONDS_IN_SECOND)
    }

    private fun getSelfDestructionTimeValue(settings: Settings): Int {
        return settings.selfDestructionTimeMin.toInt() * MILLISECONDS_IN_MINUTE
    }

    private fun getMinVoltageTimeValue(settings: Settings): Float {
        return settings.minBatteryVoltage.toFloat()
    }
}