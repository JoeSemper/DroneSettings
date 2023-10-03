package com.joesemper.dronesettings.ui.settings.screens.mapping

sealed class MappingUiEvent {
    class RelayOneStateChange(val selected: MappingSelectOption): MappingUiEvent()
    class RelayTwoStateChange(val selected: MappingSelectOption): MappingUiEvent()
    class PulseOneStateChange(val selected: MappingSelectOption): MappingUiEvent()
    class PulseTwoStateChange(val selected: MappingSelectOption): MappingUiEvent()
    class PulseThreeStateChange(val selected: MappingSelectOption): MappingUiEvent()
    object NextButtonClick: MappingUiEvent()
    object BackButtonClick: MappingUiEvent()
    object CloseClick: MappingUiEvent()
}