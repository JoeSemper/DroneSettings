package com.joesemper.dronesettings.ui.terminal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.joesemper.dronesettings.R
import com.joesemper.dronesettings.utils.copyTextToClipboard
import org.koin.androidx.compose.getViewModel

@Composable
fun TerminalScreen(
    viewModel: TerminalViewModel = getViewModel()
) {

    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        TerminalSettingsDialog(
            onDismiss = { showDialog = false },
            uiState = viewModel.uiState.settings
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TerminalTopBar(
                isConnected = viewModel.uiState.isConnected,
                onConnectClick = { viewModel.connect() },
                onDisconnectClick = { viewModel.disconnect() },
                onClearLogClick = { viewModel.clearLog() },
                onSettingsClick = { showDialog = true },
                onHelpClick = { }
            )
        }
    ) { paddingValues ->

        TerminalContentScreen(
            modifier = Modifier.padding(paddingValues),
            uiState = viewModel.uiState,
            onNewUserMassage = { viewModel.sendUserMassage(it) }
        )

    }

}

@Composable
fun TerminalContentScreen(
    modifier: Modifier = Modifier,
    uiState: TerminalUiState,
    onNewUserMassage: (String) -> Unit,
) {

    val context = LocalContext.current

    TerminalBottomSheet(
        uiState = uiState.bottomSheetState,
        onItemCopyClick = { text ->
            uiState.addTextToTextField(text)
            copyTextToClipboard(context, text)
        }
    )

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.Start,
            reverseLayout = true
        ) {
            items(count = uiState.log.size) {
                Text(
                    modifier = Modifier.padding(vertical = 2.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    text = "${uiState.log[it].time} ${uiState.log[it].prefix()} ${uiState.log[it].massage}",
                    color = uiState.log[it].color()
                )
            }
        }

        val focusRequester = remember { FocusRequester() }
        val focusManager = LocalFocusManager.current

        OutlinedTextField(
            modifier = Modifier
                .focusRequester(focusRequester)
                .fillMaxWidth()
                .padding(8.dp),
            value = uiState.textFieldState.value,
            onValueChange = { uiState.textFieldState.value = it },
            placeholder = { Text(text = stringResource(id = R.string.command)) },
            leadingIcon = {
                IconButton(
                    onClick = {
                        focusManager.clearFocus()
                        uiState.bottomSheetState.showBottomSheet()
                    },
                ) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = null)
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        onNewUserMassage(uiState.textFieldState.value)
                        uiState.clearTextField()
                    },
                ) {
                    Icon(imageVector = Icons.Default.Send, contentDescription = null)
                }
            }
        )

    }

}



