@file:OptIn(ExperimentalMaterial3Api::class)

package com.joesemper.dronesettings.ui.settings.sensors

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.joesemper.dronesettings.R
import com.joesemper.dronesettings.data.constants.SettingsConstants
import com.joesemper.dronesettings.ui.settings.CheckboxWithText
import com.joesemper.dronesettings.ui.settings.TitleWithSubtitleView

@Composable
fun SensorsSettingsScreen(
    modifier: Modifier = Modifier,
    state: SensorsUiState,
    onUiEvent: (SensorsUiEvent) -> Unit
) {
    Column(
        modifier = modifier
            .verticalScroll(state = rememberScrollState())
            .padding(horizontal = 16.dp)
            .fillMaxSize()
            .padding(bottom = 64.dp)
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
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Slider(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                value = state.targetDistance,
                steps = sliderSteps,
                valueRange = sliderMinValue..sliderMaxValue,
                onValueChange = { onUiEvent(SensorsUiEvent.TargetDistanceChange(it)) }
            )

            OutlinedTextField(
                modifier = Modifier
                    .width(96.dp)
                    .padding(start = 8.dp),
                value = state.targetDistance.toString(),
                onValueChange = { },
                singleLine = true,
                readOnly = true,
                label = { Text(text = stringResource(R.string.meters)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
        }

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

        Row(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                modifier = Modifier.size(32.dp),
                painter = painterResource(id = R.drawable.battery),
                contentDescription = null
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                value = state.minVoltage,
                onValueChange = { onUiEvent(SensorsUiEvent.MinVoltageChange(it)) },
                singleLine = true,
                label = { Text(text = stringResource(R.string.volts)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
        }
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
        modifier = modifier.padding(vertical = 16.dp)
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

        CheckboxWithText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            text = stringResource(R.string.enable_dead_time_activation),
            checked = state.isDeadTimeActivationEnabled,
            onCheckedChange = { onUiEvent(SensorsUiEvent.DeadTimeActivationChange(it)) }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                modifier = Modifier
                    .size(32.dp)
                    .padding(end = 8.dp),
                painter = painterResource(id = R.drawable.chip),
                contentDescription = null
            )
            Column {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = stringResource(R.string.average_acceleration)) },
                    supportingText = { Text(text = stringResource(R.string.noise_deviations_recorded_during_test_flight)) },
                    value = state.averageAcceleration,
                    onValueChange = { onUiEvent(SensorsUiEvent.AverageAccelerationChange(it)) },
                    enabled = state.isOverloadActivationEnabled,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    trailingIcon = {
                        Text(text = stringResource(id = R.string.m_s))
                    }
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    label = { Text(text = stringResource(R.string.average_deviation)) },
                    supportingText = { Text(text = stringResource(R.string.acceleration_recorded_during_test_flight)) },
                    value = state.averageDeviation,
                    onValueChange = { onUiEvent(SensorsUiEvent.AverageDeviationChange(it)) },
                    enabled = state.isOverloadActivationEnabled,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    label = { Text(text = stringResource(R.string.coefficient)) },
                    supportingText = { Text(text = stringResource(R.string.deviation_coefficient)) },
                    value = state.deviationCoefficient,
                    onValueChange = { onUiEvent(SensorsUiEvent.DeviationCoefficientChange(it)) },
                    enabled = state.isOverloadActivationEnabled,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    label = { Text(text = stringResource(R.string.dead_time)) },
                    supportingText = { Text(text = stringResource(R.string.activation_time_on_reduced_overloads)) },
                    value = state.deadTime,
                    onValueChange = { onUiEvent(SensorsUiEvent.DeadTimeChange(it)) },
                    enabled = state.isDeadTimeActivationEnabled,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    trailingIcon = {
                        Text(text = stringResource(id = R.string.sec))
                    }
                )
            }
        }
    }
}

