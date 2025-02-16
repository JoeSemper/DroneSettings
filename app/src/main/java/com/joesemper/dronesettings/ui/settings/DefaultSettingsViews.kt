package com.joesemper.dronesettings.ui.settings

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.joesemper.dronesettings.R
import com.joesemper.dronesettings.ui.settings.state.FloatingFieldDialogState
import com.joesemper.dronesettings.ui.settings.state.SingleFieldDialogState
import com.joesemper.dronesettings.ui.settings.state.TimeSelectDialogState

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

@Composable
fun TimeSelectDialog(
    onDismiss: () -> Unit,
    title: String,
    subtitle: String? = null,
    state: TimeSelectDialogState,
    onApply: (minutes: String, seconds: String) -> Unit
) {
    val context = LocalContext.current
    val toastMassage = stringResource(R.string.incorrect_value)

    Dialog(onDismissRequest = onDismiss) {
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                TitleWithSubtitleView(
                    modifier = Modifier.fillMaxWidth(),
                    title = title,
                    subtitle = subtitle
                )

                Spacer(modifier = Modifier.height(32.dp))

                TimeLayout(
                    minutes = state.minutes,
                    seconds = state.seconds,
                    minutesFocused = state.isMinutesFocused,
                    secondsFocused = state.isSecondsFocused,
                    isError = state.isError,
                    errorMassage = state.getErrorMassage(),
                    onMinutesClick = { state.focusMinutes() },
                    onSecondsClick = { state.focusSeconds() }
                )

                Spacer(modifier = Modifier.height(16.dp))

                NumericKeyboard(
                    onDigitClick = { state.updateValue(it) },
                    onClear = { state.clearValue() },
                    onNext = { state.focusNext() }
                )

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(text = stringResource(id = R.string.close))
                    }

                    Button(onClick = {
                        state.enableShowErrors()
                        if (state.isValid) {
                            onApply(state.minutes, state.seconds)
                            onDismiss()
                        } else {
                            Toast.makeText(context, toastMassage, Toast.LENGTH_SHORT).show()
                        }
                    }) {
                        Text(text = stringResource(R.string.done))
                    }
                }
            }
        }
    }
}

@Composable
fun SingleFieldEditDialog(
    onDismiss: () -> Unit,
    title: String,
    subtitle: String? = null,
    trailingText: String = "",
    isFloat: Boolean = false,
    state: SingleFieldDialogState,
    onApply: (value: String) -> Unit
) {
    val context = LocalContext.current
    val toastMassage = stringResource(R.string.incorrect_value)

    Dialog(onDismissRequest = onDismiss) {
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                TitleWithSubtitleView(
                    modifier = Modifier.fillMaxWidth(),
                    title = title,
                    subtitle = subtitle
                )

                Spacer(modifier = Modifier.height(32.dp))

                SingleFieldLayout(
                    value = state.value,
                    trailingText = trailingText,
                    isError = state.isError,
                    errorMassage = state.getErrorMassage(),
                )

                Spacer(modifier = Modifier.height(16.dp))

                NumericKeyboard(
                    onDigitClick = { state.updateValue(it) },
                    onClear = { state.clearValue() },
                    enableNext = false,
                    enablePoint = isFloat,
                    onNext = { }
                )

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(text = stringResource(id = R.string.close))
                    }

                    Button(onClick = {
                        state.enableShowErrors()
                        if (state.isValid) {
                            onApply(state.value)
                            onDismiss()
                        } else {
                            Toast.makeText(context, toastMassage, Toast.LENGTH_SHORT).show()
                        }
                    }) {
                        Text(text = stringResource(R.string.done))
                    }
                }
            }
        }
    }
}

