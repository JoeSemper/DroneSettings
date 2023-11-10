@file:OptIn(ExperimentalMaterial3Api::class)

package com.joesemper.dronesettings.ui.settings.screens.sensors

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.joesemper.dronesettings.R
import com.joesemper.dronesettings.data.constants.SettingsConstants
import com.joesemper.dronesettings.ui.settings.CheckboxWithText
import com.joesemper.dronesettings.ui.settings.SettingsDefaultScreenContainer
import com.joesemper.dronesettings.ui.settings.TitleWithSubtitleView
import com.joesemper.dronesettings.ui.settings.state.SettingsUiAction
import org.koin.androidx.compose.getViewModel
import kotlin.math.roundToInt

@Composable
fun SensorsSettingsScreen(
    navigateNext: (Int) -> Unit,
    navigateUp: () -> Unit,
    onClose: () -> Unit,
    viewModel: SensorsViewModel = getViewModel()
) {

    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        viewModel.uiActions.collect { action ->
            when (action) {
                SettingsUiAction.Close -> { onClose() }

                SettingsUiAction.NavigateBack -> { navigateUp() }

                is SettingsUiAction.NavigateNext -> {
                    navigateNext(action.argument)
                }
            }
        }
    }

    SettingsDefaultScreenContainer(
        title = stringResource(id = R.string.sensors),
        onNavigateBack = { viewModel.onSensorsUiEvent(SensorsUiEvent.BackButtonClick) },
        onNavigateNext = { viewModel.onSensorsUiEvent(SensorsUiEvent.NextButtonClick) },
        onTopBarNavigationClick = { viewModel.onSensorsUiEvent(SensorsUiEvent.CloseClick) }
    ) {
        AnimatedVisibility(
            visible = viewModel.uiState.isLoaded,
            enter = fadeIn()
        ) {
            SensorsScreenContent(
                modifier = Modifier
                    .verticalScroll(state = rememberScrollState())
                    .padding(top = 8.dp, bottom = 16.dp)
                    .padding(horizontal = 16.dp)
                    .fillMaxSize(),
                state = viewModel.uiState,
                onUiEvent = { viewModel.onSensorsUiEvent(it) }
            )
        }

    }
}

