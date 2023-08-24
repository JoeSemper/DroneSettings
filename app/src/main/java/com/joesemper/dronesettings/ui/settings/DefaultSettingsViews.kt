package com.joesemper.dronesettings.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TitleWithSubtitleView(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.size(4.dp))

        Text(
            text = subtitle,
            style = MaterialTheme.typography.titleSmall
        )
    }
}