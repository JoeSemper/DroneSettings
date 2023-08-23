@file:OptIn(ExperimentalMaterial3Api::class)

package com.joesemper.dronesettings.ui.settings.timeline

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.joesemper.dronesettings.R
import com.joesemper.dronesettings.ui.settings.TimelineScreenState

@Composable
fun TimeLineSettingsScreen(
    modifier: Modifier = Modifier,
    state: TimelineScreenState,
    onInputDelayMinutes: (String) -> Unit,
    onInputDelaySeconds: (String) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        DelayTimeSettingsView(
            state = state,
            onInputMinutes = onInputDelayMinutes,
            onInputSeconds = onInputDelaySeconds
        )
    }
}

@Composable
fun DelayTimeSettingsView(
    modifier: Modifier = Modifier,
    state: TimelineScreenState,
    onInputMinutes: (String) -> Unit,
    onInputSeconds: (String) -> Unit
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Text(
            modifier = Modifier,
            text = stringResource(R.string.delay_time),
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            modifier = Modifier.padding(vertical = 4.dp),
            text = stringResource(R.string.delay_until_all_systems_activation),
            style = MaterialTheme.typography.titleSmall
        )

        Row(
            modifier = Modifier.padding(vertical = 16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        )
        {
            Icon(
                modifier = Modifier.size(48.dp),
                imageVector = Icons.Default.Star,
                tint = MaterialTheme.colorScheme.secondary,
                contentDescription = null
            )

            OutlinedTextField(
                modifier = Modifier.width(128.dp).padding(horizontal = 8.dp),
                value = state.delayTimeInputMin,
                onValueChange = { onInputMinutes(it) },
                singleLine = true,
                label = { Text(text = stringResource(R.string.minutes))},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            OutlinedTextField(
                modifier = Modifier.width(128.dp).padding(horizontal = 8.dp),
                value = state.delayTimeInputSec,
                onValueChange = { onInputSeconds(it) },
                singleLine = true,
                label = { Text(text = stringResource(R.string.seconds))},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
        }

    }
}