@Composable
fun FloatingFieldEditDialog(
    onDismiss: () -> Unit,
    title: String,
    subtitle: String? = null,
    trailingText: String = "",
    state: FloatingFieldDialogState,
    onApply: (value: String) -> Unit
) {
    val context = LocalContext.current
    val toastMassage = stringResource(R.string.incorrect_value)

    Dialog(onDismissRequest = onDismiss) {
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                TitleWithSubtitleView(
                    modifier = Modifier.fillMaxWidth(),
                    title = title,
                    subtitle = subtitle
                )

                Spacer(modifier = Modifier.height(32.dp))

                SingleFieldLayout(
                    value = state.value,
                    trailingText = trailingText,
                    isError = state.isError,
                    errorMassage = state.getErrorMassage(),
                )

                Spacer(modifier = Modifier.height(16.dp))

                NumericKeyboard(
                    onDigitClick = { state.updateValue(it) },
                    onClear = { state.clearValue() },
                    enableNext = false,
                    enablePoint = true,
                    onNext = { }
                )

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(text = stringResource(id = R.string.close))
                    }

                    Button(onClick = {
                        state.enableShowErrors()
                        if (state.isValid) {
                            onApply(state.value)
                            onDismiss()
                        } else {
                            Toast.makeText(context, toastMassage, Toast.LENGTH_SHORT).show()
                        }
                    }) {
                        Text(text = stringResource(R.string.done))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeLayout(
    modifier: Modifier = Modifier,
    minutes: String = "",
    seconds: String = "",
    isError: Boolean = false,
    errorMassage: String = "",
    enabled: Boolean = true,
    minutesFocused: Boolean = false,
    secondsFocused: Boolean = false,
    onMinutesClick: () -> Unit,
    onSecondsClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center
        ) {

            FilterChip(
                selected = minutesFocused,
                onClick = onMinutesClick,
                enabled = enabled,
                colors = InputChipDefaults.inputChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                trailingIcon = {
                    Text(text = stringResource(id = R.string.min))
                },
                border = InputChipDefaults.inputChipBorder(
                    borderColor = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onPrimaryContainer,
                    selectedBorderColor = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onPrimaryContainer,
                    borderWidth = 0.5.dp,
                    selectedBorderWidth = 1.5.dp
                ),
                label = {
                    MeasureUnconstrainedViewWidth(
                        viewToMeasure = {
                            Text(
                                text = "00",
                                style = MaterialTheme.typography.displayMedium
                            )
                        }
                    ) {
                        Text(
                            modifier = Modifier.width(it),
                            textAlign = TextAlign.Center,
                            text = minutes,
                            style = MaterialTheme.typography.displayMedium
                        )
                    }
                }
            )

            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = ":",
                style = MaterialTheme.typography.displayMedium
            )

            FilterChip(
                selected = secondsFocused,
                onClick = onSecondsClick,
                enabled = enabled,
                colors = InputChipDefaults.inputChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                trailingIcon = {
                    Text(text = stringResource(id = R.string.sec))
                },
                border = InputChipDefaults.inputChipBorder(
                    borderColor = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onPrimaryContainer,
                    selectedBorderColor = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onPrimaryContainer,
                    borderWidth = 0.5.dp,
                    selectedBorderWidth = 1.5.dp
                ),
                label = {
                    MeasureUnconstrainedViewWidth(
                        viewToMeasure = {
                            Text(
                                text = "00",
                                style = MaterialTheme.typography.displayMedium
                            )
                        }
                    ) {
                        Text(
                            modifier = Modifier.width(it),
                            textAlign = TextAlign.Center,
                            text = seconds,
                            style = MaterialTheme.typography.displayMedium
                        )
                    }
                }
            )
        }

        ErrorText(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp),
            isError = isError,
            errorMassage = errorMassage
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleFieldLayout(
    modifier: Modifier = Modifier,
    value: String = "",
    trailingText: String = "",
    isError: Boolean = false,
    errorMassage: String = "",
    enabled: Boolean = true,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center
        ) {

            FilterChip(
                selected = true,
                onClick = { },
                enabled = enabled,
                colors = InputChipDefaults.inputChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                trailingIcon = {
                    Text(text = trailingText)
                },
                border = InputChipDefaults.inputChipBorder(
                    borderColor = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onPrimaryContainer,
                    selectedBorderColor = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onPrimaryContainer,
                    borderWidth = 0.5.dp,
                    selectedBorderWidth = 1.5.dp
                ),
                label = {
                    MeasureUnconstrainedViewWidth(
                        viewToMeasure = {
                            Text(
                                text = "000000",
                                style = MaterialTheme.typography.displayMedium
                            )
                        }
                    ) {
                        Text(
                            modifier = Modifier.width(it),
                            textAlign = TextAlign.Center,
                            text = value,
                            style = MaterialTheme.typography.displayMedium
                        )
                    }
                }
            )
        }

        ErrorText(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp),
            isError = isError,
            errorMassage = errorMassage
        )
    }
}

