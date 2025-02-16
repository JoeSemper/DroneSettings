package com.joesemper.dronesettings.ui.settings.state.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.joesemper.dronesettings.ui.settings.state.ValidationResult

enum class InputDialogForm {
    TIME, SINGLE_FIELD, FLOATING
}

@Composable
fun rememberInputDialogState(
    initialFirstValue: String = "",
    initialSecondValue: String = "",
    validator: (first: String, second: String) -> ValidationResult = { _, _ -> ValidationResult() },
) = rememberSaveable(saver = inputDialogStateSaver) {
    InputDialogState(
        initialFirstValue = initialFirstValue,
        initialSecondValue = initialSecondValue,
        validator = validator,
    )
}

val inputDialogStateSaver = run {
    val firstValue = "FirstValue"
    val secondValue = "SecondValue"
    val showErrors = "Errors"
    mapSaver(
        save = {
            mapOf(
                firstValue to it.firstValue,
                secondValue to it.secondValue,
                showErrors to it.showErrors
            )
        },
        restore = {
            InputDialogState(
                initialFirstValue = it[firstValue] as String,
                initialSecondValue = it[secondValue] as String,
                initialShowErrors = it[showErrors] as Boolean
            )
        }
    )
}

open class InputDialogState(
    initialFirstValue: String = "",
    initialSecondValue: String = "",
    initialShowErrors: Boolean = false,
    private val validator: (first: String, second: String) -> ValidationResult = { _, _ -> ValidationResult() },
) {
    var firstValue by mutableStateOf(initialFirstValue)
    var secondValue by mutableStateOf(initialSecondValue)

    var showErrors by mutableStateOf(initialShowErrors)
        private set

    val isValid: Boolean
        get() = validator(firstValue, secondValue).isValid

    val isError: Boolean
        get() = !isValid && showErrors

    fun enableShowErrors() {
        showErrors = true
    }

    fun updateValue() { }

    fun clearValue() {
        firstValue = ""
        secondValue = ""
    }

    @Composable
    fun getErrorMassage(): String = validator(firstValue, secondValue).errorMassage()

    private fun checkShowErrors() {
//        if (minutesWasFocused && secondsWasFocused) showErrors = true
    }
}