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

@Composable
fun rememberFloatingFieldDialogState(
    initialValue: String = "",
    validator: (value: String) -> ValidationResult = { _ -> ValidationResult() }
) = rememberSaveable(saver = floatingFieldDialogStateSaver) {
    FloatingFieldDialogState(
        initialValue = initialValue,
        validator = validator
    )
}

val floatingFieldDialogStateSaver = run {
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
            FloatingFieldDialogState(
                initialValue = it[value] as String,
                initialShowErrors = it[showErrors] as Boolean
            )
        }
    )
}

class FloatingFieldDialogState(
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
        if (newValue == ".") {
            if(!value.contains('.') && value.toList().size < 4) {
                value += if (value.toList().isEmpty()) "0." else "."
            } else {
                clearValue()
            }
        } else if (value.toList().size < 5) {
            value += newValue
        } else {
            clearValue()
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

fun minBatteryVoltageValidator(voltage: String): ValidationResult {
    return when {
        voltage.isBlank() -> {
            ValidationResult(
                isValid = false,
                errorMassage = { stringResource(id = R.string.fields_must_not_be_empty) }
            )
        }

        else -> {
            if (voltage.toFloat() > SettingsConstants.MAX_VOLTAGE_VALUE.toFloat()) {
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