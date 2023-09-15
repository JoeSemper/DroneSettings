package com.joesemper.dronesettings.ui.preset

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PresetScreen(
    navController: NavController,
    viewModel: PresetViewModel = getViewModel()
) {
    val state = viewModel.uiState.value

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        when (state) {
            PresetUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is PresetUiState.Loaded -> {
                Column(modifier = Modifier.padding(paddingValues)) {
                    Text(text = state.settingsPreset.settingsSet.name)
                    Text(text = state.settingsPreset.timelinePreset.cockingTimeMin)
                    Text(text = state.settingsPreset.sensorsPreset.targetDistance.toString())
                    Text(text = state.settingsPreset.mappingPreset.pulseOneState.toString())
                    Text(text = state.settingsPreset.signalPreset.activationPulseWidthHi)
                }
            }
        }

    }
}