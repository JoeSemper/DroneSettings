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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.getViewModel

@Composable
fun TerminalScreen(
    viewModel: TerminalViewModel = getViewModel()
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->

        TerminalContentScreen(
            modifier = Modifier.padding(paddingValues),
            uiState = viewModel.uiState,
            onNewUserMassage = { viewModel.sendUserMassage(it) },
            connect = { viewModel.connect() }
        )

    }
}

@Composable
fun TerminalContentScreen(
    modifier: Modifier = Modifier,
    uiState: TerminalUiState,
    onNewUserMassage: (String) -> Unit,
    connect: () -> Unit
) {
    val log = uiState.log
    val isConnected = uiState.isConnected

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
                    color = if (isConnected) Color.Green else Color.Gray,
                    content = {}
                )
            }

            Button(onClick = {
                connect()
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
                enabled = isConnected,
                value = textField,
                onValueChange = { textField = it }
            )

            Button(
                onClick = {
                    onNewUserMassage(textField)
                    textField = ""
                },
                enabled = isConnected
            ) {
                Text(text = "Send")
            }
        }
    }
}



