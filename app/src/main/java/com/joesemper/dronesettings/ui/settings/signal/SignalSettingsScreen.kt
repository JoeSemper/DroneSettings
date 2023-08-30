package com.joesemper.dronesettings.ui.settings.signal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.joesemper.dronesettings.R
import com.joesemper.dronesettings.ui.settings.CheckboxWithText
import com.joesemper.dronesettings.ui.settings.SignalSettingsState
import com.joesemper.dronesettings.ui.settings.TitleWithSubtitleView
import com.joesemper.dronesettings.ui.settings.TwoFieldInputText

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

        TwoFieldInputText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textFirst = state.cockingPulseWidthHi.value,
            textSecond = state.cockingPulseWidthLo.value,
            firstTitle = stringResource(R.string.high_level),
            secondTitle = stringResource(R.string.low_level),
            onFirstTextChange = { onCockingPulseWidthHiChange(it) },
            onSecondTextChange = { onCockingPulseWidthLoChange(it) },
            icon = painterResource(id = R.drawable.chart_wave),
            units = stringResource(id = R.string.ms)
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 54.dp, top = 16.dp),
            value = state.cockingPulseAmount.value,
            onValueChange = { onCockingPulseAmountChange(it) },
            label = { Text(text = stringResource(R.string.pulse_amount)) },
            enabled = !state.infiniteCockingPulseRepeat.value,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        CheckboxWithText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            text = stringResource(R.string.infinite_pulse_amount),
            checked = state.infiniteCockingPulseRepeat.value,
            onCheckedChange = onInfiniteCockingPulseChange
        )

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

        TwoFieldInputText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textFirst = state.activationPulseWidthHi.value,
            textSecond = state.activationPulseWidthLo.value,
            firstTitle = stringResource(R.string.high_level),
            secondTitle = stringResource(R.string.low_level),
            onFirstTextChange = { onActivationPulseWidthHiChange(it) },
            onSecondTextChange = { onActivationPulseWidthLoChange(it) },
            icon = painterResource(id = R.drawable.chart_wave),
            units = stringResource(id = R.string.ms)
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 54.dp, top = 16.dp),
            value = state.cockingPulseAmount.value,
            onValueChange = { onActivationPulseAmountChange(it) },
            label = { Text(text = stringResource(R.string.pulse_amount)) },
            enabled = !state.infiniteActivationPulseRepeat.value,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        CheckboxWithText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            text = stringResource(R.string.infinite_pulse_amount),
            checked = state.infiniteActivationPulseRepeat.value,
            onCheckedChange = onInfiniteActivationPulseChange
        )

    }
}