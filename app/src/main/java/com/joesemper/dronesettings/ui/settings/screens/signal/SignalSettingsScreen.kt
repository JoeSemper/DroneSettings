package com.joesemper.dronesettings.ui.settings.screens.signal

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.joesemper.dronesettings.R
import com.joesemper.dronesettings.ui.settings.CheckboxWithText
import com.joesemper.dronesettings.ui.settings.SettingsDefaultScreenContainer
import com.joesemper.dronesettings.ui.settings.TitleWithSubtitleView
import org.koin.androidx.compose.getViewModel

@Composable
fun SignalSettingsScreen(
    navController: NavController,
    viewModel: SignalViewModel = getViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        viewModel.uiActions.collect { action ->
//            when (action) {
//                SettingsUiAction.Close -> {
//                    navController.navigate(HOME_ROUTE)
//                }
//
//                SettingsUiAction.NavigateBack -> {
//                    navController.navigateUp()
//                }
//
//                is SettingsUiAction.NavigateNext -> {
//                    navController.navigate("$PRESET_ROUTE/${action.argument}")
//                }
//            }
        }
    }

    SettingsDefaultScreenContainer(
        title = stringResource(id = R.string.signal),
        onNavigateBack = { viewModel.onSignalUiEvent(SignalUiEvent.BackButtonClick) },
        onNavigateNext = { viewModel.onSignalUiEvent(SignalUiEvent.NextButtonClick) },
        onTopBarNavigationClick = { viewModel.onSignalUiEvent(SignalUiEvent.CloseClick) }
    ) {
        AnimatedVisibility(
            visible = viewModel.uiState.isLoaded,
            enter = fadeIn()
        ) {
            SignalScreenContent(
                modifier = Modifier
                    .verticalScroll(state = rememberScrollState())
                    .padding(top = 8.dp, bottom = 16.dp)
                    .padding(horizontal = 16.dp)
                    .fillMaxSize(),
                state = viewModel.uiState,
                onUiEvent = { viewModel.onSignalUiEvent(it) }
            )
        }

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
        
        Text(
            modifier = Modifier.padding(top = 16.dp, bottom = 4.dp),
            text = stringResource(R.string.pulse_width)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                value = state.cockingPulseWidthHi,
                onValueChange = { onSignalUiEvent(SignalUiEvent.CockingPulseWidthHiChange(it)) },
                label = { Text(text = stringResource(R.string.high_level)) },
                trailingIcon = {
                    Text(text = stringResource(id = R.string.ms))
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                value = state.cockingPulseWidthLo,
                onValueChange = { onSignalUiEvent(SignalUiEvent.CockingPulseWidthLoChange(it)) },
                label = { Text(text = stringResource(R.string.low_level)) },
                trailingIcon = {
                    Text(text = stringResource(id = R.string.ms))
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
        }

        Text(
            modifier = Modifier.padding(top = 16.dp, bottom = 4.dp),
            text = stringResource(R.string.pulse_amount)
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.cockingPulseAmount,
            onValueChange = { onSignalUiEvent(SignalUiEvent.CockingPulseAmountChange(it)) },
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


        Text(
            modifier = Modifier.padding(top = 16.dp, bottom = 4.dp),
            text = stringResource(R.string.pulse_width)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                value = state.activationPulseWidthHi,
                onValueChange = { onSignalUiEvent(SignalUiEvent.ActivationPulseWidthHiChange(it)) },
                label = { Text(text = stringResource(R.string.high_level)) },
                trailingIcon = {
                    Text(text = stringResource(id = R.string.ms))
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                value = state.activationPulseWidthLo,
                onValueChange = { onSignalUiEvent(SignalUiEvent.ActivationPulseWidthLoChange(it)) },
                label = { Text(text = stringResource(R.string.low_level)) },
                trailingIcon = {
                    Text(text = stringResource(id = R.string.ms))
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
        }

        Text(
            modifier = Modifier.padding(top = 16.dp, bottom = 4.dp),
            text = stringResource(R.string.pulse_amount)
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.activationPulseAmount,
            onValueChange = { onSignalUiEvent(SignalUiEvent.ActivationPulseAmountChange(it)) },
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