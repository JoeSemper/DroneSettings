package com.joesemper.dronesettings.ui.settings.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.joesemper.dronesettings.R

class TimeFieldState {
    var minutes: String by mutableStateOf("")
    var seconds: String by mutableStateOf("")

    private var isFocusedDirty: Boolean by mutableStateOf(false)
    private var isFocused: Boolean by mutableStateOf(false)

    private var displayErrors: Boolean by mutableStateOf(false)

    val isValid: Boolean
        get() = minutes.isNotBlank() && seconds.isNotBlank()

    fun onFocusChange(focused: Boolean) {
        isFocused = focused
        if (focused) isFocusedDirty = true
    }

    fun enableShowErrors() {
        if (isFocusedDirty) {
            displayErrors = true
        }
    }

    fun showErrors(): Boolean = !isValid && displayErrors

    @Composable
    fun getErrorMassage(): String {
        return if (showErrors()) {
            stringResource(id = R.string.fields_must_not_be_empty)
        } else {
            ""
        }
    }

}