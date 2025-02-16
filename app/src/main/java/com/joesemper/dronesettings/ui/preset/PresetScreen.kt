package com.joesemper.dronesettings.ui.preset

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import com.joesemper.dronesettings.data.datasource.room.main.entity.MappingPreset
import com.joesemper.dronesettings.data.datasource.room.main.entity.SensorsPreset
import com.joesemper.dronesettings.data.datasource.room.main.entity.SignalPreset
import com.joesemper.dronesettings.domain.entity.Settings
import com.joesemper.dronesettings.domain.entity.SettingsPreset
import com.joesemper.dronesettings.ui.settings.TitleWithSubtitleView
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

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = state.settingsPreset.name)
                },
                navigationIcon = {
                    IconButton(onClick = {
//                        navController.navigate(HOME_ROUTE)
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
            description = state.description
        )

        SettingsDataView(state = state.settings)

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
                ){
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