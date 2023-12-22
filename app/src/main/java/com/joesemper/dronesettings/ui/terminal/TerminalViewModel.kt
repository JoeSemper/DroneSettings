package com.joesemper.dronesettings.ui.terminal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joesemper.dronesettings.domain.repository.ProtocolRepository
import com.joesemper.dronesettings.domain.repository.TerminalSettingsDataStore
import com.joesemper.dronesettings.usb.UsbConnectionManager
import com.joesemper.dronesettings.usb.UsbConnectionMassage
import com.joesemper.dronesettings.utils.Constants.Companion.COMMAND_STRING_END_SYMBOL
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

class TerminalViewModel(
    private val connectionManager: UsbConnectionManager,
    private val protocolRepository: ProtocolRepository,
    private val settings: TerminalSettingsDataStore
) : ViewModel() {

    var uiState by mutableStateOf(TerminalUiState())
        private set

    val channel = Channel<String>()

    init {
        subscribeOnConnectionLog()
        subscribeOnConnectionStatus()
        getProtocolCommands()
        getSettings()
    }

    fun sendUserMassage(massage: String) {
        if (uiState.settings.shouldAddStringEndSymbol) {
            connectionManager.send(massage.plus(COMMAND_STRING_END_SYMBOL))
        }

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

    fun updateHideUserCommands(hide: Boolean) {
        viewModelScope.launch {
            settings.setShouldHideUserMassages(hide)
        }
    }

    fun updateAddStringEndSymbol(add: Boolean) {
        viewModelScope.launch {
            settings.setShouldAddStringEndSymbol(add)
        }
    }

    private fun getProtocolCommands() {
        viewModelScope.launch {
            uiState = uiState.copy(
                bottomSheetState = uiState.bottomSheetState.copy(
                    commands = protocolRepository.getAllCommands()
                )
            )
            uiState = uiState.copy(
                bottomSheetState = uiState.bottomSheetState.copy(
                    variables = protocolRepository.getAllVariables()
                )
            )
        }
    }

    fun test(text: String) {
        viewModelScope.launch {
            settings.setTest(text)
        }
    }

    private fun getSettings() {
        viewModelScope.launch {
            settings.getTest().collect{

                channel.send(it)

                uiState = uiState.copy(
                    testText = uiState.testText.plus(it)
                )
                uiState = uiState.copy(
                    text = it
                )
            }
        }
//        viewModelScope.launch {
//            settings.getTerminalSettings().collect {
//                uiState = uiState.copy(settings = it)
//            }
//        }
//        viewModelScope.launch {
//            settings.getShouldAddStringEndSymbol().collect {
//                uiState = uiState.copy(
//                    settings = uiState.settings.copy(
//                        shouldAddStringEndSymbol = it
//                    )
//                )
//            }
//        }
//
//        viewModelScope.launch {
//            settings.getShouldHideUserCommands().collect {
//                uiState = uiState.copy(
//                    settings = uiState.settings.copy(
//                        shouldHideUserCommands = it
//                    )
//                )
//            }
//        }
    }

    private fun subscribeOnConnectionLog() {
        viewModelScope.launch {
            connectionManager.log.collect { massages ->
                uiState =
                    uiState.copy(log = massages.map { it.toTerminalMassage() }.reversed().run {
                        if (uiState.settings.shouldHideUserCommands) {
                            filterNot { it.category == TerminalMassageCategory.USER }
                        } else {
                            this
                        }
                    })
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