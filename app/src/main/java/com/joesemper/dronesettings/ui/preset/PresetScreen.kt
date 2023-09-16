package com.joesemper.dronesettings.ui.preset

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.joesemper.dronesettings.R
import com.joesemper.dronesettings.data.datasource.room.entity.MappingPreset
import com.joesemper.dronesettings.data.datasource.room.entity.SensorsPreset
import com.joesemper.dronesettings.data.datasource.room.entity.SettingsPreset
import com.joesemper.dronesettings.data.datasource.room.entity.SignalPreset
import com.joesemper.dronesettings.data.datasource.room.entity.TimelinePreset
import com.joesemper.dronesettings.ui.HOME_ROUTE
import com.joesemper.dronesettings.ui.settings.TitleWithSubtitleView
import com.joesemper.dronesettings.ui.settings.signal.SignalUiState
import com.joesemper.dronesettings.utils.toText
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PresetScreen(
    navController: NavController,
    viewModel: PresetViewModel = getViewModel()
) {

    val state = remember(viewModel.uiState.value) {
        viewModel.uiState.value
    }

    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = state.settingsPreset.settingsSet.name)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(HOME_ROUTE) }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = null)
                    }
                },
                actions = {
                    if (!state.isLoading) {
                        if (state.settingsPreset.settingsSet.saved) {
                            IconButton(onClick = {
                                Toast.makeText(context, "Edit", Toast.LENGTH_SHORT).show()
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = null
                                )
                            }
                            IconButton(onClick = {
                                Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show()
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = null
                                )
                            }
                        } else {
                            TextButton(onClick = {
                                viewModel.onSavePreset(
                                    newName = state.settingsPreset.settingsSet.name,
                                    newDescription = state.settingsPreset.settingsSet.description
                                )
                                Toast.makeText(context, "Preset saved", Toast.LENGTH_SHORT).show()
                            }) {
                                Text(text = stringResource(R.string.save))
                            }
                        }
                    }

                }
            )
        }
    ) { paddingValues ->
        if (viewModel.uiState.value.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                PresetDataViewContent(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .weight(1f)
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    state = state.settingsPreset
                )
            }

        }
    }
}

@Composable
fun PresetDataViewContent(
    modifier: Modifier = Modifier,
    state: SettingsPreset
) {

    val context = LocalContext.current

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        PresetDescriptionView(
            modifier = Modifier.padding(bottom = 8.dp),
            description = state.settingsSet.description
        )

        TimelinePresetDataView(state = state.timelinePreset)

        Divider(modifier = Modifier.fillMaxWidth())

        SensorsPresetDataView(state = state.sensorsPreset)

        Divider(modifier = Modifier.fillMaxWidth())

        PulsePresetDataView(state = state.mappingPreset)

        Divider(modifier = Modifier.fillMaxWidth())

        SignalPresetDataView(state = state.signalPreset)

        Spacer(modifier = Modifier.height(0.dp))

        WritePresetButtonView(
            onClick = { Toast.makeText(context, "Write is successful", Toast.LENGTH_SHORT).show() }
        )

    }
}

@Composable
fun PresetDescriptionView(
    modifier: Modifier = Modifier,
    description: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = stringResource(R.string.description)
        )
        Text(
            text = description,
            color = Color.Gray
        )
    }
}

@Composable
fun TimelinePresetDataView(
    modifier: Modifier = Modifier,
    state: TimelinePreset
) {
    PresetDataViewDefaultContainer(
        modifier = modifier,
        title = stringResource(id = R.string.time_line)
    ) {
        ParameterView(
            parameter = stringResource(id = R.string.delay_time),
            value = "${state.delayTimeMin} ${stringResource(id = R.string.min)} " +
                    "${state.delayTimeSec} ${stringResource(id = R.string.sec)}"
        )

        ParameterView(
            parameter = stringResource(id = R.string.activate_cocking_time),
            value = state.cockingTimeEnabled.toText()
        )

        ParameterView(
            parameter = stringResource(id = R.string.cocking_time),
            value = "${state.cockingTimeMin} ${stringResource(id = R.string.min)} " +
                    "${state.cockingTimeSec} ${stringResource(id = R.string.sec)}"
        )

        ParameterView(
            parameter = stringResource(id = R.string.maximum_time),
            value = "${state.selfDestructionTimeMin} ${stringResource(id = R.string.min)} " +
                    "${state.selfDestructionTimeSec} ${stringResource(id = R.string.sec)}"
        )
    }
}

