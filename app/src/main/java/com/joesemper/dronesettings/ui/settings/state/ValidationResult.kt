package com.joesemper.dronesettings.ui.settings.state

import androidx.compose.runtime.Composable

data class ValidationResult(
    val isValid: Boolean = true,
    val errorMassage: @Composable () -> String = { "" }
)