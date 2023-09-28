package com.joesemper.dronesettings.ui.settings.timeline.errors

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.joesemper.dronesettings.R
import com.joesemper.dronesettings.ui.settings.TextInputError

sealed class DelayTimeInputError: TextInputError {
    object None: DelayTimeInputError() {

        @Composable
        override fun getErrorMassage(): String {
            return ""
        }

    }

    class EmptyFields(
        val isMinutesError: Boolean = false,
        val isSecondsError: Boolean = false
    ): DelayTimeInputError() {

        @Composable
        override fun getErrorMassage(): String {
            return stringResource(R.string.fields_must_not_be_empty)
        }
    }

}