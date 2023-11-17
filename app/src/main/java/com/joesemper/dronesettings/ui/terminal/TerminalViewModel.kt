package com.joesemper.dronesettings.ui.terminal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class TerminalViewModel() : ViewModel() {

    var uiState by mutableStateOf(TerminalUiState())
        private set

    fun onNewSystemMassage(massage: String) {
        uiState.log.add(
            TerminalMassage(
                massage = massage,
                category = TerminalMassageCategory.SYSTEM
            )
        )
    }

    fun onNewDeviceMassage(massage: String) {
        uiState.log.add(
            TerminalMassage(
                massage = massage,
                category = TerminalMassageCategory.DEVICE
            )
        )
    }

    fun onNewUserMassage(massage: String) {
        uiState.log.add(
            TerminalMassage(
                massage = massage,
                category = TerminalMassageCategory.USER
            )
        )
    }

    fun onNewErrorMassage(massage: String) {
        uiState.log.add(
            TerminalMassage(
                massage = massage,
                category = TerminalMassageCategory.ERROR
            )
        )
    }

}