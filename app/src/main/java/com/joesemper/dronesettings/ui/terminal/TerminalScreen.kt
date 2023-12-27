package com.joesemper.dronesettings.ui.terminal

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.joesemper.dronesettings.ui.settings.TerminalTextInputView
import com.joesemper.dronesettings.utils.copyTextToClipboard
import kotlinx.coroutines.flow.consumeAsFlow
import org.koin.androidx.compose.getViewModel

@Composable
fun TerminalScreen() {
    val viewModel: TerminalViewModel = getViewModel()
    val uiState = viewModel._uiState.collectAsStateWithLifecycle()


    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    if (showDialog) {
        TerminalSettingsDialog(
            uiState = uiState.value.settings,
            onDismiss = { showDialog = false },
            updateSettings = { hideCommands, addSymbols ->
                viewModel.updateHideUserCommands(hideCommands)
                viewModel.updateAddStringEndSymbol(addSymbols)
            }
        )
    }

    LaunchedEffect(key1 = context) {
        viewModel.channel.consumeAsFlow().collect {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TerminalTopBar(
                isConnected = uiState.value.isConnected,
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
            uiState = uiState.value,
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

        TerminalTextInputView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            textFieldState = uiState.textFieldState.value,
            updateText = { uiState.textFieldState.value = it },
            onMenuClick = { uiState.bottomSheetState.showBottomSheet() },
            onSendClick = {
                onNewUserMassage(uiState.textFieldState.value)
                uiState.clearTextField()
            }
        )
    }

}



