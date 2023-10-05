package com.joesemper.dronesettings.ui.settings.screens.timeline.state

import androidx.compose.ui.res.stringResource
import com.joesemper.dronesettings.R
import com.joesemper.dronesettings.data.constants.SettingsConstants
import com.joesemper.dronesettings.ui.settings.state.TimeFieldState
import com.joesemper.dronesettings.ui.settings.state.ValidationResult
import java.util.regex.Pattern

private const val NUMBER_REGEXP = "^\\d+\$"

class DelayTimeFieldState(
    min: String? = null,
    sec: String? = null
) : TimeFieldState(
    validator = ::isTimeValid
) {
    init {
        min?.let { minutes = min }
        sec?.let { seconds = sec }
    }
}

private fun isTimeValid(min: String, sec: String): ValidationResult {
    return when {
        min.isBlank() || sec.isBlank() -> {
            ValidationResult(
                isValid = false,
                errorMassage = { stringResource(id = R.string.fields_must_not_be_empty) }
            )
        }

        !Pattern.matches(NUMBER_REGEXP, min) || !Pattern.matches(NUMBER_REGEXP, sec) -> {
            ValidationResult(
                isValid = false,
                errorMassage = { stringResource(R.string.wrong_format) }
            )
        }

        Pattern.matches(NUMBER_REGEXP, min) && Pattern.matches(NUMBER_REGEXP, sec) -> {
            if ((min.toInt() * 60 + sec.toInt()) > SettingsConstants.MAX_TIME_DELAY) {
                ValidationResult(
                    isValid = false,
                    errorMassage = { stringResource(id = R.string.value_is_out_of_range) }
                )
            } else {
                ValidationResult()
            }
        }

        else -> ValidationResult()
    }
}