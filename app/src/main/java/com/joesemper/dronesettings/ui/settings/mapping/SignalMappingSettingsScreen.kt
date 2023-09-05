package com.joesemper.dronesettings.ui.settings.mapping

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.joesemper.dronesettings.R
import com.joesemper.dronesettings.ui.settings.TitleWithSubtitleView

@Composable
fun SignalMappingSettingsScreen(
    modifier: Modifier = Modifier,
    state: MappingUiState,
    onUiEvent: (MappingUiEvent) -> Unit
) {
    Column(
        modifier = modifier
            .verticalScroll(state = rememberScrollState())
            .padding(horizontal = 16.dp)
            .fillMaxSize()
            .padding(bottom = 64.dp)
    ) {
        SignalMappingScreenContent(
            state = state,
            onUiEvent = onUiEvent
        )
    }
}

@Composable
fun SignalMappingScreenContent(
    modifier: Modifier = Modifier,
    state: MappingUiState,
    onUiEvent: (MappingUiEvent) -> Unit
) {
    Column(
        modifier = modifier.padding(vertical = 16.dp)
    ) {

        TitleWithSubtitleView(
            title = stringResource(R.string.relay_1),
        )

        ChipSelectView(
            currentSelected = state.relayOneState,
            onChipClick = { onUiEvent(MappingUiEvent.RelayOneStateChange(it)) }
        )

        TitleWithSubtitleView(
            title = stringResource(R.string.relay_2),
        )

        ChipSelectView(
            currentSelected = state.relayTwoState,
            onChipClick = { onUiEvent(MappingUiEvent.RelayTwoStateChange(it)) }
        )

        TitleWithSubtitleView(
            title = stringResource(R.string.pulse_1),
        )

        ChipSelectView(
            currentSelected = state.pulseOneState,
            onChipClick = { onUiEvent(MappingUiEvent.PulseOneStateChange(it)) }
        )

        TitleWithSubtitleView(
            title = stringResource(R.string.pulse_2),
        )

        ChipSelectView(
            currentSelected = state.pulseTwoState,
            onChipClick = { onUiEvent(MappingUiEvent.PulseTwoStateChange(it)) }
        )

        TitleWithSubtitleView(
            title = stringResource(R.string.pulse_3),
        )

        ChipSelectView(
            currentSelected = state.pulseThreeState,
            onChipClick = { onUiEvent(MappingUiEvent.PulseThreeStateChange(it)) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChipSelectView(
    modifier: Modifier = Modifier,
    currentSelected: MappingSelectOption,
    onChipClick: (MappingSelectOption) -> Unit
) {
    Column(
        modifier = modifier.padding(vertical = 16.dp)
    ) {

        val doneIcon = painterResource(id = R.drawable.baseline_done_24)
        val closeIcon = painterResource(id = R.drawable.baseline_close_24)

        val chips = remember(currentSelected) {
            listOf(
                MappingChip(
                    label = R.string.cocking,
                    icon = doneIcon,
                    selected = MappingSelectOption.COCKING == currentSelected,
                    onClick = { onChipClick(MappingSelectOption.COCKING) }
                ),
                MappingChip(
                    label = R.string.activation,
                    icon = doneIcon,
                    selected = MappingSelectOption.ACTIVATION == currentSelected,
                    onClick = { onChipClick(MappingSelectOption.ACTIVATION) }
                ),
                MappingChip(
                    label = R.string.none,
                    icon = closeIcon,
                    selected = MappingSelectOption.NONE == currentSelected,
                    onClick = { onChipClick(MappingSelectOption.NONE) }
                )
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            chips.forEach { chip ->
                FilterChip(
                    selected = chip.selected,
                    onClick = chip.onClick,
                    label = { Text(text = stringResource(id = chip.label)) },
                    leadingIcon = {
                        chip.icon?.let {
                            Icon(
                                painter = chip.icon,
                                contentDescription = null
                            )
                        }
                    }
                )
            }
        }
    }
}

class MappingChip(
    @StringRes
    val label: Int,
    val icon: Painter? = null,
    val selected: Boolean,
    val onClick: () -> Unit
)