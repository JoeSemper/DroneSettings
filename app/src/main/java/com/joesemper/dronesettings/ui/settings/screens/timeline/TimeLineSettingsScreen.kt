@file:OptIn(ExperimentalMaterial3Api::class)

package com.joesemper.dronesettings.ui.settings.screens.timeline

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.joesemper.dronesettings.R
import com.joesemper.dronesettings.ui.settings.ParameterCardView
import com.joesemper.dronesettings.ui.settings.SettingsDefaultScreenContainer
import com.joesemper.dronesettings.ui.settings.TimeSelectDialog
import com.joesemper.dronesettings.ui.settings.state.SettingsUiAction
import com.joesemper.dronesettings.ui.settings.state.delayTimeValidator
import com.joesemper.dronesettings.ui.settings.state.rememberTimeSelectDialogState
import com.joesemper.dronesettings.utils.Constants.Companion.SECONDS_IN_MINUTE
import org.koin.androidx.compose.getViewModel

@Composable
fun TimeLineSettingsScreen(
    navigateNext: (Int) -> Unit,
    onClose: () -> Unit,
    viewModel: TimelineViewModel = getViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        viewModel.uiActions.collect { action ->
            when (action) {
                SettingsUiAction.Close -> {
                    onClose()
                }

                SettingsUiAction.NavigateBack -> {}

                is SettingsUiAction.NavigateNext -> {
                    navigateNext(action.argument)
                }
            }
        }
    }

    SettingsDefaultScreenContainer(
        title = stringResource(id = R.string.time_line),
        backButtonEnabled = false,
        onNavigateNext = { viewModel.onTimelineUiEvent(TimelineUiEvent.NextButtonClick) },
        onTopBarNavigationClick = { viewModel.onTimelineUiEvent(TimelineUiEvent.CloseClick) }
    ) {
        AnimatedVisibility(
            visible = viewModel.uiState.isLoaded,
            enter = fadeIn()
        ) {
            TimelineScreenContent(
                modifier = Modifier
                    .verticalScroll(state = rememberScrollState())
                    .padding(top = 8.dp, bottom = 16.dp)
                    .padding(horizontal = 16.dp)
                    .fillMaxSize(),
                state = viewModel.uiState,
                onUiEvent = { viewModel.onTimelineUiEvent(it) }
            )
        }
    }
}

@Composable
fun TimelineScreenContent(
    modifier: Modifier = Modifier,
    state: TimelineUiState,
    onUiEvent: (TimelineUiEvent) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        DelayTimeSettingsView(
            state = state.delayTimeState,
            onUiEvent = onUiEvent
        )

        CockingTimeSettingsView(
            state = state.cockingTimeState,
            onUiEvent = onUiEvent
        )

        MaximumTimeSettingsView(
            state = state.selfDestructionTimeState,
            onUiEvent = onUiEvent
        )
    }
}

@Composable
fun DelayTimeSettingsView(
    modifier: Modifier = Modifier,
    state: DelayTimeUiState,
    onUiEvent: (TimelineUiEvent) -> Unit
) {

    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        TimeSelectDialog(
            title = stringResource(R.string.delay_time),
            subtitle = stringResource(
                id = R.string.from_to_minutes,
                (state.timeLimits.minValue.toInt() / SECONDS_IN_MINUTE),
                (state.timeLimits.maxValue.toInt() / SECONDS_IN_MINUTE)
            ),
            onDismiss = { showDialog = false },
            onApply = { min, sec ->
                onUiEvent(TimelineUiEvent.DelayTimeMinutesChange(min))
                onUiEvent(TimelineUiEvent.DelayTimeSecondsChange(sec))
            },
            state = rememberTimeSelectDialogState(
                initialMinutes = state.time.minutes,
                initialSeconds = state.time.seconds,
                validator = ::delayTimeValidator,
            )
        )
    }

    ParameterCardView(
        modifier = modifier,
        title = stringResource(id = R.string.delay_time),
        onClick = { showDialog = true },
        content = {
            Text(
                text = "${state.time.minutes} : ${state.time.seconds}",
                style = MaterialTheme.typography.displayMedium
            )
        }
    )

}

@Composable
fun CockingTimeSettingsView(
    modifier: Modifier = Modifier,
    state: CockingTimeUiState,
    onUiEvent: (TimelineUiEvent) -> Unit
) {

    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        TimeSelectDialog(
            title = stringResource(R.string.cocking_time),
            subtitle = stringResource(
                id = R.string.from_to_minutes,
                (state.timeLimits.minValue.toInt() / SECONDS_IN_MINUTE),
                (state.timeLimits.maxValue.toInt() / SECONDS_IN_MINUTE)
            ),
            onDismiss = { showDialog = false },
            onApply = { min, sec ->
                onUiEvent(TimelineUiEvent.CockingTimeMinutesChange(min))
                onUiEvent(TimelineUiEvent.CockingTimeSecondsChange(sec))
            },
            state = rememberTimeSelectDialogState(
                initialMinutes = state.time.minutes,
                initialSeconds = state.time.seconds,
                validator = ::delayTimeValidator,
            )
        )
    }

    ParameterCardView(
        modifier = modifier,
        title = stringResource(id = R.string.cocking_time),
        switchable = true,
        enabled = state.enabled,
        onEnabledChange = {onUiEvent(TimelineUiEvent.CockingTimeActivationChange(it))},
        onClick = { showDialog = true },
        content = {
            Text(
                text = "${state.time.minutes} : ${state.time.seconds}",
                style = MaterialTheme.typography.displayMedium
            )
        }
    )

}

@Composable
fun MaximumTimeSettingsView(
    modifier: Modifier = Modifier,
    state: SelfDestructionTimeUiState,
    onUiEvent: (TimelineUiEvent) -> Unit
) {

    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        TimeSelectDialog(
            title = stringResource(R.string.maximum_time),
            subtitle = stringResource(
                id = R.string.from_to_minutes,
                (state.timeLimits.minValue.toInt() / SECONDS_IN_MINUTE),
                (state.timeLimits.maxValue.toInt() / SECONDS_IN_MINUTE)
            ),
            onDismiss = { showDialog = false },
            onApply = { min, sec ->
                onUiEvent(TimelineUiEvent.SelfDestructionTimeMinutesChange(min))
                onUiEvent(TimelineUiEvent.SelfDestructionTimeSecondsChange(sec))
            },
            state = rememberTimeSelectDialogState(
                initialMinutes = state.time.minutes,
                initialSeconds = state.time.seconds,
                validator = ::delayTimeValidator,
            )
        )
    }

    ParameterCardView(
        modifier = modifier,
        title = stringResource(id = R.string.maximum_time),
        onClick = { showDialog = true },
        content = {
            Text(
                text = "${state.time.minutes} : ${state.time.seconds}",
                style = MaterialTheme.typography.displayMedium
            )
        }
    )

}