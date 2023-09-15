package com.joesemper.dronesettings.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joesemper.dronesettings.data.datasource.room.entity.SettingsSet
import com.joesemper.dronesettings.domain.use_case.CreateSettingsSetUseCase
import com.joesemper.dronesettings.domain.use_case.GetAllSettingsSetsUseCase
import com.joesemper.dronesettings.utils.unixTimeToDate
import com.joesemper.dronesettings.utils.unixTimeToTime
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val createSettingsPreset: CreateSettingsSetUseCase,
    private val getAllSettingsPresets: GetAllSettingsSetsUseCase
) : ViewModel() {

    var uiState = mutableStateOf<HomeUiState>(HomeUiState.Loading)
        private set

    private val actions = Channel<HomeUiAction>()
    val uiActions = actions.receiveAsFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            getAllSettingsPresets().collect { sets ->
                updateUiData(sets)
            }
        }
    }

    fun onNewSettingsPresetClick() {
        viewModelScope.launch {
            val settingsSet = createSettingsPreset()
            actions.send(HomeUiAction.NewSettingsSet(settingsSet.setId))
        }
    }

    fun onPresetClick(setId: Int) {
        viewModelScope.launch {
            actions.send(HomeUiAction.OpenPreset(setId))
        }
    }

    private fun updateUiData(sets: List<SettingsSet>) {
        uiState.value = HomeUiState.Loaded(
            sets.map {
                SettingsSetUiState(
                    setId = it.setId,
                    name = it.name,
                    description = it.description,
                    date = unixTimeToDate(it.date),
                    time = unixTimeToTime(it.date)
                )
            }.filter { it.saved }.groupBy { it.date }
        )
    }
}

sealed class HomeUiAction() {
    class NewSettingsSet(val setId: Int): HomeUiAction()
    class OpenPreset(val setId: Int): HomeUiAction()
}