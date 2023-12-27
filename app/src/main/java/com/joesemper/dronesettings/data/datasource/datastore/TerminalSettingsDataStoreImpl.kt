package com.joesemper.dronesettings.data.datasource.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.joesemper.dronesettings.domain.repository.TerminalSettingsDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

private const val TERMINAL_DATASTORE_NAME = "terminal_settings"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = TERMINAL_DATASTORE_NAME)

data class TerminalSettings(
    val shouldHideUserCommands: Boolean = true,
    val shouldAddStringEndSymbol: Boolean = false,
)

class TerminalSettingsDataStoreImpl(private val dataStore: DataStore<Preferences>) : TerminalSettingsDataStore {

    companion object {

        private val HIDE_USER_COMMANDS = booleanPreferencesKey("hide_user_massages")
        private const val DEFAULT_HIDE_USER_COMMANDS = true

        private val ADD_STRING_END_SYMBOL = booleanPreferencesKey("add_string_end_symbol")
        private const val DEFAULT_ADD_STRING_END = false

    }

    override suspend fun setShouldHideUserMassages(hide: Boolean) {
        dataStore.edit { settings ->
            settings[HIDE_USER_COMMANDS] = hide
        }
    }

    override fun getTerminalSettings(): Flow<TerminalSettings> =
        dataStore.data.map { preferences ->
            val hideUserCommands = preferences[HIDE_USER_COMMANDS] ?: DEFAULT_HIDE_USER_COMMANDS
            val addStringEndSymbol = preferences[ADD_STRING_END_SYMBOL] ?: DEFAULT_ADD_STRING_END
            TerminalSettings(hideUserCommands, addStringEndSymbol)
        }


    override fun getShouldHideUserCommands(): Flow<Boolean> =
        dataStore.data.map { preferences ->
            preferences[HIDE_USER_COMMANDS] ?: DEFAULT_HIDE_USER_COMMANDS
        }


    override suspend fun setShouldAddStringEndSymbol(add: Boolean) {
        dataStore.edit { settings ->
            settings[ADD_STRING_END_SYMBOL] = add
        }
    }

    override fun getShouldAddStringEndSymbol(): Flow<Boolean> =
        dataStore.data.catch {

        }.map { preferences ->
            preferences[ADD_STRING_END_SYMBOL] ?: DEFAULT_ADD_STRING_END
        }

}
