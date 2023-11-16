package com.joesemper.dronesettings.ui.terminal

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

class TerminalUiState {
    val hasPermission = mutableStateOf(false)
    val connected = mutableStateOf(false)
    val log = mutableStateListOf<String>()
}