package com.joesemper.dronesettings.ui.preset

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.joesemper.dronesettings.R
import com.joesemper.dronesettings.domain.entity.Settings
import com.joesemper.dronesettings.domain.entity.SettingsPreset
import com.joesemper.dronesettings.ui.settings.ErrorText
import com.joesemper.dronesettings.ui.settings.TitleWithSubtitleView
import com.joesemper.dronesettings.ui.terminal.TerminalMassage
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PresetScreen(
    upPress: () -> Unit,
    viewModel: PresetViewModel = getViewModel()
) {

    val state = remember(viewModel.uiState.value) {
        viewModel.uiState.value
    }

    val context = LocalContext.current

    val openSaveDialog = remember { mutableStateOf(false) }
    val openDeleteDialog = remember { mutableStateOf(false) }
    val openDeviceDialog = remember { mutableStateOf(false) }

    if (openSaveDialog.value) {
        val savePresetToastMassage = stringResource(R.string.preset_saved)
        SaveDialogView(
            onDismiss = { openSaveDialog.value = false },
            onApply = { name, description ->
                openSaveDialog.value = false
                viewModel.onSavePreset(
                    newName = name,
                    newDescription = description
                )
                Toast.makeText(context, savePresetToastMassage, Toast.LENGTH_SHORT).show()
            },
            initialName = state.settingsPreset.name,
            initialDescription = state.settingsPreset.description
        )
    }

    val deleteToastMassage = stringResource(R.string.preset_deleted)

    if (openDeleteDialog.value) {
        DeleteDialogView(
            onDismiss = { openDeleteDialog.value = false },
            onApply = {
                openDeleteDialog.value = false
                viewModel.deletePreset()
                Toast.makeText(context, deleteToastMassage, Toast.LENGTH_SHORT).show()
                upPress()
            }
        )
    }

    if (openDeviceDialog.value) {
        DeviceDialog(
            onDismiss = {
                openDeviceDialog.value = false
                viewModel.onDeviceDialogClose()
            },
            title = stringResource(R.string.writing_on_device),
            state = viewModel.deviceUiState.value.deviceStatus,
            log = viewModel.deviceUiState.value.log
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = state.settingsPreset.name)
                },
                navigationIcon = {
                    IconButton(onClick = {
                        upPress()
                    }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = null)
                    }
                },
                actions = {
                    if (!state.isLoading) {
                        if (state.settingsPreset.saved) {
                            IconButton(onClick = {
                                openSaveDialog.value = true
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = null
                                )
                            }

                            IconButton(onClick = {
                                openDeleteDialog.value = true
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = null
                                )
                            }
                        } else {
                            TextButton(onClick = {
                                openSaveDialog.value = true
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
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(24.dp)
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

                if(viewModel.deviceUiState.value.isConnected) {
                    WritePresetButtonView(
                        modifier = Modifier.padding(16.dp),
                        text = stringResource(R.string.write_on_device),
                        onClick = {
                            openDeviceDialog. value = true
                            viewModel.writePresetOnDevice()
                        }
                    )
                } else {
                    WritePresetButtonView(
                        modifier = Modifier.padding(16.dp),
                        text = stringResource(R.string.connect_device),
                        onClick = {
                            viewModel.connect()
                        }
                    )
                }

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
            description = state.description
        )

        SettingsDataView(state = state.settings)

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
fun SettingsDataView(
    modifier: Modifier = Modifier,
    state: Settings
) {
    PresetDataViewDefaultContainer(
        modifier = modifier,
        title = stringResource(id = R.string.settings)
    ) {
        ParameterView(
            parameter = stringResource(id = R.string.delay_time),
            value = "${state.delayTimeMin} ${stringResource(id = R.string.min)} " +
                    "${state.delayTimeSec} ${stringResource(id = R.string.sec)}"
        )

        ParameterView(
            parameter = stringResource(id = R.string.cocking_time),
            value = "${state.cockingTimeMin} ${stringResource(id = R.string.min)} " +
                    "${state.cockingTimeSec} ${stringResource(id = R.string.sec)}"
        )

        ParameterView(
            parameter = stringResource(id = R.string.maximum_time),
            value = "${state.selfDestructionTimeMin} ${stringResource(id = R.string.min)}"
        )

        ParameterView(
            parameter = stringResource(id = R.string.minimum_voltage),
            value = "${state.minBatteryVoltage} ${stringResource(id = R.string.volts)}"
        )
    }
}


@Composable
fun WritePresetButtonView(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onClick() }
        ) {
            Text(text = text)
        }
    }
}

@Composable
fun SaveDialogView(
    onDismiss: () -> Unit,
    onApply: (String, String) -> Unit,
    initialName: String = "",
    initialDescription: String = ""
) {
    val name = remember {
        mutableStateOf(initialName)
    }
    val description = remember {
        mutableStateOf(initialDescription)
    }

    Dialog(onDismissRequest = onDismiss) {
        Card {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TitleWithSubtitleView(title = stringResource(R.string.save_preset))

                OutlinedTextField(
                    value = name.value,
                    onValueChange = { name.value = it },
                    label = {
                        Text(text = stringResource(R.string.preset_name))
                    }
                )

                OutlinedTextField(
                    value = description.value,
                    onValueChange = { description.value = it },
                    label = {
                        Text(text = stringResource(R.string.preset_description))
                    }
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    TextButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        onClick = onDismiss,
                    ) {
                        Text(text = stringResource(R.string.close))
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        onClick = { onApply(name.value, description.value) }
                    ) {
                        Text(text = stringResource(id = R.string.save))
                    }
                }
            }

        }
    }
}

