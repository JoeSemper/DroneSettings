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
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.joesemper.dronesettings.R
import com.joesemper.dronesettings.ui.settings.CheckboxWithText
import com.joesemper.dronesettings.ui.settings.TimelineState
import com.joesemper.dronesettings.ui.settings.TitleWithSubtitleView
import com.joesemper.dronesettings.ui.settings.TwoFieldInputText

@Stable
class TimeLineSettingsScreenState(
    val timelineState: TimelineState,
    val onInputDelayMinutes: (String) -> Unit,
    val onInputDelaySeconds: (String) -> Unit,
    val onInputCockingMinutes: (String) -> Unit,
    val onInputCockingSeconds: (String) -> Unit,
    val onActivateCockingTimeChange: (Boolean) -> Unit,
    val onInputSelfDestructionTimeMinutes: (String) -> Unit,
    val onInputSelfDestructionTimeSeconds: (String) -> Unit
)

@Composable
fun rememberTimeLineSettingsScreenState(
    timelineState: TimelineState,
    onInputDelayMinutes: (String) -> Unit,
    onInputDelaySeconds: (String) -> Unit,
    onInputCockingMinutes: (String) -> Unit,
    onInputCockingSeconds: (String) -> Unit,
    onActivateCockingTimeChange: (Boolean) -> Unit,
    onInputSelfDestructionTimeMinutes: (String) -> Unit,
    onInputSelfDestructionTimeSeconds: (String) -> Unit
): TimeLineSettingsScreenState = remember() {
    TimeLineSettingsScreenState(
        timelineState,
        onInputDelayMinutes,
        onInputDelaySeconds,
        onInputCockingMinutes,
        onInputCockingSeconds,
        onActivateCockingTimeChange,
        onInputSelfDestructionTimeMinutes,
        onInputSelfDestructionTimeSeconds
    )
}

@Composable
fun TimeLineSettingsScreen(
    modifier: Modifier = Modifier,
    state: TimeLineSettingsScreenState
) {
    Column(
        modifier = modifier
            .verticalScroll(state = rememberScrollState())
            .padding(horizontal = 16.dp)
            .fillMaxSize()
            .padding(bottom = 64.dp)
    ) {
        DelayTimeSettingsView(
            state = state.timelineState,
            onInputMinutes = state.onInputDelayMinutes,
            onInputSeconds = state.onInputDelaySeconds
        )

        Divider(modifier = Modifier.fillMaxWidth())

        CockingTimeSettingsView(
            state = state.timelineState,
            onInputMinutes = state.onInputCockingMinutes,
            onInputSeconds = state.onInputCockingSeconds,
            onActivateCockingTimeChange = state.onActivateCockingTimeChange
        )

        Divider(modifier = Modifier.fillMaxWidth())

        MaximumTimeSettingsView(
            state = state.timelineState,
            onInputMinutes = state.onInputSelfDestructionTimeMinutes,
            onInputSeconds = state.onInputSelfDestructionTimeSeconds
        )
    }
}

@Composable
fun DelayTimeSettingsView(
    modifier: Modifier = Modifier,
    state: TimelineState,
    onInputMinutes: (String) -> Unit,
    onInputSeconds: (String) -> Unit
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
            textFirst = state.delayTimeMinutes.value,
            textSecond = state.delayTimeSeconds.value,
            firstTitle = stringResource(id = R.string.minutes),
            secondTitle = stringResource(id = R.string.seconds),
            onFirstTextChange = { onInputMinutes(it) },
            onSecondTextChange = { onInputSeconds(it) },
            icon = painterResource(id = R.drawable.alarm)
        )
    }
}

@Composable
fun CockingTimeSettingsView(
    modifier: Modifier = Modifier,
    state: TimelineState,
    onInputMinutes: (String) -> Unit,
    onInputSeconds: (String) -> Unit,
    onActivateCockingTimeChange: (Boolean) -> Unit
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
            checked = state.isCockingTimeActivated.value,
            onCheckedChange = onActivateCockingTimeChange
        )

        TwoFieldInputText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textFirst = state.cockingTimeMinutes.value,
            textSecond = state.cockingTimeSeconds.value,
            firstTitle = stringResource(id = R.string.minutes),
            secondTitle = stringResource(id = R.string.seconds),
            enabled = state.isCockingTimeActivated.value,
            onFirstTextChange = { onInputMinutes(it) },
            onSecondTextChange = { onInputSeconds(it) },
            icon = painterResource(id = R.drawable.hand_switch)
        )

    }
}

@Composable
fun MaximumTimeSettingsView(
    modifier: Modifier = Modifier,
    state: TimelineState,
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

        TwoFieldInputText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textFirst = state.selfDestructionTimeMinutes.value,
            textSecond = state.selfDestructionTimeSeconds.value,
            firstTitle = stringResource(id = R.string.minutes),
            secondTitle = stringResource(id = R.string.seconds),
            onFirstTextChange = { onInputMinutes(it) },
            onSecondTextChange = { onInputSeconds(it) },
            icon = painterResource(id = R.drawable.hand_switch)
        )

    }
}

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
            modifier = Modifier.size(48.dp),
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
