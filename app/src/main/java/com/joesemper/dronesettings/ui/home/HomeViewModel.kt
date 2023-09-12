package com.joesemper.dronesettings.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joesemper.dronesettings.data.datasource.room.entity.SettingsSet
import com.joesemper.dronesettings.domain.use_case.CreateSettingsSetUseCase
import com.joesemper.dronesettings.domain.use_case.GetAllSettingsSetsUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val createSettingsPreset: CreateSettingsSetUseCase,
    private val getAllSettingsPresets: GetAllSettingsSetsUseCase
) : ViewModel() {

    var uiState = mutableStateOf<List<SettingsSet>>(listOf())
        private set

    private val events = Channel<Int>()
    val uiEvents = events.receiveAsFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            getAllSettingsPresets().collect {
                uiState.value = it
            }
        }
    }


    fun onNewSettingsPresetClick() {
        viewModelScope.launch {
            val settingsPreset = createSettingsPreset()
            events.send(settingsPreset.setId)
        }
    }
}