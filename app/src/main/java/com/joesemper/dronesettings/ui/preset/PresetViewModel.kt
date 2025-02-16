package com.joesemper.dronesettings.ui.preset

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joesemper.dronesettings.data.datasource.room.main.entity.PresetData
import com.joesemper.dronesettings.domain.entity.SettingsPreset
import com.joesemper.dronesettings.domain.use_case.DeletePresetUseCase
import com.joesemper.dronesettings.domain.use_case.GetSettingsPresetUseCase
import com.joesemper.dronesettings.domain.use_case.UpdatePresetDataUseCase
import com.joesemper.dronesettings.domain.use_case.device.GenerateDeviceSettingsUseCase
import com.joesemper.dronesettings.domain.use_case.device.GenerateSetCommandUseCase
import com.joesemper.dronesettings.navigation.home.HomeDestinations.HOME_PRESET_ID_KEY
import com.joesemper.dronesettings.usb.UsbConnectionManager
import com.joesemper.dronesettings.usb.UsbConnectionMassage

import kotlinx.coroutines.launch

class PresetViewModel(
    savedStateHandle: SavedStateHandle,
    private val getSettingsPreset: GetSettingsPresetUseCase,
    private val updatePresetData: UpdatePresetDataUseCase,
    private val deletePreset: DeletePresetUseCase,
    private val connectionManager: UsbConnectionManager,
    private val generateSetCommand: GenerateSetCommandUseCase,
    private val generateDeviceSettings: GenerateDeviceSettingsUseCase
) : ViewModel() {

    val uiState = mutableStateOf(PresetUiState())
    private val deviceConnectionUiState = mutableStateOf(DeviceConnectionUiState())

    private val dataId: Int = checkNotNull(savedStateHandle[HOME_PRESET_ID_KEY])

    init {
        loadData()
        setUpDevice()
    }

    private fun loadData() {
        viewModelScope.launch {
            getSettingsPreset(dataId).collect { preset ->
                preset?.let {
                    updateUiData(it)
                }

            }
        }
    }

    fun onSavePreset(
        newName: String,
        newDescription: String
    ) {
        viewModelScope.launch {
            updatePresetData(
                data = PresetData(
                    dataId = uiState.value.settingsPreset.dataId,
                    name = newName,
                    description = newDescription,
                    date = uiState.value.settingsPreset.date,
                    saved = true

                )
            )
        }
    }

    fun deletePreset() {
        viewModelScope.launch {
            deletePreset(dataId)
        }
    }

    fun writePresetOnDevice() {
        clearLog()
        connect()
        viewModelScope.launch {
            connectionManager.connection.collect { isConnected ->
                if(isConnected) {
                    deviceConnectionUiState.value = deviceConnectionUiState.value.copy(
                        deviceWriteStatus = DeviceWriteStatus.WRITING
                    )
                    sendCommand(generateSetCommand(uiState.value.settingsPreset.settings))
                }
            }
        }
    }

    private fun updateUiData(preset: SettingsPreset) {
        uiState.value = uiState.value.copy(
            isLoading = false,
            settingsPreset = preset
        )
    }

    private fun setUpDevice() {
        clearLog()
        subscribeOnDeviceLog()
    }

    private fun sendCommand(command: String) {
        connectionManager.send(command)
    }

    private fun connect() {
        deviceConnectionUiState.value = deviceConnectionUiState.value.copy(
            deviceWriteStatus = DeviceWriteStatus.CONNECTING
        )
        connectionManager.connect()
    }

    private fun disconnect() {
        connectionManager.disconnect()
    }

    private fun clearLog() {
        connectionManager.clearLog()
    }

    private fun subscribeOnDeviceLog() {
        viewModelScope.launch {
            connectionManager.log.collect { massages ->
                deviceConnectionUiState.value =
                    deviceConnectionUiState.value.copy(
                        log = massages
                    )
            }
        }
    }

    private fun subscribeOnConnectionStatus() {
        viewModelScope.launch {
            connectionManager.connection.collect { isConnected ->
                deviceConnectionUiState.value =
                    deviceConnectionUiState.value.copy(isConnected = isConnected)
            }
        }
    }

}

data class PresetUiState(
    val isLoading: Boolean = true,
    val settingsPreset: SettingsPreset = SettingsPreset(),
)

data class DeviceConnectionUiState(
    val log: List<UsbConnectionMassage> = emptyList(),
    val deviceWriteStatus: DeviceWriteStatus = DeviceWriteStatus.NEUTRAL,
    val isConnected: Boolean = false,
)

enum class DeviceWriteStatus {
    NEUTRAL, CONNECTING, WRITING, COMPLETE, ERROR
}

