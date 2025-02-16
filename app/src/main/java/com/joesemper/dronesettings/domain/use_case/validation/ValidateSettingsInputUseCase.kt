package com.joesemper.dronesettings.domain.use_case.validation

import com.joesemper.dronesettings.data.constants.SettingsConstants.Companion.MAX_COCKING_TIME
import com.joesemper.dronesettings.data.constants.SettingsConstants.Companion.MAX_SELF_DESTRUCTION_TIME
import com.joesemper.dronesettings.data.constants.SettingsConstants.Companion.MAX_TIME_DELAY
import com.joesemper.dronesettings.data.constants.SettingsConstants.Companion.MAX_VOLTAGE_VALUE
import com.joesemper.dronesettings.domain.entity.Settings
import com.joesemper.dronesettings.ui.settings.state.errors.SettingsError
import com.joesemper.dronesettings.ui.settings.state.errors.SettingsParameterError
import com.joesemper.dronesettings.utils.Constants.Companion.SECONDS_IN_MINUTE

class ValidateSettingsInputUseCase {
    operator fun invoke(settings: Settings): SettingsError {
        return SettingsError(
            delayTimeError = checkDelayTime(settings),
            cockingTimeError = checkCockingTime(settings),
            maximumTimeError = checkMaximumTime(settings),
            batteryVoltageError = checkMinVoltage(settings)
        )
    }
}

private fun checkDelayTime(settings: Settings): SettingsParameterError {
    if (settings.delayTimeMin.isEmpty() || settings.delayTimeSec.isEmpty()) {
        return SettingsParameterError.EmptyField
    } else {
        val delayTime =
            settings.delayTimeMin.toInt() * SECONDS_IN_MINUTE + settings.delayTimeSec.toInt()

        if (delayTime > MAX_TIME_DELAY) {
            return SettingsParameterError.OutOfRange
        }
    }
    return SettingsParameterError.None
}

private fun checkCockingTime(settings: Settings): SettingsParameterError {
    if (settings.cockingTimeMin.isEmpty() || settings.cockingTimeSec.isEmpty()) {
        return SettingsParameterError.EmptyField
    } else {
        val cockingTime =
            settings.cockingTimeMin.toInt() * SECONDS_IN_MINUTE + settings.cockingTimeSec.toInt()
        if (cockingTime > MAX_COCKING_TIME) {
            return SettingsParameterError.OutOfRange
        } else if(settings.delayTimeMin.isNotEmpty() && settings.delayTimeSec.isNotEmpty()) {
            val delayTime =
                settings.delayTimeMin.toInt() * SECONDS_IN_MINUTE + settings.delayTimeSec.toInt()
            if (cockingTime < delayTime) {
                return SettingsParameterError.FieldConflict
            }
        }
    }
    return SettingsParameterError.None
}

private fun checkMaximumTime(settings: Settings): SettingsParameterError {
    if (settings.selfDestructionTimeMin.isEmpty()) {
        return SettingsParameterError.EmptyField
    } else {
        if (settings.selfDestructionTimeMin.toInt() > MAX_SELF_DESTRUCTION_TIME / SECONDS_IN_MINUTE) {
            return SettingsParameterError.OutOfRange
        } else if (settings.cockingTimeMin.isNotEmpty() && settings.cockingTimeSec.isNotEmpty()) {
            val cockingTime =
                settings.cockingTimeMin.toInt() * SECONDS_IN_MINUTE + settings.cockingTimeSec.toInt()
            if (settings.selfDestructionTimeMin.toInt() * SECONDS_IN_MINUTE < cockingTime) {
                return SettingsParameterError.FieldConflict
            }
        }
    }
    return SettingsParameterError.None
}

private fun checkMinVoltage(settings: Settings): SettingsParameterError {
    if (settings.minBatteryVoltage.isEmpty()) {
        return SettingsParameterError.EmptyField
    } else {
        if (settings.minBatteryVoltage.toFloat() > MAX_VOLTAGE_VALUE) {
            return SettingsParameterError.OutOfRange
        }
    }
    return SettingsParameterError.None
}