package com.joesemper.dronesettings.ui.terminal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joesemper.dronesettings.domain.repository.ProtocolRepository
import com.joesemper.dronesettings.domain.repository.TerminalSettingsDataStore
import com.joesemper.dronesettings.usb.UsbConnectionManager
import com.joesemper.dronesettings.usb.UsbConnectionMassage
import com.joesemper.dronesettings.utils.Constants.Companion.COMMAND_STRING_END_SYMBOL
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TerminalViewModel(
    private val connectionManager: UsbConnectionManager,
    private val protocolRepository: ProtocolRepository,
    private val settings: TerminalSettingsDataStore
) : ViewModel() {

    val _uiState = MutableStateFlow(TerminalUiState())
    val uiState = _uiState.asStateFlow()

    val channel = Channel<String>()

    init {
        subscribeOnConnectionLog()
        subscribeOnConnectionStatus()
        getProtocolCommands()
        getSettings()
    }

    fun sendUserMassage(massage: String) {
        connectionManager.send(massage.apply {
            if (_uiState.value.settings.shouldAddStringEndSymbol.value) {
                plus(COMMAND_STRING_END_SYMBOL)
            }
        })
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
            _uiState.value = _uiState.value.copy(
                bottomSheetState = _uiState.value.bottomSheetState.copy(
                    commands = protocolRepository.getAllCommands(),
                    variables = protocolRepository.getAllVariables()
                )
            )
        }
    }

    private fun getSettings() {

        viewModelScope.launch {
            settings.getTerminalSettings().collect {
                _uiState.value.settings.shouldHideUserCommands.value = it.shouldHideUserCommands
                _uiState.value.settings.shouldAddStringEndSymbol.value = it.shouldAddStringEndSymbol
            }
        }
    }

    private fun subscribeOnConnectionLog() {
        viewModelScope.launch {
            connectionManager.log.collect { massages ->
                _uiState.value =
                    _uiState.value.copy(
                        log = massages.map { it.toTerminalMassage() }.reversed().run {
                            if (_uiState.value.settings.shouldHideUserCommands.value) {
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
                _uiState.value = _uiState.value.copy(isConnected = isConnected)
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