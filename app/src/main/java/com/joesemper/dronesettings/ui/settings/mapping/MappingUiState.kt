package com.joesemper.dronesettings.ui.settings.mapping

data class MappingUiState(
    val relayOneState: MappingSelectOption = MappingSelectOption.NONE,
    val relayTwoState: MappingSelectOption = MappingSelectOption.NONE,
    val pulseOneState: MappingSelectOption = MappingSelectOption.NONE,
    val pulseTwoState: MappingSelectOption = MappingSelectOption.NONE,
    val pulseThreeState: MappingSelectOption = MappingSelectOption.NONE,
)

enum class MappingSelectOption {
    COCKING, ACTIVATION, NONE
}
