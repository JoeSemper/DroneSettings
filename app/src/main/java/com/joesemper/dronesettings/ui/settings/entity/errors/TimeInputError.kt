package com.joesemper.dronesettings.ui.settings.entity.errors

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.joesemper.dronesettings.R

sealed class TimeInputError (
    val isMinutesError: Boolean,
    val isSecondsError: Boolean
): UiTextInputError {
    object None : TimeInputError(
        isMinutesError = false,
        isSecondsError = false
    ) {
        @Composable
        override fun getErrorMassage(): String {
            return ""
        }
    }

    class EmptyFields(
        isMinutesError: Boolean,
        isSecondsError: Boolean
    ) : TimeInputError(
        isMinutesError = isMinutesError,
        isSecondsError = isSecondsError
    ) {
        @Composable
        override fun getErrorMassage(): String {
            return stringResource(R.string.fields_must_not_be_empty)
        }
    }

}