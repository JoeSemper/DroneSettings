package com.joesemper.dronesettings.ui.settings.entity.errors

import androidx.compose.runtime.Composable

interface UiTextInputError {
    @Composable
    fun getErrorMassage(): String
}