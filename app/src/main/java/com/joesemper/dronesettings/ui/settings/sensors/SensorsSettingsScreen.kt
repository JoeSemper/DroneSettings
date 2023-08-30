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
import com.joesemper.dronesettings.ui.settings.CheckboxWithText
import com.joesemper.dronesettings.ui.settings.SensorsState
import com.joesemper.dronesettings.ui.settings.TitleWithSubtitleView

@Composable
fun SensorsSettingsScreen(
    modifier: Modifier = Modifier,
    state: SensorsState,
    onTargetDistanceChange: (Float) -> Unit,
    onMinVoltageChange: (String) -> Unit,
    onOverloadActivationChange: (Boolean) -> Unit,
    onDeadTimeActivationChange: (Boolean) -> Unit,
    onAccelerationChange: (String) -> Unit,
    onDeviationChange: (String) -> Unit,
    onDeviationCoefficientChange: (String) -> Unit,
    onDeadTimeChange: (String) -> Unit
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
            onTargetDistanceChange = onTargetDistanceChange
        )

        Divider(modifier = Modifier.fillMaxWidth())

        BatteryView(
            state = state,
            onMinVoltageChange = onMinVoltageChange
        )

        Divider(modifier = Modifier.fillMaxWidth())

        AccelerometerView(
            state = state,
            onOverloadActivationChange = onOverloadActivationChange,
            onDeadTimeActivationChange = onDeadTimeActivationChange,
            onAccelerationChange = onAccelerationChange,
            onDeviationChange = onDeviationChange,
            onDeviationCoefficientChange = onDeviationCoefficientChange,
            onDeadTimeChange = onDeadTimeChange
        )
    }
}

@Composable
fun TargetSensorView(
    modifier: Modifier = Modifier,
    state: SensorsState,
    onTargetDistanceChange: (Float) -> Unit
) {
    Column(
        modifier = modifier.padding(vertical = 16.dp)
    ) {

        val sliderMinValue = rememberSaveable { state.constants.minTargetDistance.toFloat() }
        val sliderMaxValue = rememberSaveable { state.constants.maxTargetDistance.toFloat() }
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
                value = state.targetDistance.value,
                steps = sliderSteps,
                valueRange = sliderMinValue..sliderMaxValue,
                onValueChange = onTargetDistanceChange
            )

            OutlinedTextField(
                modifier = Modifier
                    .width(96.dp)
                    .padding(start = 8.dp),
                value = state.targetDistance.value.toString(),
                onValueChange = { },
                singleLine = true,
                readOnly = true,
                label = { Text(text = stringResource(R.string.meters)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
        }

    }
}

@Composable
fun BatteryView(
    modifier: Modifier = Modifier,
    state: SensorsState,
    onMinVoltageChange: (String) -> Unit,
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
                modifier = Modifier.size(48.dp),
                painter = painterResource(id = R.drawable.battery),
                contentDescription = null
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                value = state.minVoltage.value,
                onValueChange = { onMinVoltageChange(it) },
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
    state: SensorsState,
    onOverloadActivationChange: (Boolean) -> Unit,
    onDeadTimeActivationChange: (Boolean) -> Unit,
    onAccelerationChange: (String) -> Unit,
    onDeviationChange: (String) -> Unit,
    onDeviationCoefficientChange: (String) -> Unit,
    onDeadTimeChange: (String) -> Unit
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
            checked = state.isOverloadActivationEnabled.value,
            onCheckedChange = onOverloadActivationChange
        )

        CheckboxWithText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            text = stringResource(R.string.enable_dead_time_activation),
            checked = state.isDeadTimeActivationEnabled.value,
            onCheckedChange = onDeadTimeActivationChange
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
                    .size(48.dp)
                    .padding(end = 8.dp),
                painter = painterResource(id = R.drawable.chip),
                contentDescription = null
            )
            Column {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = stringResource(R.string.average_acceleration)) },
                    supportingText = { Text(text = stringResource(R.string.noise_deviations_recorded_during_test_flight)) },
                    value = state.averageAcceleration.value,
                    onValueChange = { onAccelerationChange(it) },
                    enabled = state.isOverloadActivationEnabled.value,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    trailingIcon = {
                        Text(text = stringResource(id = R.string.m_s))
                    },
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    label = { Text(text = stringResource(R.string.average_deviation)) },
                    supportingText = { Text(text = stringResource(R.string.acceleration_recorded_during_test_flight)) },
                    value = state.averageDeviation.value,
                    onValueChange = { onDeviationChange(it) },
                    enabled = state.isOverloadActivationEnabled.value,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    label = { Text(text = stringResource(R.string.coefficient)) },
                    supportingText = { Text(text = stringResource(R.string.deviation_coefficient)) },
                    value = state.deviationCoefficient.value,
                    onValueChange = { onDeviationCoefficientChange(it) },
                    enabled = state.isOverloadActivationEnabled.value,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    label = { Text(text = stringResource(R.string.dead_time)) },
                    supportingText = { Text(text = stringResource(R.string.activation_time_on_reduced_overloads)) },
                    value = state.deadTime.value,
                    onValueChange = { onDeadTimeChange(it) },
                    enabled = state.isDeadTimeActivationEnabled.value,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    trailingIcon = {
                        Text(text = stringResource(id = R.string.sec))
                    }
                )
            }
        }
    }
}

