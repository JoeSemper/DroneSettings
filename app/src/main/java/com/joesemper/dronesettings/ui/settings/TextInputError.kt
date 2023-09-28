package com.joesemper.dronesettings.ui.settings

import androidx.compose.runtime.Composable

interface TextInputError {
    @Composable
    fun getErrorMassage(): String
}