package com.joesemper.dronesettings.domain.use_case.validation

import com.joesemper.dronesettings.ui.settings.state.Limits
import com.joesemper.dronesettings.ui.settings.state.TimeState
import com.joesemper.dronesettings.ui.settings.state.errors.TimeInputError

class ValidateTimeInputUseCase {
    operator fun invoke(time: TimeState, limits: Limits): TimeInputError =
        when {
            time.minutes.isNotBlank() and time.seconds.isNotBlank() -> {
                if ((time.minutes.toInt() * 60 + time.seconds.toInt()) > limits.maxValue) {
                    TimeInputError.None
                } else {
                    TimeInputError.None
                }

            }
            time.minutes.isBlank() or time.seconds.isBlank() -> TimeInputError.EmptyFields(
                isMinutesError = time.minutes.isBlank(),
                isSecondsError = time.seconds.isBlank()
            )
            else -> TimeInputError.None
        }
}
