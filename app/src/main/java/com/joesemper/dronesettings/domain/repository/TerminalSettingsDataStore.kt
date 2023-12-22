package com.joesemper.dronesettings.domain.repository

import com.joesemper.dronesettings.ui.terminal.TerminalsSettings
import kotlinx.coroutines.flow.Flow

interface TerminalSettingsDataStore {
    suspend fun setShouldHideUserMassages(hide: Boolean)
    fun getShouldHideUserCommands(): Flow<Boolean>
    suspend fun setShouldAddStringEndSymbol(add: Boolean)
    fun getShouldAddStringEndSymbol(): Flow<Boolean>
    fun getTerminalSettings(): Flow<TerminalsSettings>
    suspend fun setTest(test: String)
    fun getTest(): Flow<String>
}