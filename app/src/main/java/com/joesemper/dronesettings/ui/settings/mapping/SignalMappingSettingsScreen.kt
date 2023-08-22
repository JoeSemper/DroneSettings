package com.joesemper.dronesettings.ui.settings.mapping

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.joesemper.dronesettings.R

@Composable
fun SignalMappingSettingsScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier, contentAlignment = Alignment.Center
    ) {
        Text(text = stringResource(R.string.signal_mapping))
    }
}
