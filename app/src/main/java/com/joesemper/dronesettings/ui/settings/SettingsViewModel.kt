package com.joesemper.dronesettings.ui.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.joesemper.dronesettings.domain.entity.TimelineConstants

class SettingsViewModel : ViewModel() {

    var uiState by mutableStateOf(SettingsUiState())
        private set

    fun onDelayTimeMinChange(newValue: String) {
        uiState = uiState.copy(
            timelineScreenState = uiState.timelineScreenState.copy(
                delayTimeInputMin = newValue
            )
        )
    }

    fun onDelayTimeSecChange(newValue: String) {
        uiState = uiState.copy(
            timelineScreenState = uiState.timelineScreenState.copy(
                delayTimeInputSec = newValue
            )
        )
    }

}

data class SettingsUiState(
    val timelineScreenState: TimelineScreenState = TimelineScreenState()
)

data class TimelineScreenState(
    val constants: TimelineConstants = TimelineConstants(),
    val delayTimeInputMin: String = "0",
    val delayTimeInputSec: String = "0",
)