@Composable
fun SensorsPresetDataView(
    modifier: Modifier = Modifier,
    state: SensorsPreset
) {
    PresetDataViewDefaultContainer(
        modifier = modifier,
        title = stringResource(id = R.string.sensors)
    ) {
        ParameterView(
            parameter = stringResource(id = R.string.target_sensor),
            value = "${state.targetDistance} ${stringResource(id = R.string.meters)}"
        )

        ParameterView(
            parameter = stringResource(id = R.string.enable_battery_activation),
            value = state.batteryActivationEnabled.toText()
        )

        ParameterView(
            parameter = stringResource(R.string.minimum_voltage),
            value = "${state.minVoltage} ${stringResource(id = R.string.volts)}"
        )

        ParameterView(
            parameter = stringResource(R.string.enable_overload_activation),
            value = state.overloadActivationEnabled.toText()
        )

        ParameterView(
            parameter = stringResource(R.string.average_acceleration),
            value = "${state.averageAcceleration} ${stringResource(id = R.string.m_s)}"
        )

        ParameterView(
            parameter = stringResource(R.string.average_deviation),
            value = state.averageDeviation
        )

        ParameterView(
            parameter = stringResource(R.string.coefficient),
            value = state.deviationCoefficient
        )

        ParameterView(
            parameter = stringResource(R.string.enable_dead_time_activation),
            value = state.deadTimeActivationEnabled.toText()
        )

        ParameterView(
            parameter = stringResource(R.string.dead_time),
            value = "${state.deadTime} ${stringResource(id = R.string.sec)}"
        )
    }
}

@Composable
fun PulsePresetDataView(
    modifier: Modifier = Modifier,
    state: MappingPreset
) {
    PresetDataViewDefaultContainer(
        modifier = modifier,
        title = stringResource(id = R.string.signal_mapping)
    ) {
        ParameterView(
            parameter = stringResource(id = R.string.relay_1),
            value = state.relayOneState.toText()
        )

        ParameterView(
            parameter = stringResource(id = R.string.relay_2),
            value = state.relayTwoState.toText()
        )

        ParameterView(
            parameter = stringResource(id = R.string.pulse_1),
            value = state.pulseOneState.toText()
        )

        ParameterView(
            parameter = stringResource(id = R.string.pulse_2),
            value = state.pulseTwoState.toText()
        )

        ParameterView(
            parameter = stringResource(id = R.string.pulse_3),
            value = state.pulseThreeState.toText()
        )
    }
}

@Composable
fun SignalPresetDataView(
    modifier: Modifier = Modifier,
    state: SignalPreset
) {
    PresetDataViewDefaultContainer(
        modifier = modifier,
        title = stringResource(id = R.string.signal)
    ) {
        ParameterView(
            parameter = "${stringResource(R.string.cocking_pulse_width)} " +
                    stringResource(R.string.high),
            value = "${state.cockingPulseWidthHi} ${stringResource(id = R.string.ms)}"
        )

        ParameterView(
            parameter = "${stringResource(R.string.cocking_pulse_width)} " +
                    stringResource(R.string.low),
            value = "${state.cockingPulseWidthLo} ${stringResource(id = R.string.ms)}"
        )

        ParameterView(
            parameter = stringResource(R.string.cocking_pulse_amount),
            value = state.cockingPulseAmount
        )

        ParameterView(
            parameter = stringResource(R.string.infinite_cocking_pulse_amount),
            value = state.cockingPulseInfiniteEnabled.toText()
        )

        ParameterView(
            parameter = "${stringResource(R.string.activation_pulse_width)} " +
                    stringResource(R.string.high),
            value = "${state.activationPulseWidthHi} ${stringResource(id = R.string.ms)}"
        )

        ParameterView(
            parameter = "${stringResource(R.string.activation_pulse_width)} " +
                    stringResource(R.string.low),
            value = "${state.activationPulseWidthLo} ${stringResource(id = R.string.ms)}"
        )

        ParameterView(
            parameter = stringResource(R.string.activation_pulse_amount),
            value = state.activationPulseAmount
        )

        ParameterView(
            parameter = stringResource(R.string.infinite_activation_pulse_amount),
            value = state.activationPulseInfiniteEnabled.toText()
        )
    }
}

@Composable
fun WritePresetButtonView(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onClick
        ) {
            Text(text = stringResource(R.string.write_to_controller))
        }
    }
}

