package com.joesemper.dronesettings.ui.settings.signal

sealed class SignalUiEvent {
    class CockingPulseWidthHiChange(val width: String): SignalUiEvent()
    class CockingPulseWidthLoChange(val width: String): SignalUiEvent()
    class CockingPulseAmountChange(val amount: String): SignalUiEvent()
    class InfiniteCockingPulseRepeatChange(val enabled: Boolean): SignalUiEvent()
    class ActivationPulseWidthHiChange(val width: String): SignalUiEvent()
    class ActivationPulseWidthLoChange(val width: String): SignalUiEvent()
    class ActivationPulseAmountChange(val amount: String): SignalUiEvent()
    class InfiniteActivationPulseRepeatChange(val enabled: Boolean): SignalUiEvent()
}