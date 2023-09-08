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
import androidx.navigation.NavController
import com.joesemper.dronesettings.R
import com.joesemper.dronesettings.ui.settings.CheckboxWithText
import com.joesemper.dronesettings.ui.settings.SettingsDefaultScreenContainer
import com.joesemper.dronesettings.ui.settings.TitleWithSubtitleView
import com.joesemper.dronesettings.ui.settings.TwoFieldInputText
import org.koin.androidx.compose.getViewModel

@Composable
fun SignalSettingsScreen(
    navController: NavController,
    viewModel: SignalViewModel = getViewModel()
) {
    SettingsDefaultScreenContainer(
        title = stringResource(id = R.string.signal),
        onNavigateBack = { navController.navigateUp() },
        onNavigateNext = { }
    ) {
        SignalScreenContent(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .padding(16.dp)
                .fillMaxSize(),
            state = viewModel.uiState,
            onUiEvent = { viewModel.onSignalUiEvent(it) }
        )
    }
}

@Composable
fun SignalScreenContent(
    modifier: Modifier = Modifier,
    state: SignalUiState,
    onUiEvent: (SignalUiEvent) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        CockingSettingsView(
            state = state,
            onSignalUiEvent = onUiEvent,
        )

        Divider(modifier = Modifier.fillMaxWidth())

        ActivationSettingsView(
            state = state,
            onSignalUiEvent = onUiEvent,
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CockingSettingsView(
    modifier: Modifier = Modifier,
    state: SignalUiState,
    onSignalUiEvent: (SignalUiEvent) -> Unit
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
            textFirst = state.cockingPulseWidthHi,
            textSecond = state.cockingPulseWidthLo,
            firstTitle = stringResource(R.string.high_level),
            secondTitle = stringResource(R.string.low_level),
            onFirstTextChange = { onSignalUiEvent(SignalUiEvent.CockingPulseWidthHiChange(it)) },
            onSecondTextChange = { onSignalUiEvent(SignalUiEvent.CockingPulseWidthLoChange(it)) },
            icon = painterResource(id = R.drawable.chart_wave),
            units = stringResource(id = R.string.ms)
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 54.dp, top = 16.dp),
            value = state.cockingPulseAmount,
            onValueChange = { onSignalUiEvent(SignalUiEvent.CockingPulseAmountChange(it)) },
            label = { Text(text = stringResource(R.string.pulse_amount)) },
            enabled = !state.infiniteCockingPulseRepeat,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        CheckboxWithText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            text = stringResource(R.string.infinite_pulse_amount),
            checked = state.infiniteCockingPulseRepeat,
            onCheckedChange = { onSignalUiEvent(SignalUiEvent.InfiniteCockingPulseRepeatChange(it)) }
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivationSettingsView(
    modifier: Modifier = Modifier,
    state: SignalUiState,
    onSignalUiEvent: (SignalUiEvent) -> Unit
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
            textFirst = state.activationPulseWidthHi,
            textSecond = state.activationPulseWidthLo,
            firstTitle = stringResource(R.string.high_level),
            secondTitle = stringResource(R.string.low_level),
            onFirstTextChange = { onSignalUiEvent(SignalUiEvent.ActivationPulseWidthHiChange(it)) },
            onSecondTextChange = { onSignalUiEvent(SignalUiEvent.ActivationPulseWidthLoChange(it)) },
            icon = painterResource(id = R.drawable.chart_wave),
            units = stringResource(id = R.string.ms)
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 54.dp, top = 16.dp),
            value = state.activationPulseAmount,
            onValueChange = { onSignalUiEvent(SignalUiEvent.ActivationPulseAmountChange(it)) },
            label = { Text(text = stringResource(R.string.pulse_amount)) },
            enabled = !state.infiniteActivationPulseRepeat,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        CheckboxWithText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            text = stringResource(R.string.infinite_pulse_amount),
            checked = state.infiniteActivationPulseRepeat,
            onCheckedChange = { onSignalUiEvent(SignalUiEvent.InfiniteActivationPulseRepeatChange(it)) }
        )

    }
}