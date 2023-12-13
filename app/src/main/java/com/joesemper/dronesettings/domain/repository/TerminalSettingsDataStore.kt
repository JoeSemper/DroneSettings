package com.joesemper.dronesettings.domain.repository

import kotlinx.coroutines.flow.Flow

interface TerminalSettingsDataStore {
    suspend fun setShouldHideUserMassages(hide: Boolean)
    fun getShouldHideUserCommands(): Flow<Boolean>
    suspend fun setShouldAddStringEndSymbol(add: Boolean)
    fun getShouldAddStringEndSymbol(): Flow<Boolean>
}