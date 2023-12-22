package com.joesemper.dronesettings.ui.terminal

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import com.joesemper.dronesettings.R
import com.joesemper.dronesettings.data.datasource.room.prepopulated.entity.Command
import com.joesemper.dronesettings.data.datasource.room.prepopulated.entity.Variable

data class TerminalUiState(
    val log: List<TerminalMassage> = emptyList(),
    val text: String = "T",
    val testText: String = "",
    val bottomSheetState: BottomSheetState = BottomSheetState(),
    val textFieldState: MutableState<String> = mutableStateOf(""),
    val isConnected: Boolean = false,
    val settings: TerminalsSettings = TerminalsSettings()
) {
    fun clearTextField() {
        textFieldState.value = ""
    }

    fun addTextToTextField(text: String) {
        textFieldState.value = textFieldState.value + text + " "
    }
}

data class TerminalMassage(
    val massage: String,
    val time: String,
    val category: TerminalMassageCategory
) {
    @Composable
    fun prefix() = when (category) {
        TerminalMassageCategory.USER -> stringResource(R.string.user_prefix)
        TerminalMassageCategory.SYSTEM -> stringResource(R.string.system_prefix)
        TerminalMassageCategory.DEVICE -> stringResource(R.string.device_prefix)
        TerminalMassageCategory.ERROR -> stringResource(R.string.error_prefix)
    }

    @Composable
    fun color() = when (category) {
        TerminalMassageCategory.USER -> MaterialTheme.colorScheme.onPrimaryContainer
        TerminalMassageCategory.SYSTEM -> MaterialTheme.colorScheme.secondary
        TerminalMassageCategory.DEVICE -> MaterialTheme.colorScheme.primary
        TerminalMassageCategory.ERROR -> MaterialTheme.colorScheme.error
    }
}

data class BottomSheetState(
    val shouldShowBottomSheet: MutableState<Boolean> = mutableStateOf(false),
    val commands: List<Command> = emptyList(),
    val variables: List<Variable> = emptyList(),
) {
    fun showBottomSheet() {
        shouldShowBottomSheet.value = true
    }

    fun hideBottomSheet() {
        shouldShowBottomSheet.value = false
    }
}

data class TerminalsSettings(
    val shouldHideUserCommands: Boolean = false,
    val shouldAddStringEndSymbol: Boolean = true,
    val text: String = "T"
)


enum class TerminalMassageCategory() {
    USER, SYSTEM, DEVICE, ERROR
}