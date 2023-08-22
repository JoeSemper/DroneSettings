package com.joesemper.dronesettings.domain.entity

import com.joesemper.dronesettings.data.constants.SettingsConstants

data class SignalConstants(
    val cockingPulseHighDefault: Int = SettingsConstants.COCKING_PULSE_DURATION_HIGH_DEFAULT_VALUE,
    val cockingPulseLowDefault: Int = SettingsConstants.COCKING_PULSE_DURATION_LOW_DEFAULT_VALUE,
    val cockingPulseAmountDefault: Int = SettingsConstants.COCKING_PULSE_AMOUNT_DEFAULT_VALUE,
    val activationPulseHighDefault: Int = SettingsConstants.ACTIVATION_PULSE_DURATION_HIGH_DEFAULT_VALUE,
    val activationPulseLowDefault: Int = SettingsConstants.ACTIVATION_PULSE_DURATION_LOW_DEFAULT_VALUE,
    val activationPulseAmountDefault: Int = SettingsConstants.ACTIVATION_PULSE_AMOUNT_DEFAULT_VALUE,
)