@Composable
fun MeasureUnconstrainedViewWidth(
    viewToMeasure: @Composable () -> Unit,
    content: @Composable (measuredWidth: Dp) -> Unit,
) {
    SubcomposeLayout { constraints ->
        val measuredWidth = subcompose("viewToMeasure", viewToMeasure)[0]
            .measure(Constraints()).width.toDp()

        val contentPlaceable = subcompose("content") {
            content(measuredWidth)
        }[0].measure(constraints)
        layout(contentPlaceable.width, contentPlaceable.height) {
            contentPlaceable.place(0, 0)
        }
    }
}

@Composable
fun NumericKeyboard(
    modifier: Modifier = Modifier,
    onDigitClick: (String) -> Unit,
    onClear: () -> Unit,
    enableNext: Boolean = true,
    enablePoint: Boolean = false,
    onNext: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
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
                                style = MaterialTheme.typography.displaySmall
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
                        style = MaterialTheme.typography.displaySmall
                    )
                })
            if (enableNext) {
                IconButton(onClick = onNext) {
                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null)
                }
            } else if (enablePoint) {
                IconButton(onClick = { onDigitClick(".") }) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(R.drawable.dot),
                        contentDescription = null
                    )
                }
            } else {
                IconButton(
                    onClick = { },
                    enabled = false
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(R.drawable.dot),
                        contentDescription = null,
                        tint = Color.Transparent
                    )
                }
            }

        }
    }
}

@Composable
fun ErrorText(
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorMassage: String
) {
    Row(
        modifier = modifier
    ) {
        AnimatedVisibility(
            visible = isError,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    tint = MaterialTheme.colorScheme.error,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = errorMassage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center
                )
            }

        }
    }
}

@Composable
fun TerminalTextInputView(
    modifier: Modifier = Modifier,
    textFieldState: String,
    updateText: (String) -> Unit,
    onMenuClick: () -> Unit,
    onSendClick: () -> Unit

) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = modifier.focusRequester(focusRequester),
        value = textFieldState,
        onValueChange = updateText,
        placeholder = { Text(text = stringResource(id = R.string.command)) },
        leadingIcon = {
            IconButton(
                onClick = {
                    focusManager.clearFocus()
                    onMenuClick()
                },
            ) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = null)
            }
        },
        trailingIcon = {
            IconButton(
                onClick = onSendClick,
            ) {
                Icon(imageVector = Icons.Default.Send, contentDescription = null)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParameterCardView(
    modifier: Modifier = Modifier,
    title: String,
    info: String? = null,
    units: String? = null,
    errorMassage: String = "",
    switchable: Boolean = false,
    enabled: Boolean = true,
    onEnabledChange: ((Boolean) -> Unit)? = null,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    val showInfoDialog = remember {
        mutableStateOf(false)
    }
    if (showInfoDialog.value) {
        InfoDialogView(
            onDismiss = { showInfoDialog.value = false },
            title = title,
            info = info
        )
    }

    Card(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleSmall
                    )
                    IconButton(
                        modifier = Modifier.size(24.dp),
                        onClick = { showInfoDialog.value = true }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_help_outline_24),
                            contentDescription = null
                        )
                    }
                }

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        content()

                        Spacer(modifier = Modifier.width(8.dp))

                        units?.let { Text(text = units) }
                    }
                }
                if(errorMassage.isNotEmpty()) {
                    ErrorText(
                        isError = true,
                        errorMassage = errorMassage,
                    )
                }
            }

            if (switchable) {
                Switch(
                    modifier = Modifier,
                    checked = enabled,
                    onCheckedChange = { onEnabledChange?.invoke(it) }
                )
            }
        }

    }
}

@Composable
fun InfoDialogView(
    title: String,
    info: String? = null,
    onDismiss: () -> Unit,
) {
    Dialog(onDismissRequest = onDismiss) {
        Card {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TitleWithSubtitleView(title = title)
                }

                Text(
                    text = info ?: "",
                    style = MaterialTheme.typography.bodyMedium
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                )
                {
                    TextButton(
                        onClick = onDismiss
                    ) {
                        Text(text = stringResource(id = R.string.close))
                    }
                }
            }
        }
    }
}


