package com.joesemper.dronesettings.ui.settings.mapping

sealed class MappingUiEvent {
    class RelayOneStateChange(val selected: MappingSelectOption): MappingUiEvent()
    class RelayTwoStateChange(val selected: MappingSelectOption): MappingUiEvent()
    class PulseOneStateChange(val selected: MappingSelectOption): MappingUiEvent()
    class PulseTwoStateChange(val selected: MappingSelectOption): MappingUiEvent()
    class PulseThreeStateChange(val selected: MappingSelectOption): MappingUiEvent()
}