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
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.joesemper.dronesettings.R
import com.joesemper.dronesettings.ui.settings.CheckboxWithText
import com.joesemper.dronesettings.ui.settings.TitleWithSubtitleView
import com.joesemper.dronesettings.ui.settings.TwoFieldInputText

@Composable
fun TimeLineSettingsScreen(
    modifier: Modifier = Modifier,
    state: TimelineUiState,
    onUiEvent: (TimelineUiEvent) -> Unit
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
            onUiEvent = onUiEvent
        )

        Divider(modifier = Modifier.fillMaxWidth())

        CockingTimeSettingsView(
            state = state,
            onUiEvent = onUiEvent
        )

        Divider(modifier = Modifier.fillMaxWidth())

        MaximumTimeSettingsView(
            state = state,
            onUiEvent = onUiEvent
        )
    }
}

@Composable
fun DelayTimeSettingsView(
    modifier: Modifier = Modifier,
    state: TimelineUiState,
    onUiEvent: (TimelineUiEvent) -> Unit
) {
    Column(
        modifier = modifier.padding(vertical = 16.dp)
    ) {

        TitleWithSubtitleView(
            title = stringResource(R.string.delay_time),
            subtitle = stringResource(R.string.delay_until_all_systems_activation),
        )

        TwoFieldInputText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textFirst = state.delayTimeMinutes,
            textSecond = state.delayTimeSeconds,
            firstTitle = stringResource(id = R.string.minutes),
            secondTitle = stringResource(id = R.string.seconds),
            onFirstTextChange = { onUiEvent(TimelineUiEvent.DelayTimeMinutesChange(it)) },
            onSecondTextChange = { onUiEvent(TimelineUiEvent.DelayTimeSecondsChange(it)) },
            icon = painterResource(id = R.drawable.alarm)
        )
    }
}

@Composable
fun CockingTimeSettingsView(
    modifier: Modifier = Modifier,
    state: TimelineUiState,
    onUiEvent: (TimelineUiEvent) -> Unit
) {
    Column(
        modifier = modifier.padding(vertical = 16.dp)
    ) {

        TitleWithSubtitleView(
            title = stringResource(R.string.cocking_time),
            subtitle = stringResource(R.string.cocking_time_description),
        )

        CheckboxWithText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            text = stringResource(R.string.activate_cocking_time),
            checked = state.isCockingTimeActivated,
            onCheckedChange = { onUiEvent(TimelineUiEvent.CockingTimeActivationChange(it)) }
        )

        TwoFieldInputText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textFirst = state.cockingTimeMinutes,
            textSecond = state.cockingTimeSeconds,
            firstTitle = stringResource(id = R.string.minutes),
            secondTitle = stringResource(id = R.string.seconds),
            enabled = state.isCockingTimeActivated,
            onFirstTextChange = { onUiEvent(TimelineUiEvent.CockingTimeMinutesChange(it)) },
            onSecondTextChange = {  onUiEvent(TimelineUiEvent.CockingTimeSecondsChange(it)) },
            icon = painterResource(id = R.drawable.hand_switch)
        )

    }
}

@Composable
fun MaximumTimeSettingsView(
    modifier: Modifier = Modifier,
    state: TimelineUiState,
    onUiEvent: (TimelineUiEvent) -> Unit
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

        TwoFieldInputText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textFirst = state.selfDestructionTimeMinutes,
            textSecond = state.selfDestructionTimeSeconds,
            firstTitle = stringResource(id = R.string.minutes),
            secondTitle = stringResource(id = R.string.seconds),
            onFirstTextChange = { onUiEvent(TimelineUiEvent.SelfDestructionTimeMinutesChange(it)) },
            onSecondTextChange = { onUiEvent(TimelineUiEvent.SelfDestructionTimeSecondsChange(it)) },
            icon = painterResource(id = R.drawable.hand_switch)
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeInputLayoutWithIcon(
    modifier: Modifier = Modifier,
    icon: Painter? = null,
    minutes: String,
    seconds: String,
    enabled: Boolean = true,
    onInputMinutes: (String) -> Unit,
    onInputSeconds: (String) -> Unit
) {
    Row(
        modifier = modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            modifier = Modifier.size(32.dp),
            imageVector = Icons.Default.Star,
            tint = MaterialTheme.colorScheme.secondary,
            contentDescription = null
        )

        OutlinedTextField(
            modifier = Modifier
                .width(128.dp)
                .padding(horizontal = 8.dp),
            value = minutes,
            onValueChange = { onInputMinutes(it) },
            singleLine = true,
            enabled = enabled,
            label = { Text(text = stringResource(R.string.minutes)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        OutlinedTextField(
            modifier = Modifier
                .width(128.dp)
                .padding(horizontal = 8.dp),
            value = seconds,
            onValueChange = { onInputSeconds(it) },
            singleLine = true,
            enabled = enabled,
            label = { Text(text = stringResource(R.string.seconds)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )
    }
}
