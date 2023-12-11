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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.joesemper.dronesettings.R
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TerminalScreen(
    viewModel: TerminalViewModel = getViewModel()
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.terminal))
                },
                actions = {
                    ConnectButton(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        isConnected = viewModel.uiState.isConnected,
                        onConnectClick = { viewModel.connect() },
                        onDisconnectClick = { viewModel.disconnect() }
                    )
                }
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

    TerminalBottomSheet(
        uiState = uiState.bottomSheetState,
        onItemCopyClick = {
            uiState.textFieldState.value = uiState.textFieldState.value + "$it "
        }
    )

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
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

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                value = uiState.textFieldState.value,
                onValueChange = { uiState.textFieldState.value = it },
                placeholder = { Text(text = stringResource(id = R.string.command)) },
                leadingIcon = {
                    IconButton(
                        onClick = {
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
                            uiState.textFieldState.value = ""
                        },
                    ) {
                        Icon(imageVector = Icons.Default.Send, contentDescription = null)
                    }
                }
            )

        }
    }
}



