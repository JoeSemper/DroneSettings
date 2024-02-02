package com.joesemper.dronesettings.ui.terminal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.joesemper.dronesettings.R
import com.joesemper.dronesettings.ui.settings.CheckboxWithText
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TerminalTopBar(
    isConnected: Boolean,
    onConnectClick: () -> Unit,
    onDisconnectClick: () -> Unit,
    onClearLogClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onHelpClick: () -> Unit
) {

    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.terminal))
        },
        actions = {
            ConnectButton(
                modifier = Modifier.padding(horizontal = 8.dp),
                isConnected = isConnected,
                onConnectClick = onConnectClick,
                onDisconnectClick = onDisconnectClick
            )

            Box {
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = null
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                        },
                        text = { Text(text = stringResource(R.string.clear_log)) },
                        onClick = {
                            onClearLogClick()
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Settings, contentDescription = null)
                        },
                        text = { Text(text = stringResource(R.string.settings)) },
                        onClick = {
                            onSettingsClick()
                            expanded = false
                        }
                    )
                    Divider()
                    DropdownMenuItem(
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Info, contentDescription = null)
                        },
                        text = { Text(text = stringResource(R.string.help)) },
                        onClick = {
                            onHelpClick()
                            expanded = false
                        }
                    )

                }
            }

        }
    )
}

@Composable
fun ConnectButton(
    modifier: Modifier = Modifier,
    isConnected: Boolean,
    onConnectClick: () -> Unit,
    onDisconnectClick: () -> Unit
) {
    AssistChip(
        modifier = modifier,
        leadingIcon = {
            Surface(
                modifier = Modifier.size(24.dp),
                shape = CircleShape,
                color = if (isConnected) Color.Green else Color.Gray,
                content = {}
            )
        },
        onClick = {
            if (isConnected) {
                onDisconnectClick()
            } else {
                onConnectClick()
            }
        },
        label = {
            if (isConnected) {
                Text(
                    text = "Disconnect"
                )
            } else {
                Text(
                    text = "Connect"
                )

            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TerminalBottomSheet(
    uiState: BottomSheetState,
    onItemCopyClick: (String) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    fun closeBottomSheet() {
        scope.launch { sheetState.hide() }.invokeOnCompletion {
            if (!sheetState.isVisible) {
                uiState.hideBottomSheet()
            }
        }
    }

    if (uiState.shouldShowBottomSheet.value) {
        ModalBottomSheet(
            onDismissRequest = {
                uiState.hideBottomSheet()
            },
            sheetState = sheetState
        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 32.dp, start = 16.dp, end = 16.dp)
            ) {

                item {
                    Text(
                        text = stringResource(R.string.commands),
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                items(count = uiState.commands.size) {

                    BottomSheetItem(
                        title = uiState.commands[it].command ?: "",
                        description = uiState.commands[it].description ?: "",
                        onIconButtonClick = { command ->
                            onItemCopyClick(command)
                            closeBottomSheet()
                        }
                    )

                }

                item {
                    Divider(modifier = Modifier.fillMaxWidth())
                }

                item {
                    Text(
                        text = stringResource(R.string.variables),
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                items(count = uiState.variables.size) {

                    BottomSheetItem(
                        title = uiState.variables[it].name ?: "",
                        description = uiState.variables[it].description ?: "",
                        onIconButtonClick = { variable ->
                            onItemCopyClick(variable)
                            closeBottomSheet()
                        }
                    )

                }

            }
        }
    }
}

@Composable
fun BottomSheetItem(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    onIconButtonClick: (String) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                softWrap = true
            )
        }

        IconButton(onClick = { onIconButtonClick(title) }) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_content_copy_24),
                contentDescription = null
            )
        }
    }
}

@Composable
fun TerminalSettingsDialog(
    uiState: TerminalsSettingsUiState,
    onDismiss: () -> Unit,
    updateSettings: (hideCommands: Boolean, addSymbol: Boolean) -> Unit
) {

    var hideUserCommands by remember { mutableStateOf(uiState.shouldHideUserCommands) }
    var addStringEndSymbolToCommands by remember { mutableStateOf(uiState.shouldAddStringEndSymbol) }

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Card {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.settings),
                    style = MaterialTheme.typography.titleLarge
                )

                CheckboxWithText(
                    text = stringResource(R.string.hide_user_commands_in_log),
                    checked = hideUserCommands.value,
                    onCheckedChange = { hideUserCommands.value = it }
                )

                CheckboxWithText(
                    text = stringResource(R.string.add_string_end_symbol_to_commands),
                    checked = addStringEndSymbolToCommands.value,
                    onCheckedChange = { addStringEndSymbolToCommands.value = it }
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(text = stringResource(id = R.string.close))
                    }

                    Button(onClick = {
                        updateSettings(
                            hideUserCommands.value,
                            addStringEndSymbolToCommands.value
                        )
                        onDismiss()
                    }) {
                        Text(text = stringResource(id = R.string.apply))
                    }
                }

            }
        }
    }

}