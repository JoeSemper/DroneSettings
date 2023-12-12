package com.joesemper.dronesettings.ui.terminal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joesemper.dronesettings.domain.repository.ProtocolRepository
import com.joesemper.dronesettings.usb.UsbConnectionManager
import com.joesemper.dronesettings.usb.UsbConnectionMassage
import kotlinx.coroutines.launch

class TerminalViewModel(
    private val connectionManager: UsbConnectionManager,
    private val protocolRepository: ProtocolRepository
) : ViewModel() {

    var uiState by mutableStateOf(TerminalUiState())
        private set

    init {
        subscribeOnConnectionLog()
        subscribeOnConnectionStatus()
        getCommands()
    }

    fun sendUserMassage(massage: String) {
        connectionManager.send(massage)
    }

    fun connect() {
        connectionManager.connect()
    }

    fun disconnect() {
        connectionManager.disconnect()
    }

    fun clearLog() {
        connectionManager.clearLog()
    }

    private fun getCommands() {
        viewModelScope.launch {
            uiState = uiState.copy(bottomSheetState = uiState.bottomSheetState.copy(
                commands = protocolRepository.getAllCommands()
            ))
            uiState = uiState.copy(bottomSheetState = uiState.bottomSheetState.copy(
                variables = protocolRepository.getAllVariables()
            ))
        }
    }

    private fun subscribeOnConnectionLog() {
        viewModelScope.launch {
            connectionManager.log.collect { massages ->
                uiState = uiState.copy(log = massages.map { it.toTerminalMassage() }.reversed())
            }
        }
    }

    private fun subscribeOnConnectionStatus() {
        viewModelScope.launch {
            connectionManager.connection.collect { isConnected ->
                uiState = uiState.copy(isConnected = isConnected)
            }
        }
    }

    private fun UsbConnectionMassage.toTerminalMassage() = TerminalMassage(
        massage = this.text,
        time = this.time,
        category = when (this) {
            is UsbConnectionMassage.Device -> TerminalMassageCategory.DEVICE
            is UsbConnectionMassage.Error -> TerminalMassageCategory.ERROR
            is UsbConnectionMassage.System -> TerminalMassageCategory.SYSTEM
            is UsbConnectionMassage.User -> TerminalMassageCategory.USER
        }
    )
}