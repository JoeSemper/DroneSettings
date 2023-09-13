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

fun Int.asMappingSelectOption() =
    when (this) {
        1 -> MappingSelectOption.COCKING
        2 -> MappingSelectOption.ACTIVATION
        else -> MappingSelectOption.NONE
    }

fun MappingSelectOption.toInt() =
    when (this) {
        MappingSelectOption.COCKING -> 1
        MappingSelectOption.ACTIVATION -> 2
        else -> 0
    }


