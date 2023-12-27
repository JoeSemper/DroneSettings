package com.joesemper.dronesettings.domain.repository

import com.joesemper.dronesettings.data.datasource.datastore.TerminalSettings
import kotlinx.coroutines.flow.Flow

interface TerminalSettingsDataStore {
    suspend fun setShouldHideUserMassages(hide: Boolean)
    fun getShouldHideUserCommands(): Flow<Boolean>
    suspend fun setShouldAddStringEndSymbol(add: Boolean)
    fun getShouldAddStringEndSymbol(): Flow<Boolean>
    fun getTerminalSettings(): Flow<TerminalSettings>
}