@Composable
fun DeleteDialogView(
    onDismiss: () -> Unit,
    onApply: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TitleWithSubtitleView(title = stringResource(R.string.delete_preset))
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    TextButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        onClick = onDismiss,
                    ) {
                        Text(text = stringResource(R.string.no))
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    TextButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        onClick = onApply
                    ) {
                        Text(text = stringResource(id = R.string.yes))
                    }
                }
            }
        }
    }
}

@Composable
fun DeviceDialog(
    onDismiss: () -> Unit,
    log: List<TerminalMassage>,
    title: String,
    state: Set<DeviceStatus>,
) {
    val context = LocalContext.current
    val toastMassage = stringResource(R.string.incorrect_value)

    Dialog(onDismissRequest = onDismiss) {
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {

                TitleWithSubtitleView(
                    modifier = Modifier.fillMaxWidth(),
                    title = title,
                )

                Spacer(modifier = Modifier.height(32.dp))

                DeviceStatusView(
                    modifier = Modifier.fillMaxWidth(),
                    state = state
                )

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(text = stringResource(R.string.ok))
                    }
                }
            }
        }
    }
}

@Composable
fun DeviceStatusView(
    modifier: Modifier = Modifier,
    state: Set<DeviceStatus>,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {

        DeviceStatusItem(
            text = stringResource(R.string.writing),
            state = when {
                state.contains(DeviceStatus.Checking) -> DeviceStatusItemState.COMPLETE
                state.contains(DeviceStatus.Writing) -> DeviceStatusItemState.LOADING
                else -> DeviceStatusItemState.NEUTRAL
            }
        )

        DeviceStatusItem(
            text = stringResource(R.string.checking),
            state = when {
                state.contains(DeviceStatus.Complete) -> DeviceStatusItemState.COMPLETE
                state.contains(DeviceStatus.Checking)-> DeviceStatusItemState.LOADING
                else -> DeviceStatusItemState.NEUTRAL
            }
        )

        DeviceStatusItem(
            text = stringResource(R.string.complete),
            state = when {
                state.contains(DeviceStatus.Complete) -> DeviceStatusItemState.COMPLETE
                else -> DeviceStatusItemState.NEUTRAL
            }
        )

        state.find { it is DeviceStatus.Error }?.let {
            ErrorText(
                isError = true,
                errorMassage = (it as DeviceStatus.Error).massage
            )
        }

    }
}

@Composable
fun DeviceStatusItem(
    modifier: Modifier = Modifier,
    text: String,
    state: DeviceStatusItemState = DeviceStatusItemState.NEUTRAL
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        when (state) {
            DeviceStatusItemState.NEUTRAL -> {
                Spacer(modifier = Modifier.size(32.dp))
            }

            DeviceStatusItemState.LOADING -> {
                CircularProgressIndicator(
                    modifier = Modifier.size(32.dp)
                )
            }

            DeviceStatusItemState.COMPLETE -> {
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = Icons.Default.Check,
                    tint = Color.Green,
                    contentDescription = null
                )
            }
        }


        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

enum class DeviceStatusItemState {
    NEUTRAL, LOADING, COMPLETE
}
