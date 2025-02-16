@file:OptIn(ExperimentalMaterial3Api::class)

package com.joesemper.dronesettings.ui.settings.screens.timeline

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.joesemper.dronesettings.R
import com.joesemper.dronesettings.ui.settings.FloatingFieldEditDialog
import com.joesemper.dronesettings.ui.settings.ParameterCardView
import com.joesemper.dronesettings.ui.settings.SingleFieldEditDialog
import com.joesemper.dronesettings.ui.settings.TimeSelectDialog
import com.joesemper.dronesettings.ui.settings.state.SettingsUiAction
import com.joesemper.dronesettings.ui.settings.state.cockingTimeValidator
import com.joesemper.dronesettings.ui.settings.state.delayTimeValidator
import com.joesemper.dronesettings.ui.settings.state.rememberSingleFieldDialogState
import com.joesemper.dronesettings.ui.settings.state.rememberTimeSelectDialogState
import com.joesemper.dronesettings.ui.settings.state.maximumTimeValidator
import com.joesemper.dronesettings.ui.settings.state.minBatteryVoltageValidator
import com.joesemper.dronesettings.ui.settings.state.rememberFloatingFieldDialogState
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

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.settings))
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    onClick = { onClose() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null
                    )
                    Text(text = stringResource(R.string.cancel))
                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    onClick = { viewModel.onTimelineUiEvent(TimelineUiEvent.NextButtonClick) }
                ) {
                    Text(
                        text = stringResource(R.string.finish)
                    )
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null
                    )

                }
            }
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

        MinBatteryVoltageSettingsView(
            state = state.minBatteryVoltageUiState,
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
                (state.timeLimits.minValue),
                (state.timeLimits.maxValue)
            ),
            onDismiss = { showDialog = false },
            onApply = { min, sec ->
                onUiEvent(
                    TimelineUiEvent.DelayTimeChange(
                        min.toInt().toString(),
                        sec.toInt().toString()
                    )
                )
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
        info = stringResource(R.string.delay_time_description),
        errorMassage = state.getErrorMassage(),
        units = stringResource(R.string.min_sec),
        onClick = { showDialog = true },
        content = {
            if (state.time.minutes.isEmpty()) {
                Text(
                    text = "0 : 00",
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.Gray
                )
            } else {
                Text(
                    text = "${state.time.minutes} : ${state.time.seconds}",
                    style = MaterialTheme.typography.displayMedium
                )
            }
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
                (state.timeLimits.minValue),
                (state.timeLimits.maxValue)
            ),
            onDismiss = { showDialog = false },
            onApply = { min, sec ->
                onUiEvent(
                    TimelineUiEvent.CockingTimeChange(
                        min.toInt().toString(),
                        sec.toInt().toString()
                    )
                )
            },
            state = rememberTimeSelectDialogState(
                initialMinutes = state.time.minutes,
                initialSeconds = state.time.seconds,
                validator = ::cockingTimeValidator,
            )
        )
    }

    ParameterCardView(
        modifier = modifier,
        title = stringResource(id = R.string.cocking_time),
        info = stringResource(R.string.cocking_time_description),
        errorMassage = state.getErrorMassage(),
        units = stringResource(R.string.min_sec),
        onClick = { showDialog = true },
        content = {
            if (state.time.minutes.isEmpty()) {
                Text(
                    text = "0 : 00",
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.Gray
                )
            } else {
                Text(
                    text = "${state.time.minutes} : ${state.time.seconds}",
                    style = MaterialTheme.typography.displayMedium
                )
            }
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

        SingleFieldEditDialog(
            title = stringResource(R.string.maximum_time),
            subtitle = stringResource(
                id = R.string.from_to_minutes,
                (state.timeLimits.minValue),
                (state.timeLimits.maxValue)
            ),
            onDismiss = { showDialog = false },
            onApply = { value ->
                onUiEvent(
                    TimelineUiEvent.SelfDestructionTimeMinutesChange(
                        value.toInt().toString()
                    )
                )
            },
            state = rememberSingleFieldDialogState(
                initialValue = state.minutes,
                validator = ::maximumTimeValidator
            )
        )
    }

    ParameterCardView(
        modifier = modifier,
        title = stringResource(id = R.string.maximum_time),
        info = stringResource(R.string.maximum_time_description),
        errorMassage = state.getErrorMassage(),
        units = stringResource(R.string.min),
        onClick = { showDialog = true },
        content = {
            if (state.minutes.isEmpty()) {
                Text(
                    text = "00",
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.Gray
                )
            } else {
                Text(
                    text = state.minutes,
                    style = MaterialTheme.typography.displayMedium
                )
            }
        }
    )

}

@Composable
fun MinBatteryVoltageSettingsView(
    modifier: Modifier = Modifier,
    state: MinBatteryVoltageUiState,
    onUiEvent: (TimelineUiEvent) -> Unit
) {

    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {

        FloatingFieldEditDialog(
            title = stringResource(R.string.minimum_voltage),
            subtitle = stringResource(
                id = R.string.from_to_volts, state.timeLimits.minValue, state.timeLimits.maxValue,
            ),
            onDismiss = { showDialog = false },
            onApply = { value ->
                onUiEvent(TimelineUiEvent.MinBatteryVoltageChange(value.toFloat().toString()))
            },
            state = rememberFloatingFieldDialogState(
                initialValue = state.voltage,
                validator = ::minBatteryVoltageValidator
            )
        )
    }

    ParameterCardView(
        modifier = modifier,
        title = stringResource(id = R.string.minimum_voltage),
        info = stringResource(R.string.min_battery_description),
        errorMassage = state.getErrorMassage(),
        units = stringResource(R.string.volts),
        onClick = { showDialog = true },
        content = {
            if (state.voltage.isEmpty()) {
                Text(
                    text = "0.0",
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.Gray
                )
            } else {
                Text(
                    text = state.voltage,
                    style = MaterialTheme.typography.displayMedium
                )
            }
        }
    )

}