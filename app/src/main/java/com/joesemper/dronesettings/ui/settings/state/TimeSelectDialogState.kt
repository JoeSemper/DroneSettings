package com.joesemper.dronesettings.ui.settings.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.joesemper.dronesettings.R
import com.joesemper.dronesettings.data.constants.SettingsConstants
import com.joesemper.dronesettings.utils.Constants.Companion.MAX_SECONDS

@Composable
fun rememberTimeSelectDialogState(
    initialMinutes: String = "",
    initialSeconds: String = "",
    validator: (min: String, sec: String) -> ValidationResult = { _, _ -> ValidationResult() }
) = rememberSaveable(saver = TimeSelectDialogStateSaver) {
    TimeSelectDialogState(
        initialMinutes = initialMinutes,
        initialSeconds = initialSeconds,
        validator = validator
    )
}

val TimeSelectDialogStateSaver = run {
    val min = "Minutes"
    val sec = "Seconds"
    val focusMin = "Focus"
    val minWasFocused = "MinFocused"
    val secWasFocused = "SecWasFocused"
    val showErrors = "ShowErrors"

    mapSaver(
        save = {
            mapOf(
                min to it.minutes,
                sec to it.seconds,
                focusMin to it.isMinutesFocused,
                minWasFocused to it.minutesWasFocused,
                secWasFocused to it.secondsWasFocused,
                showErrors to it.showErrors
            )
        },
        restore = {
            TimeSelectDialogState(
                initialMinutes = it[min] as String,
                initialSeconds = it[sec] as String,
                initialFocusMinutes = it[focusMin] as Boolean,
                initialMinutesWasFocused = it[minWasFocused] as Boolean,
                initialSecondsWasFocused = it[secWasFocused] as Boolean,
                initialShowErrors = it[showErrors] as Boolean
            )
        }
    )
}

class TimeSelectDialogState(
    initialMinutes: String = "",
    initialSeconds: String = "",
    initialFocusMinutes: Boolean = true,
    initialMinutesWasFocused: Boolean = false,
    initialSecondsWasFocused: Boolean = false,
    initialShowErrors: Boolean = false,
    private val validator: (min: String, sec: String) -> ValidationResult = { _, _ -> ValidationResult() },
) {
    var minutes by mutableStateOf(initialMinutes)
    var seconds by mutableStateOf(initialSeconds)

    var minutesWasFocused by mutableStateOf(initialFocusMinutes || initialMinutesWasFocused)
        private set
    var secondsWasFocused by mutableStateOf(!initialFocusMinutes || initialSecondsWasFocused)
        private set
    var showErrors by mutableStateOf(initialShowErrors)
        private set

    private var currentFocus: TimeFocus by mutableStateOf(
        if (initialFocusMinutes) TimeFocus.Minutes else TimeFocus.Seconds
    )

    val isMinutesFocused: Boolean
        get() = currentFocus is TimeFocus.Minutes

    val isSecondsFocused: Boolean
        get() = currentFocus is TimeFocus.Seconds

    val isValid: Boolean
        get() = validator(minutes, seconds).isValid

    val isError: Boolean
        get() = !isValid && showErrors

    fun focusMinutes() {
        checkShowErrors()
        currentFocus = TimeFocus.Minutes
        minutesWasFocused = true
    }

    fun focusSeconds() {
        checkShowErrors()
        currentFocus = TimeFocus.Seconds
        secondsWasFocused = true
    }

    fun focusNext() {
        checkShowErrors()
        currentFocus = if (currentFocus is TimeFocus.Minutes) {
            secondsWasFocused = true
            TimeFocus.Seconds
        } else {
            minutesWasFocused = true
            TimeFocus.Minutes
        }
    }

    fun enableShowErrors() {
        showErrors = true
    }

    fun updateValue(newValue: String) {
        when (currentFocus) {
            TimeFocus.Minutes -> {
                if (minutes.toList().size > 1) {
                    minutes = newValue
                } else {
                    minutes += newValue
                }
            }

            TimeFocus.Seconds -> {
                if (seconds.toList().size > 1) {
                    seconds = newValue
                } else {
                    if (seconds.isNotEmpty() && ((seconds + newValue).toInt() > MAX_SECONDS)) {
                        seconds = MAX_SECONDS.toString()
                    } else {
                        seconds += newValue
                    }

                }
            }
        }
    }

    fun clearValue() {
        when (currentFocus) {
            TimeFocus.Minutes -> {
                minutes = ""
            }

            TimeFocus.Seconds -> {
                seconds = ""
            }
        }
    }

    @Composable
    fun getErrorMassage(): String = validator(minutes, seconds).errorMassage()

    private fun checkShowErrors() {
        if (minutesWasFocused && secondsWasFocused) showErrors = true
    }

}

fun delayTimeValidator(min: String, sec: String): ValidationResult {
    return when {
        min.isBlank() || sec.isBlank() -> {
            ValidationResult(
                isValid = false,
                errorMassage = { stringResource(id = R.string.fields_must_not_be_empty) }
            )
        }

        else -> {
            if ((min.toInt() * 60 + sec.toInt()) > SettingsConstants.MAX_TIME_DELAY) {
                ValidationResult(
                    isValid = false,
                    errorMassage = { stringResource(id = R.string.value_is_out_of_range) }
                )
            } else {
                ValidationResult(isValid = true)
            }
        }
    }
}

fun cockingTimeValidator(min: String, sec: String): ValidationResult {
    return when {
        min.isBlank() || sec.isBlank() -> {
            ValidationResult(
                isValid = false,
                errorMassage = { stringResource(id = R.string.fields_must_not_be_empty) }
            )
        }

        else -> {
            if ((min.toInt() * 60 + sec.toInt()) > SettingsConstants.MAX_COCKING_TIME) {
                ValidationResult(
                    isValid = false,
                    errorMassage = { stringResource(id = R.string.value_is_out_of_range) }
                )
            } else {
                ValidationResult(isValid = true)
            }
        }
    }
}

sealed class TimeFocus() {
    object Minutes : TimeFocus()
    object Seconds : TimeFocus()
}

