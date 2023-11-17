package com.joesemper.dronesettings.ui.terminal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.joesemper.dronesettings.usb.UsbConnectionManager
import com.joesemper.dronesettings.usb.UsbConnectionMassage
import com.joesemper.dronesettings.usb.rememberUsbConnectionManager
import org.koin.androidx.compose.getViewModel

@Composable
fun TerminalScreen(
    viewModel: TerminalViewModel = getViewModel()
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->


        val context = LocalContext.current
        val scope = rememberCoroutineScope()

        val connectionManager: UsbConnectionManager = rememberUsbConnectionManager(
            context = context,
            coroutineScope = scope
        )

        LaunchedEffect(key1 = context) {
            connectionManager.subscribeOnMassages().collect { massege ->
                when (massege) {
                    is UsbConnectionMassage.Device -> viewModel.onNewDeviceMassage(massege.text)
                    is UsbConnectionMassage.Error -> viewModel.onNewErrorMassage(massege.text)
                    is UsbConnectionMassage.System -> viewModel.onNewSystemMassage(massege.text)
                }
            }
        }

        TerminalContentScreen(
            modifier = Modifier.padding(paddingValues),
            uiState = viewModel.uiState,
            connectionManager = connectionManager,
            onNewUserMassage = { viewModel.onNewUserMassage(it) }
        )

        DisposableEffect(key1 = context) {
            onDispose {
                connectionManager.disconnect()
            }
        }
    }
}

@Composable
fun TerminalContentScreen(
    modifier: Modifier = Modifier,
    uiState: TerminalUiState,
    connectionManager: UsbConnectionManager,
    onNewUserMassage: (String) -> Unit
) {
    val isConnected = connectionManager.connection.collectAsState()
    val log = uiState.log

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Connection ")
                Surface(
                    modifier = Modifier.size(24.dp),
                    shape = CircleShape,
                    color = if (isConnected.value) Color.Green else Color.Gray,
                    content = {}
                )
            }

            Button(onClick = {
                connectionManager.connect()
            }) {
                Text(text = "Connect")
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(1f),
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.Start,
            reverseLayout = true
        ) {
            items(count = log.size) {
                Text(
                    text = "${log[log.lastIndex - it].prefix()} ${log[log.lastIndex - it].massage}",
                    color = log[log.lastIndex - it].color()
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            var textField by remember { mutableStateOf("") }

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                enabled = isConnected.value,
                value = textField,
                onValueChange = { textField = it }
            )

            Button(
                onClick = {
                    connectionManager.send(textField)
                    onNewUserMassage(textField)
                    textField = ""
                },
                enabled = isConnected.value
            ) {
                Text(text = "Send")
            }
        }
    }
}



