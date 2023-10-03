package com.joesemper.dronesettings.ui.settings.state.errors

import androidx.compose.runtime.Composable

interface UiTextInputError {
    @Composable
    fun getErrorMassage(): String
}