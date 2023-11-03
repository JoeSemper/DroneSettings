package com.joesemper.dronesettings.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

    var uiState by mutableStateOf(HomeUiState())
        private set

    private var presets by mutableStateOf<List<PresetData>>(emptyList())
    
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

    fun updateSearchQuery(newQuery: String) {
        uiState.searchQuery.value = newQuery
    }

    private fun updateUiData(presets: List<PresetData>) {
        uiState = uiState.copy(
            isLoading = false,
            presets = convertToState(presets)
        )
    }

    private fun convertToState(sets: List<PresetData>): List<PresetDataUiState> {
        return sets.map {
            PresetDataUiState(
                dataId = it.dataId,
                name = it.name,
                description = it.description,
                date = unixTimeToDate(it.date),
                time = unixTimeToTime(it.date),
                saved = it.saved
            )
        }.filter { it.saved }
    }
}

sealed class HomeUiAction() {
    class NewSettingsSet(val dataId: Int) : HomeUiAction()
    class OpenPreset(val dataId: Int) : HomeUiAction()
}