@Composable
fun SensorsScreenContent(
    modifier: Modifier = Modifier,
    state: SensorsUiState,
    onUiEvent: (SensorsUiEvent) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        TargetSensorView(
            state = state,
            onUiEvent = onUiEvent
        )

        Divider(modifier = Modifier.fillMaxWidth())

        BatteryView(
            state = state,
            onUiEvent = onUiEvent
        )

        Divider(modifier = Modifier.fillMaxWidth())

        AccelerometerView(
            state = state,
            onUiEvent = onUiEvent
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TargetSensorView(
    modifier: Modifier = Modifier,
    state: SensorsUiState,
    onUiEvent: (SensorsUiEvent) -> Unit
) {
    Column(
        modifier = modifier.padding(vertical = 16.dp)
    ) {

        val sliderMinValue = rememberSaveable { SettingsConstants.MIN_TARGET_DISTANCE.toFloat() }
        val sliderMaxValue = rememberSaveable { SettingsConstants.MAX_TARGET_DISTANCE.toFloat() }
        val sliderSteps = rememberSaveable { 9 }

        TitleWithSubtitleView(
            title = stringResource(R.string.target_sensor),
            subtitle = stringResource(R.string.activation_distance)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = state.targetDistance.toString(),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = stringResource(id = R.string.meters),
                color = Color.Gray,
                style = MaterialTheme.typography.titleMedium,
            )
        }

        Slider(
            modifier = Modifier
                .fillMaxWidth(),
            value = state.targetDistance,
            steps = sliderSteps,
            valueRange = sliderMinValue..sliderMaxValue,
            onValueChange = {
                onUiEvent(
                    SensorsUiEvent.TargetDistanceChange(
                        (it * 100).roundToInt().toFloat() / 100
                    )
                )
            }
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BatteryView(
    modifier: Modifier = Modifier,
    state: SensorsUiState,
    onUiEvent: (SensorsUiEvent) -> Unit
) {
    Column(
        modifier = modifier.padding(vertical = 16.dp)
    ) {
        TitleWithSubtitleView(
            title = stringResource(R.string.battery),
            subtitle = stringResource(R.string.minimum_activation_voltage)
        )

        CheckboxWithText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            text = stringResource(R.string.enable_battery_activation),
            checked = state.isBatteryActivationEnabled,
            onCheckedChange = { onUiEvent(SensorsUiEvent.BatteryActivationChange(it)) }
        )

        Text(
            modifier = Modifier.padding(top = 16.dp, bottom = 4.dp),
            text = stringResource(R.string.voltage)
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.minVoltage,
            onValueChange = { onUiEvent(SensorsUiEvent.MinVoltageChange(it)) },
            enabled = state.isBatteryActivationEnabled,
            singleLine = true,
            leadingIcon = {
                Icon(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(id = R.drawable.battery),
                    contentDescription = null
                )
            },
            trailingIcon = {
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    text = stringResource(R.string.volts)
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccelerometerView(
    modifier: Modifier = Modifier,
    state: SensorsUiState,
    onUiEvent: (SensorsUiEvent) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        TitleWithSubtitleView(
            title = stringResource(R.string.accelerometer),
            subtitle = stringResource(R.string.overload_and_dead_time_activation)
        )

        CheckboxWithText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            text = stringResource(R.string.enable_overload_activation),
            checked = state.isOverloadActivationEnabled,
            onCheckedChange = { onUiEvent(SensorsUiEvent.OverloadActivationChange(it)) }
        )

        Text(
            modifier = Modifier.padding(top = 16.dp, bottom = 4.dp),
            text = stringResource(R.string.average_acceleration)
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text(text = stringResource(R.string.acceleration_recorded_during_test_flight)) },
            leadingIcon = {
                Icon(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(id = R.drawable.chip),
                    contentDescription = null
                )
            },
            value = state.averageAcceleration,
            onValueChange = { onUiEvent(SensorsUiEvent.AverageAccelerationChange(it)) },
            enabled = state.isOverloadActivationEnabled,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            trailingIcon = { Text(text = stringResource(id = R.string.m_s)) }
        )

        Text(
            modifier = Modifier.padding(top = 16.dp, bottom = 4.dp),
            text = stringResource(R.string.average_deviation)
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text(text = stringResource(R.string.noise_deviations_recorded_during_test_flight)) },
            leadingIcon = {
                Icon(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(id = R.drawable.chip),
                    contentDescription = null
                )
            },
            value = state.averageDeviation,
            onValueChange = { onUiEvent(SensorsUiEvent.AverageDeviationChange(it)) },
            enabled = state.isOverloadActivationEnabled,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        )

        Text(
            modifier = Modifier.padding(top = 16.dp, bottom = 4.dp),
            text = stringResource(R.string.coefficient)
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text(text = stringResource(R.string.deviation_coefficient)) },
            leadingIcon = {
                Icon(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(id = R.drawable.chip),
                    contentDescription = null
                )
            },
            value = state.deviationCoefficient,
            onValueChange = { onUiEvent(SensorsUiEvent.DeviationCoefficientChange(it)) },
            enabled = state.isOverloadActivationEnabled,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        )

        CheckboxWithText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            text = stringResource(R.string.enable_dead_time_activation),
            checked = state.isDeadTimeActivationEnabled,
            onCheckedChange = { onUiEvent(SensorsUiEvent.DeadTimeActivationChange(it)) }
        )

        Text(
            modifier = Modifier.padding(top = 16.dp, bottom = 4.dp),
            text = stringResource(R.string.dead_time)
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text(text = stringResource(R.string.activation_time_on_reduced_overloads)) },
            leadingIcon = {
                Icon(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(id = R.drawable.alarm),
                    contentDescription = null
                )
            },
            value = state.deadTime,
            onValueChange = { onUiEvent(SensorsUiEvent.DeadTimeChange(it)) },
            enabled = state.isDeadTimeActivationEnabled,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            trailingIcon = { Text(text = stringResource(id = R.string.sec)) }
        )

    }
}

