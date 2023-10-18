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
import com.joesemper.dronesettings.utils.Constants.Companion.SECONDS_IN_MINUTE

@Composable
fun rememberTimeSelectDialogState(
    initialMinutes: String = "",
    initialSeconds: String = "",
    limitInSeconds: Int
) = rememberSaveable(saver = TimeSelectDialogStateSaver) {
    TimeSelectDialogState(
        initialMinutes = initialMinutes,
        initialSeconds = initialSeconds,
        limitInSeconds = limitInSeconds
    )
}

val TimeSelectDialogStateSaver = run {
    val min = "Minutes"
    val sec = "Seconds"
    val focusMin = "Focus"
    val limit = "Limit"
    mapSaver(
        save = {
            mapOf(
                min to it.minutes,
                sec to it.seconds,
                focusMin to it.isMinutesFocused,
                limit to it.limitInSeconds
            )
        },
        restore = {
            TimeSelectDialogState(
                initialMinutes = it[min] as String,
                initialSeconds = it[sec] as String,
                initialFocusMinutes = it[focusMin] as Boolean,
                limitInSeconds = it[limit] as Int
            )
        }
    )
}

class TimeSelectDialogState(
    initialMinutes: String = "",
    initialSeconds: String = "",
    initialFocusMinutes: Boolean = true,
    val limitInSeconds: Int = SettingsConstants.MAX_TIME_DELAY.toInt()
) {
    var minutes by mutableStateOf(initialMinutes)
    var seconds by mutableStateOf(initialSeconds)

    private var currentFocus: TimeFocus by mutableStateOf(
        if (initialFocusMinutes) TimeFocus.Minutes else TimeFocus.Seconds
    )

    val isMinutesFocused: Boolean
        get() = currentFocus is TimeFocus.Minutes

    val isSecondsFocused: Boolean
        get() = currentFocus is TimeFocus.Seconds

    val isError: Boolean
        get() = checkError()

    fun focusMinutes() {
        currentFocus = TimeFocus.Minutes
    }

    fun focusSeconds() {
        currentFocus = TimeFocus.Seconds
    }

    fun focusNext() {
        currentFocus = if (currentFocus is TimeFocus.Minutes) {
            TimeFocus.Seconds
        } else {
            TimeFocus.Minutes
        }
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
                    if (seconds.isNotBlank() && (seconds.toInt() > MAX_SECONDS)) {
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
    fun getErrorMassage(): String = stringResource(id = R.string.value_is_out_of_range)

    private fun checkError(): Boolean {
        val min = if (minutes.isBlank()) 0 else minutes.toInt()
        val sec = if (seconds.isBlank()) 0 else seconds.toInt()
        return (min * SECONDS_IN_MINUTE + sec > limitInSeconds)
    }
}

sealed class TimeFocus() {
    object Minutes : TimeFocus()
    object Seconds : TimeFocus()
}

