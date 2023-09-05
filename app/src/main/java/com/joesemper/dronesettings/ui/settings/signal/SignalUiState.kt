package com.joesemper.dronesettings.ui.settings.signal

data class SignalUiState (
    val cockingPulseWidthHi: String = "",
    val cockingPulseWidthLo: String = "",
    val cockingPulseAmount: String = "",
    val infiniteCockingPulseRepeat: Boolean = true,
    val activationPulseWidthHi: String = "",
    val activationPulseWidthLo: String = "",
    val infiniteActivationPulseRepeat: Boolean = true,
    val activationPulseAmount: String = "",
)