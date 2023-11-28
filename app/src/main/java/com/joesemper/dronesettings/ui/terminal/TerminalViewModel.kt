package com.joesemper.dronesettings.ui.terminal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joesemper.dronesettings.usb.UsbConnectionManager
import com.joesemper.dronesettings.usb.UsbConnectionMassage
import kotlinx.coroutines.launch

class TerminalViewModel(
    private val connectionManager: UsbConnectionManager
) : ViewModel() {

    var uiState by mutableStateOf(TerminalUiState())
        private set

    init {
        subscribeOnConnectionLog()
        subscribeOnConnectionStatus()
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

    private fun subscribeOnConnectionLog() {
        viewModelScope.launch {
            connectionManager.log.collect { massages ->
                uiState = uiState.copy(log = massages.map { it.toTerminalMassage() })
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
        category = when (this) {
            is UsbConnectionMassage.Device -> TerminalMassageCategory.DEVICE
            is UsbConnectionMassage.Error -> TerminalMassageCategory.ERROR
            is UsbConnectionMassage.System -> TerminalMassageCategory.SYSTEM
            is UsbConnectionMassage.User -> TerminalMassageCategory.USER
        }
    )
}