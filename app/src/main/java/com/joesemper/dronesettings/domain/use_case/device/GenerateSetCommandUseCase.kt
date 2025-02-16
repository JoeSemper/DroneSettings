package com.joesemper.dronesettings.domain.use_case.device

import com.joesemper.dronesettings.domain.entity.Settings

class GenerateSetCommandUseCase(
    private val generateDeviceSettings: GenerateDeviceSettingsUseCase
) {

    operator fun invoke(settings: Settings): String {
        val deviceSettings = generateDeviceSettings(settings)

        return "set ${deviceSettings.delayTime} ${deviceSettings.cockingTime} ${deviceSettings.selfDestructionTime} ${deviceSettings.minVoltage}\r\n"
    }

}