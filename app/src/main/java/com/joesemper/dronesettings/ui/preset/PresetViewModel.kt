package com.joesemper.dronesettings.ui.preset

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joesemper.dronesettings.data.constants.DeviceConstants.Companion.GET_COMPLETE
import com.joesemper.dronesettings.data.constants.DeviceConstants.Companion.SET_COMPLETE
import com.joesemper.dronesettings.data.datasource.room.main.entity.PresetData
import com.joesemper.dronesettings.domain.entity.SettingsPreset
import com.joesemper.dronesettings.domain.use_case.DeletePresetUseCase
import com.joesemper.dronesettings.domain.use_case.GetSettingsPresetUseCase
import com.joesemper.dronesettings.domain.use_case.UpdatePresetDataUseCase
import com.joesemper.dronesettings.domain.use_case.device.GenerateDeviceSettingsUseCase
import com.joesemper.dronesettings.domain.use_case.device.GenerateSetCommandUseCase
import com.joesemper.dronesettings.navigation.home.HomeDestinations.HOME_PRESET_ID_KEY
import com.joesemper.dronesettings.ui.terminal.TerminalMassage
import com.joesemper.dronesettings.ui.terminal.TerminalMassageCategory
import com.joesemper.dronesettings.usb.UsbConnectionManager
import com.joesemper.dronesettings.usb.UsbConnectionMassage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

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
    val deviceUiState = mutableStateOf(DeviceConnectionUiState())

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
        clearDeviceStatus()
        viewModelScope.launch {
            delay(500)
            sendCommand(generateSetCommand(uiState.value.settingsPreset.settings))
        }

    }

    fun onDeviceDialogClose() {
        disconnect()
        clearLog()
        clearDeviceStatus()
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
        subscribeOnConnectionStatus()
        subscribeOnErrors()
    }

    private fun sendCommand(command: String) {
        connectionManager.send(command)
    }

    fun connect() {
        connectionManager.connect()
    }

    private fun disconnect() {
        connectionManager.disconnect()
    }

    private fun clearLog() {
        connectionManager.clearLog()
        deviceUiState.value = deviceUiState.value.copy(
            log = emptyList()
        )
    }

    private fun sendGetCommand() {
        sendCommand("get\r\n")
    }

    private fun updateDeviceStatus(newStatus: DeviceStatus) {
        deviceUiState.value = deviceUiState.value.copy(
            deviceStatus = deviceUiState.value.deviceStatus.plus(newStatus)
        )
    }

    private fun clearDeviceStatus() {
        deviceUiState.value = deviceUiState.value.copy(
            deviceStatus = emptySet()
        )
    }

    private fun subscribeOnDeviceLog() {
        viewModelScope.launch {
            connectionManager.deviceLogFlow.collect { massage ->
                when {
                    massage == SET_COMPLETE -> {
                        updateDeviceStatus(DeviceStatus.Checking)
                        delay(500)
                        sendGetCommand()
                    }

                    massage.contains(generateControlString()) && !massage.contains("set") -> {
                        updateDeviceStatus(DeviceStatus.Complete)
                    }
                }

                if (massage == SET_COMPLETE) {
                    sendGetCommand()
                }

            }

        }
    }

    private fun subscribeOnErrors() {
        viewModelScope.launch {
            connectionManager.log.collect { massage ->
                massage.find { it is UsbConnectionMassage.Error }?.let {
                    updateDeviceStatus(DeviceStatus.Error(it.text))
                }

            }
        }
    }

    private fun checkLogForStatus(log: List<String>) {
        when {
            log.contains(SET_COMPLETE) && !deviceUiState.value.deviceStatus.contains(DeviceStatus.Checking) -> {
                sendGetCommand()
                updateDeviceStatus(DeviceStatus.Checking)
            }

            log.contains(GET_COMPLETE) -> {
                if (log.contains(generateControlString())) {
                    updateDeviceStatus(DeviceStatus.Complete)
                } else {
                    updateDeviceStatus(DeviceStatus.Error("Incorrect writing"))
                }
            }
        }
    }

    private fun generateControlString(): String {
        val control = generateDeviceSettings(uiState.value.settingsPreset.settings)
        return "${control.delayTime} ${control.cockingTime} ${control.selfDestructionTime} ${control.minVoltage}"
    }

    private fun subscribeOnConnectionStatus() {
        viewModelScope.launch {
            connectionManager.connection.collect { isConnected ->
                deviceUiState.value =
                    deviceUiState.value.copy(isConnected = isConnected)
            }
        }
    }

    private fun UsbConnectionMassage.toTerminalMassage() = TerminalMassage(
        massage = this.text.filter { it != '\r' && it != '\n' },
        time = this.time,
        category = when (this) {
            is UsbConnectionMassage.Device -> TerminalMassageCategory.DEVICE
            is UsbConnectionMassage.Error -> TerminalMassageCategory.ERROR
            is UsbConnectionMassage.System -> TerminalMassageCategory.SYSTEM
            is UsbConnectionMassage.User -> TerminalMassageCategory.USER
        }
    )

}

data class PresetUiState(
    val isLoading: Boolean = true,
    val settingsPreset: SettingsPreset = SettingsPreset(),
)

data class DeviceConnectionUiState(
    val log: List<TerminalMassage> = emptyList(),
    val deviceStatus: Set<DeviceStatus> = emptySet(),
    val isConnected: Boolean = false,
)

sealed class DeviceStatus() {
    object Writing : DeviceStatus()
    object Checking : DeviceStatus()
    object Complete : DeviceStatus()
    class Error(val massage: String = "") : DeviceStatus()
}

