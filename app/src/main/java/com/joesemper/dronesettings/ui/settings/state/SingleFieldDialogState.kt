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
import com.joesemper.dronesettings.utils.Constants.Companion.SECONDS_IN_MINUTE

@Composable
fun rememberSingleFieldDialogState(
    initialValue: String = "",
    validator: (value: String) -> ValidationResult = { _ -> ValidationResult() }
) = rememberSaveable(saver = SingleFieldDialogStateSaver) {
    SingleFieldDialogState(
        initialValue = initialValue,
        validator = validator
    )
}

val SingleFieldDialogStateSaver = run {
    val value = "Value"
    val showErrors = "ShowErrors"

    mapSaver(
        save = {
            mapOf(
                value to it.value,
                showErrors to it.showErrors
            )
        },
        restore = {
            SingleFieldDialogState(
                initialValue = it[value] as String,
                initialShowErrors = it[showErrors] as Boolean
            )
        }
    )
}

class SingleFieldDialogState(
    initialValue: String = "",
    initialShowErrors: Boolean = false,
    private val validator: (value: String) -> ValidationResult = { _ -> ValidationResult() },
) {
    var value by mutableStateOf(initialValue)

    var showErrors by mutableStateOf(initialShowErrors)
        private set

    val isValid: Boolean
        get() = validator(value).isValid

    val isError: Boolean
        get() = !isValid && showErrors

    fun enableShowErrors() {
        showErrors = true
    }

    fun updateValue(newValue: String) {
        if (value.toList().size < 4) {
            value += newValue
        } else {
            value = newValue
        }
    }

    fun clearValue() {
        value = ""
    }

    @Composable
    fun getErrorMassage(): String = validator(value).errorMassage()

    private fun checkShowErrors() {
//        if (minutesWasFocused && secondsWasFocused) showErrors = true
    }

}

fun maximumTimeValidator(min: String): ValidationResult {
    return when {
        min.isBlank() -> {
            ValidationResult(
                isValid = false,
                errorMassage = { stringResource(id = R.string.fields_must_not_be_empty) }
            )
        }

        else -> {
            if (min.toInt() > SettingsConstants.MAX_SELF_DESTRUCTION_TIME / SECONDS_IN_MINUTE) {
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

