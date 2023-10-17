package com.joesemper.dronesettings.ui.settings

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.joesemper.dronesettings.R

@Composable
fun TitleWithSubtitleView(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String? = null,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge
        )

        if (!subtitle.isNullOrBlank()) {
            Spacer(modifier = Modifier.size(2.dp))

            Text(
                text = subtitle,
                color = Color.Gray,
                style = MaterialTheme.typography.titleSmall,
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TwoFieldInputText(
    modifier: Modifier = Modifier,
    textFirst: String,
    textSecond: String,
    firstTitle: String,
    secondTitle: String,
    onFirstTextChange: (String) -> Unit,
    onSecondTextChange: (String) -> Unit,
    isError: Boolean = false,
    enabled: Boolean = true,
    icon: Painter? = null,
    units: String? = null
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Top
    ) {
        icon?.let {
            Icon(
                modifier = Modifier
                    .size(32.dp)
                    .padding(end = 8.dp),
                painter = icon, contentDescription = null
            )
        }

        OutlinedTextField(
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp),
            value = textFirst,
            onValueChange = { onFirstTextChange(it) },
            singleLine = true,
            enabled = enabled,
            label = { Text(text = firstTitle) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            trailingIcon = {
                units?.let {
                    Text(text = units)
                }
            }
        )
        OutlinedTextField(
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp),
            value = textSecond,
            onValueChange = { onSecondTextChange(it) },
            singleLine = true,
            enabled = enabled,
            label = { Text(text = secondTitle) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            trailingIcon = {
                units?.let {
                    Text(text = units)
                }
            }
        )
    }
}

@Composable
fun TitleWithSubtitleSmallView(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String? = null
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = title,
        )
        if (!subtitle.isNullOrBlank()) {
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun CheckboxWithText(
    modifier: Modifier = Modifier,
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            text = text
        )

        Checkbox(
            modifier = Modifier.padding(start = 32.dp),
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsDefaultScreenContainer(
    title: String,
    onNavigateBack: () -> Unit = {},
    backButtonEnabled: Boolean = true,
    onNavigateNext: () -> Unit,
    onTopBarNavigationClick: () -> Unit,
    content: @Composable() () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SettingsDefaultTopBar(
                title = title,
                onActionClick = {
                    Toast.makeText(context, "Help", Toast.LENGTH_SHORT).show()
                },
                onNavigationClick = onTopBarNavigationClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
        ) {

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
            ) {
                content()
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    onClick = onNavigateBack,
                    enabled = backButtonEnabled
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = null
                    )
                    Text(text = stringResource(R.string.back))
                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    onClick = onNavigateNext
                ) {
                    Text(
                        text = stringResource(R.string.next)
                    )
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = null
                    )

                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsDefaultTopBar(
    title: String,
    onNavigationClick: () -> Unit,
    onActionClick: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(imageVector = Icons.Default.Close, contentDescription = null)
            }
        },
        title = {
            Text(text = title)
        },
        actions = {
            IconButton(onClick = onActionClick) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_help_outline_24),
                    contentDescription = null
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeSelectDialog(
    onDismiss: () -> Unit,
    title: String,
    initialMinutes: String = "",
    initialSeconds: String = ""
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TitleWithSubtitleView(title = title)

                var minutes by remember { mutableStateOf(initialMinutes) }
                var seconds by remember { mutableStateOf(initialSeconds) }

                var isMinutesFocused by remember { mutableStateOf(true) }

                fun updateValue(newValue: String) {
                    if (isMinutesFocused) {
                        if (minutes.toList().size > 1) {
                            minutes = newValue
                        } else {
                            minutes += newValue
                        }
                    } else {
                        if (seconds.toList().size > 1) {
                            seconds = newValue
                        } else {
                            seconds += newValue
                        }
                    }
                }

                fun clear() {
                    if (isMinutesFocused) {
                        minutes = ""
                    } else {
                        seconds = ""
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    InputChip(
                        selected = isMinutesFocused,
                        onClick = { isMinutesFocused = true },
                        colors = InputChipDefaults.inputChipColors(selectedContainerColor = MaterialTheme.colorScheme.surfaceVariant),
                        label = {
                            if (minutes.isBlank()) {
                                Text(
                                    text = "00",
                                    color = Color.Gray,
                                    style = MaterialTheme.typography.headlineLarge
                                )
                            } else {
                                Text(
                                    text = minutes,
                                    style = MaterialTheme.typography.headlineLarge
                                )
                            }
                        }
                    )

                    Text(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        text = ":",
                        style = MaterialTheme.typography.headlineLarge
                    )

                    InputChip(
                        selected = !isMinutesFocused,
                        onClick = { isMinutesFocused = false },
                        colors = InputChipDefaults.inputChipColors(selectedContainerColor = MaterialTheme.colorScheme.surfaceVariant),
                        label = {
                            if (seconds.isBlank()) {
                                Text(
                                    text = "00",
                                    color = Color.Gray,
                                    style = MaterialTheme.typography.headlineLarge
                                )
                            } else {
                                Text(
                                    text = seconds,
                                    style = MaterialTheme.typography.headlineLarge
                                )
                            }

                        }
                    )
                }

                NumericKeyboard(
                    onDigitClick = { updateValue(it) },
                    onClear = { clear() },
                    onDone = { }
                )
            }
        }
    }
}

@Composable
fun NumericKeyboard(
    modifier: Modifier = Modifier,
    onDigitClick: (String) -> Unit,
    onClear: () -> Unit,
    onDone: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        (0..2).forEach { i ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                (1..3).forEach { j ->
                    val buttonValue: String = remember { (j + 3 * i).toString() }
                    SuggestionChip(
                        shape = CircleShape,
                        onClick = { onDigitClick(buttonValue) },
                        label = {
                            Text(
                                text = buttonValue,
                                style = MaterialTheme.typography.titleLarge
                            )
                        })
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            IconButton(onClick = onClear) {
                Icon(imageVector = Icons.Default.Clear, contentDescription = null)
            }
            SuggestionChip(
                shape = CircleShape,
                onClick = { onDigitClick("0") },
                label = {
                    Text(
                        text = "0",
                        style = MaterialTheme.typography.titleLarge
                    )
                })
            IconButton(onClick = onDone) {
                Icon(imageVector = Icons.Default.Done, contentDescription = null)
            }
        }
    }
}
