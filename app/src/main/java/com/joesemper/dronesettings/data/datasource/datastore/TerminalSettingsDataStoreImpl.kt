package com.joesemper.dronesettings.data.datasource.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.joesemper.dronesettings.domain.repository.TerminalSettingsDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val TERMINAL_DATASTORE_NAME = "terminal_settings"

class TerminalSettingsDataStoreImpl(private val context: Context) : TerminalSettingsDataStore {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = TERMINAL_DATASTORE_NAME)

        private val HIDE_USER_MASSAGES = stringPreferencesKey("hide_user_massages")
        private const val DEFAULT_HIDE_USER_MASSAGES = false

        private val ADD_STRING_END_SYMBOL = stringPreferencesKey("add_string_end_symbol")
        private const val DEFAULT_ADD_STRING_END = true
    }

    override suspend fun setShouldHideUserMassages(hide: Boolean) {
        context.dataStore.edit { settings ->
            settings[HIDE_USER_MASSAGES] = hide.toString()
        }
    }

    override fun getShouldHideUserCommands(): Flow<Boolean> =
        context.dataStore.data.map { preferences ->
            preferences[HIDE_USER_MASSAGES]?.toBoolean() ?: DEFAULT_HIDE_USER_MASSAGES
        }


    override suspend fun setShouldAddStringEndSymbol(add: Boolean) {
        context.dataStore.edit { settings ->
            settings[ADD_STRING_END_SYMBOL] = add.toString()
        }
    }

    override fun getShouldAddStringEndSymbol(): Flow<Boolean> =
        context.dataStore.data.map { preferences ->
            preferences[ADD_STRING_END_SYMBOL]?.toBoolean() ?: DEFAULT_ADD_STRING_END
        }

}
