package com.joesemper.dronesettings.ui.preset

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.joesemper.dronesettings.ui.settings.TitleWithSubtitleView

@Composable
fun PresetDataViewDefaultContainer(
    modifier: Modifier = Modifier,
    title: String,
    content: @Composable() (ColumnScope.() -> Unit)
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TitleWithSubtitleView(
            modifier = Modifier.padding(bottom = 8.dp),
            title = title
        )

        content(this)

    }
}

@Composable
fun ParameterView(
    modifier: Modifier = Modifier,
    parameter: String,
    value: String
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            text = parameter,
            color = Color.Gray,
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            modifier = Modifier.padding(start = 64.dp),
            text = value,
            style = MaterialTheme.typography.titleMedium
        )
    }
}