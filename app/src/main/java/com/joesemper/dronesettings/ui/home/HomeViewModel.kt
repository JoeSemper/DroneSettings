package com.joesemper.dronesettings.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joesemper.dronesettings.data.datasource.room.entity.PresetData
import com.joesemper.dronesettings.domain.use_case.CreatePresetDataUseCase
import com.joesemper.dronesettings.domain.use_case.GetAllPresetsUseCase
import com.joesemper.dronesettings.utils.unixTimeToDate
import com.joesemper.dronesettings.utils.unixTimeToTime
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val createSettingsPreset: CreatePresetDataUseCase,
    private val getAllSettingsPresets: GetAllPresetsUseCase
) : ViewModel() {

    var uiState = mutableStateOf(HomeUiState())
        private set

    private val actions = Channel<HomeUiAction>()
    val uiActions = actions.receiveAsFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            getAllSettingsPresets().collect { presets ->
                updateUiData(presets)
            }
        }
    }

    fun onNewSettingsPresetClick() {
        viewModelScope.launch {
            val settingsSet = createSettingsPreset()
            actions.send(HomeUiAction.NewSettingsSet(settingsSet.dataId))
        }
    }

    fun onPresetClick(setId: Int) {
        viewModelScope.launch {
            actions.send(HomeUiAction.OpenPreset(setId))
        }
    }

    private fun updateUiData(presets: List<PresetData>) {
        uiState.value = uiState.value.copy(
            isLoading = false,
            presets = splitPresetsByDates(presets)
        )
    }

    private fun splitPresetsByDates(sets: List<PresetData>): Map<String, List<PresetDataUiState>> {
        return sets.map {
            PresetDataUiState(
                dataId = it.dataId,
                name = it.name,
                description = it.description,
                date = unixTimeToDate(it.date),
                time = unixTimeToTime(it.date),
                saved = it.saved
            )
        }.filter { it.saved }.groupBy { it.date }
    }
}

sealed class HomeUiAction() {
    class NewSettingsSet(val dataId: Int) : HomeUiAction()
    class OpenPreset(val dataId: Int) : HomeUiAction()
}