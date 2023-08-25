package com.joesemper.dronesettings.ui.settings.signal

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
import com.joesemper.dronesettings.ui.settings.SignalSettingsState
import com.joesemper.dronesettings.ui.settings.TitleWithSubtitleView

@Composable
fun SignalSettingsScreen(
    modifier: Modifier = Modifier,
    state: SignalSettingsState,
    onCockingPulseWidthHiChange: (String) -> Unit,
    onCockingPulseWidthLoChange: (String) -> Unit,
    onCockingPulseAmountChange: (String) -> Unit,
    onInfiniteCockingPulseChange: (Boolean) -> Unit,
    onActivationPulseWidthHiChange: (String) -> Unit,
    onActivationPulseWidthLoChange: (String) -> Unit,
    onActivationPulseAmountChange: (String) -> Unit,
    onInfiniteActivationPulseChange: (Boolean) -> Unit,
) {
    Column(
        modifier = modifier
            .verticalScroll(state = rememberScrollState())
            .padding(horizontal = 16.dp)
            .fillMaxSize()
            .padding(bottom = 64.dp)
    ) {
        CockingSettingsView(
            state = state,
            onCockingPulseWidthHiChange = onCockingPulseWidthHiChange,
            onCockingPulseWidthLoChange = onCockingPulseWidthLoChange,
            onCockingPulseAmountChange = onCockingPulseAmountChange,
            onInfiniteCockingPulseChange = onInfiniteCockingPulseChange,
        )

        Divider(modifier = Modifier.fillMaxWidth())

        ActivationSettingsView(
            state = state,
            onActivationPulseWidthHiChange = onActivationPulseWidthHiChange,
            onActivationPulseWidthLoChange = onActivationPulseWidthLoChange,
            onActivationPulseAmountChange = onActivationPulseAmountChange,
            onInfiniteActivationPulseChange = onInfiniteActivationPulseChange,
        )


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CockingSettingsView(
    modifier: Modifier = Modifier,
    state: SignalSettingsState,
    onCockingPulseWidthHiChange: (String) -> Unit,
    onCockingPulseWidthLoChange: (String) -> Unit,
    onCockingPulseAmountChange: (String) -> Unit,
    onInfiniteCockingPulseChange: (Boolean) -> Unit,
) {
    Column(
        modifier = modifier.padding(vertical = 16.dp)
    ) {

        TitleWithSubtitleView(
            title = stringResource(R.string.cocking),
            subtitle = stringResource(R.string.cocking_signal_settings)
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
                value = state.cockingPulseWidthHi.value,
                onValueChange = { onCockingPulseWidthHiChange(it) },
                singleLine = true,
                label = { Text(text = stringResource(R.string.ms)) },
                supportingText = { Text(text = stringResource(R.string.high_level)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            OutlinedTextField(
                modifier = Modifier
                    .width(128.dp)
                    .padding(horizontal = 8.dp),
                value = state.cockingPulseWidthLo.value,
                onValueChange = { onCockingPulseWidthLoChange(it) },
                singleLine = true,
                label = { Text(text = stringResource(R.string.ms)) },
                supportingText = { Text(text = stringResource(R.string.low_level)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.pulse_amount),
                )
            }

            OutlinedTextField(
                modifier = Modifier
                    .width(96.dp)
                    .padding(horizontal = 8.dp),
                value = state.cockingPulseAmount.value,
                onValueChange = { onCockingPulseAmountChange(it) },
                enabled = !state.infiniteCockingPulseRepeat.value,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = stringResource(R.string.infinite_pulse_amount)
            )

            Checkbox(
                modifier = modifier.padding(start = 32.dp),
                checked = state.infiniteCockingPulseRepeat.value,
                onCheckedChange = onInfiniteCockingPulseChange
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivationSettingsView(
    modifier: Modifier = Modifier,
    state: SignalSettingsState,
    onActivationPulseWidthHiChange: (String) -> Unit,
    onActivationPulseWidthLoChange: (String) -> Unit,
    onActivationPulseAmountChange: (String) -> Unit,
    onInfiniteActivationPulseChange: (Boolean) -> Unit,
) {
    Column(
        modifier = modifier.padding(vertical = 16.dp)
    ) {

        TitleWithSubtitleView(
            title = stringResource(R.string.activation),
            subtitle = stringResource(R.string.activation_signal_settings)
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
                value = state.activationPulseWidthHi.value,
                onValueChange = { onActivationPulseWidthHiChange(it) },
                singleLine = true,
                label = { Text(text = stringResource(R.string.ms)) },
                supportingText = { Text(text = stringResource(R.string.high_level)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            OutlinedTextField(
                modifier = Modifier
                    .width(128.dp)
                    .padding(horizontal = 8.dp),
                value = state.activationPulseWidthLo.value,
                onValueChange = { onActivationPulseWidthLoChange(it) },
                singleLine = true,
                label = { Text(text = stringResource(R.string.ms)) },
                supportingText = { Text(text = stringResource(R.string.low_level)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.pulse_amount),
                )
            }

            OutlinedTextField(
                modifier = Modifier
                    .width(96.dp)
                    .padding(horizontal = 8.dp),
                value = state.cockingPulseAmount.value,
                onValueChange = { onActivationPulseAmountChange(it) },
                enabled = !state.infiniteActivationPulseRepeat.value,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = stringResource(R.string.infinite_pulse_amount)
            )

            Checkbox(
                modifier = modifier.padding(start = 32.dp),
                checked = state.infiniteActivationPulseRepeat.value,
                onCheckedChange = onInfiniteActivationPulseChange
            )
        }

    }
}