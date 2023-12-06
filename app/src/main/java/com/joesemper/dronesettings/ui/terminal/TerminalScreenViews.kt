package com.joesemper.dronesettings.ui.terminal

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

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