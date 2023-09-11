package com.joesemper.dronesettings.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joesemper.dronesettings.domain.use_case.CreateSettingsPresetUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val createSettingsPresetUseCase: CreateSettingsPresetUseCase
) : ViewModel() {

    private val events = Channel<Int>()
    val uiEvents = events.receiveAsFlow()

    fun onNewSettingsPresetClick() {
        viewModelScope.launch {
            val settingsPreset = createSettingsPresetUseCase()
            events.send(settingsPreset.presetId)
        }
    }
}