package com.joesemper.dronesettings.ui.settings.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

open class TimeFieldState(
    private val validator: (min: String, sec: String) -> ValidationResult = { _, _ -> ValidationResult() },
) {
    var minutes: String by mutableStateOf("")
    var seconds: String by mutableStateOf("")

    private var isFocusedDirtyMinutes: Boolean by mutableStateOf(false)
    private var isFocusedDirtySeconds: Boolean by mutableStateOf(false)
    private var isFocusedMinutes: Boolean by mutableStateOf(false)
    private var isFocusedSeconds: Boolean by mutableStateOf(false)

    private var displayErrors: Boolean by mutableStateOf(false)

    open val isValid: Boolean
        get() = validator(minutes, seconds).isValid

    fun onMinutesFocusChange(focused: Boolean) {
        isFocusedMinutes = focused
        if (focused) isFocusedDirtyMinutes = true
    }

    fun onSecondsFocusChange(focused: Boolean) {
        isFocusedSeconds = focused
        if (focused) isFocusedDirtySeconds = true
    }

    fun enableShowErrors() {
        if (isFocusedDirtyMinutes && isFocusedDirtySeconds) {
            displayErrors = true
        }
    }

    fun showErrors(): Boolean = !isValid && displayErrors

    @Composable
    open fun getErrorMassage(): String? {
        return if (showErrors()) {
            validator(minutes, seconds).errorMassage()
        } else {
            null
        }
    }

}