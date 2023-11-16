package com.joesemper.dronesettings.ui.terminal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class TerminalViewModel(): ViewModel() {

    var uiState by mutableStateOf(TerminalUiState())
        private set

    fun send(msg: String) {

    }

    fun onReceive(msg: String) {

    }

}