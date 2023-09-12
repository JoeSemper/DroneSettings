@file:OptIn(ExperimentalMaterial3Api::class)

package com.joesemper.dronesettings.ui.settings.timeline

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.joesemper.dronesettings.R
import com.joesemper.dronesettings.ui.HOME_ROUTE
import com.joesemper.dronesettings.ui.SENSORS_ROUTE
import com.joesemper.dronesettings.ui.settings.CheckboxWithText
import com.joesemper.dronesettings.ui.settings.PresetUiAction
import com.joesemper.dronesettings.ui.settings.SettingsDefaultScreenContainer
import com.joesemper.dronesettings.ui.settings.TitleWithSubtitleView
import org.koin.androidx.compose.getViewModel

@Composable
fun TimeLineSettingsScreen(
    navController: NavController,
    viewModel: TimelineViewModel = getViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        viewModel.uiActions.collect { action ->
            when (action) {
                PresetUiAction.Close -> {
                    navController.navigate(HOME_ROUTE)
                }

                PresetUiAction.NavigateBack -> {
                    navController.navigateUp()
                }

                is PresetUiAction.NavigateNext -> {
                    navController.navigate("$SENSORS_ROUTE/${action.argument}")
                }
            }
        }
    }

    SettingsDefaultScreenContainer(
        title = stringResource(id = R.string.time_line),
        onNavigateBack = { viewModel.onTimelineUiEvent(TimelineUiEvent.BackButtonClick) },
        onNavigateNext = { viewModel.onTimelineUiEvent(TimelineUiEvent.NextButtonClick) },
        onTopBarNavigationClick = { viewModel.onTimelineUiEvent(TimelineUiEvent.CloseClick) }
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

@Composable
fun TimelineScreenContent(
    modifier: Modifier = Modifier,
    state: TimelineUiState,
    onUiEvent: (TimelineUiEvent) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        DelayTimeSettingsView(
            state = state,
            onUiEvent = onUiEvent
        )

        Divider(modifier = Modifier.fillMaxWidth())

        CockingTimeSettingsView(
            state = state,
            onUiEvent = onUiEvent
        )

        Divider(modifier = Modifier.fillMaxWidth())

        MaximumTimeSettingsView(
            state = state,
            onUiEvent = onUiEvent
        )
    }
}

@Composable
fun DelayTimeSettingsView(
    modifier: Modifier = Modifier,
    state: TimelineUiState,
    onUiEvent: (TimelineUiEvent) -> Unit
) {
    Column(
        modifier = modifier.padding(vertical = 16.dp)
    ) {

        TitleWithSubtitleView(
            title = stringResource(R.string.delay_time),
            subtitle = stringResource(R.string.delay_until_all_systems_activation),
        )

        TimeInputLayout(
            modifier = Modifier.padding(vertical = 16.dp),
            title = stringResource(R.string.time),
            supportingText = stringResource(R.string.from_0_to_3_minutes),
            minutes = state.delayTimeMinutes,
            seconds = state.delayTimeSeconds,
            onInputMinutes = { onUiEvent(TimelineUiEvent.DelayTimeMinutesChange(it)) },
            onInputSeconds = { onUiEvent(TimelineUiEvent.DelayTimeSecondsChange(it)) },
        )

    }
}

@Composable
fun CockingTimeSettingsView(
    modifier: Modifier = Modifier,
    state: TimelineUiState,
    onUiEvent: (TimelineUiEvent) -> Unit
) {
    Column(
        modifier = modifier.padding(vertical = 16.dp)
    ) {

        TitleWithSubtitleView(
            title = stringResource(R.string.cocking_time),
            subtitle = stringResource(R.string.cocking_time_description),
        )

        CheckboxWithText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            text = stringResource(R.string.activate_cocking_time),
            checked = state.isCockingTimeEnabled,
            onCheckedChange = { onUiEvent(TimelineUiEvent.CockingTimeActivationChange(it)) }
        )

        TimeInputLayout(
            modifier = Modifier.padding(bottom = 16.dp),
            title = stringResource(R.string.time),
            supportingText = stringResource(R.string.from_0_to_3_minutes),
            minutes = state.cockingTimeMinutes,
            seconds = state.cockingTimeSeconds,
            enabled = state.isCockingTimeEnabled,
            onInputMinutes = { onUiEvent(TimelineUiEvent.CockingTimeMinutesChange(it)) },
            onInputSeconds = { onUiEvent(TimelineUiEvent.CockingTimeSecondsChange(it)) },
        )

    }
}

@Composable
fun MaximumTimeSettingsView(
    modifier: Modifier = Modifier,
    state: TimelineUiState,
    onUiEvent: (TimelineUiEvent) -> Unit
) {
    Column(
        modifier = modifier
            .padding(vertical = 16.dp)
            .padding(bottom = 16.dp)
    ) {

        TitleWithSubtitleView(
            title = stringResource(R.string.maximum_time),
            subtitle = stringResource(R.string.self_destruction_time),
        )

        TimeInputLayout(
            modifier = Modifier.padding(vertical = 16.dp),
            title = stringResource(R.string.time),
            supportingText = stringResource(R.string.from_0_to_10_minutes),
            minutes = state.selfDestructionTimeMinutes,
            seconds = state.selfDestructionTimeSeconds,
            onInputMinutes = { onUiEvent(TimelineUiEvent.SelfDestructionTimeMinutesChange(it)) },
            onInputSeconds = { onUiEvent(TimelineUiEvent.SelfDestructionTimeSecondsChange(it)) },
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeInputLayout(
    modifier: Modifier = Modifier,
    title: String? = null,
    supportingText: String? = null,
    minutes: String,
    seconds: String,
    enabled: Boolean = true,
    onInputMinutes: (String) -> Unit,
    onInputSeconds: (String) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    )
    {
        title?.let {
            Text(text = title)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.6f),
                value = minutes,
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End),
                onValueChange = { onInputMinutes(it) },
                singleLine = true,
                enabled = enabled,
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(id = R.drawable.alarm),
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    Text(text = stringResource(R.string.min))
                },
                supportingText = {
                    supportingText?.let { Text(text = supportingText) }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.4f),
                value = seconds,
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End),
                onValueChange = { onInputSeconds(it) },
                singleLine = true,
                enabled = enabled,
                trailingIcon = {
                    Text(text = stringResource(id = R.string.sec))
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
        }

    }
}
