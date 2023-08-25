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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
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
import com.joesemper.dronesettings.ui.settings.TitleWithSubtitleView

@Composable
fun TimeLineSettingsScreen(
    modifier: Modifier = Modifier,
    state: TimelineScreenState,
    onInputDelayMinutes: (String) -> Unit,
    onInputDelaySeconds: (String) -> Unit,
    onInputCockingMinutes: (String) -> Unit,
    onInputCockingSeconds: (String) -> Unit,
    onActivateCockingTimeChange: (Boolean) -> Unit,
    onInputSelfDestructionTimeMinutes: (String) -> Unit,
    onInputSelfDestructionTimeSeconds: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .verticalScroll(state = rememberScrollState())
            .padding(horizontal = 16.dp)
            .fillMaxSize()
            .padding(bottom = 64.dp)
    ) {
        DelayTimeSettingsView(
            state = state,
            onInputMinutes = onInputDelayMinutes,
            onInputSeconds = onInputDelaySeconds
        )

        Divider(modifier = Modifier.fillMaxWidth())

        CockingTimeSettingsView(
            state = state,
            onInputMinutes = onInputCockingMinutes,
            onInputSeconds = onInputCockingSeconds,
            onActivateCockingTimeChange = onActivateCockingTimeChange
        )

        Divider(modifier = Modifier.fillMaxWidth())

        MaximumTimeSettingsView(
            state = state,
            onInputMinutes = onInputSelfDestructionTimeMinutes,
            onInputSeconds = onInputSelfDestructionTimeSeconds
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DelayTimeSettingsView(
    modifier: Modifier = Modifier,
    state: TimelineScreenState,
    onInputMinutes: (String) -> Unit,
    onInputSeconds: (String) -> Unit
) {
    Column(
        modifier = modifier.padding(vertical = 16.dp)
    ) {

        TitleWithSubtitleView(
            title = stringResource(R.string.delay_time),
            subtitle = stringResource(R.string.delay_until_all_systems_activation)
        )

        Row(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                modifier = Modifier.size(48.dp),
                imageVector = Icons.Default.Star,
                tint = MaterialTheme.colorScheme.secondary,
                contentDescription = null
            )

            OutlinedTextField(
                modifier = Modifier
                    .width(128.dp)
                    .padding(horizontal = 8.dp),
                value = state.delayTimeMinutes.value,
                onValueChange = { onInputMinutes(it) },
                singleLine = true,
                label = { Text(text = stringResource(R.string.minutes)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            OutlinedTextField(
                modifier = Modifier
                    .width(128.dp)
                    .padding(horizontal = 8.dp),
                value = state.delayTimeSeconds.value,
                onValueChange = { onInputSeconds(it) },
                singleLine = true,
                label = { Text(text = stringResource(R.string.seconds)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
        }

    }
}

@Composable
fun CockingTimeSettingsView(
    modifier: Modifier = Modifier,
    state: TimelineScreenState,
    onInputMinutes: (String) -> Unit,
    onInputSeconds: (String) -> Unit,
    onActivateCockingTimeChange: (Boolean) -> Unit
) {
    Column(
        modifier = modifier.padding(vertical = 16.dp)
    ) {

        TitleWithSubtitleView(
            title = stringResource(R.string.cocking_time),
            subtitle = stringResource(R.string.cocking_time_description)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.fillMaxWidth().weight(1f),
                text = stringResource(R.string.activate_cocking_time)
            )

            Checkbox(
                modifier = modifier.padding(start = 32.dp),
                checked = state.isCockingTimeActivated.value,
                onCheckedChange = onActivateCockingTimeChange
            )
        }

        Row(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
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
                modifier = Modifier
                    .width(128.dp)
                    .padding(horizontal = 8.dp),
                value = state.cockingTimeMinutes.value,
                onValueChange = { onInputMinutes(it) },
                singleLine = true,
                enabled = state.isCockingTimeActivated.value,
                label = { Text(text = stringResource(R.string.minutes)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            OutlinedTextField(
                modifier = Modifier
                    .width(128.dp)
                    .padding(horizontal = 8.dp),
                value = state.cockingTimeSeconds.value,
                onValueChange = { onInputSeconds(it) },
                singleLine = true,
                enabled = state.isCockingTimeActivated.value,
                label = { Text(text = stringResource(R.string.seconds)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
        }
    }
}

@Composable
fun MaximumTimeSettingsView(
    modifier: Modifier = Modifier,
    state: TimelineScreenState,
    onInputMinutes: (String) -> Unit,
    onInputSeconds: (String) -> Unit
) {
    Column(
        modifier = modifier
            .padding(vertical = 16.dp)
            .padding(bottom = 16.dp)
    ) {

        TitleWithSubtitleView(
            title = stringResource(R.string.maximum_time),
            subtitle = stringResource(R.string.self_destruction_time)
        )

        Row(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                modifier = Modifier.size(48.dp),
                imageVector = Icons.Default.Star,
                tint = MaterialTheme.colorScheme.secondary,
                contentDescription = null
            )

            OutlinedTextField(
                modifier = Modifier
                    .width(128.dp)
                    .padding(horizontal = 8.dp),
                value = state.selfDestructionTimeMinutes.value,
                onValueChange = { onInputMinutes(it) },
                singleLine = true,
                label = { Text(text = stringResource(R.string.minutes)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            OutlinedTextField(
                modifier = Modifier
                    .width(128.dp)
                    .padding(horizontal = 8.dp),
                value = state.selfDestructionTimeSeconds.value,
                onValueChange = { onInputSeconds(it) },
                singleLine = true,
                label = { Text(text = stringResource(R.string.seconds)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
        }

    }
}
