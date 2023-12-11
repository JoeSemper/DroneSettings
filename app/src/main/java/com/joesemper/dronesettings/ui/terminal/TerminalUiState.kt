package com.joesemper.dronesettings.ui.terminal

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.joesemper.dronesettings.R
import com.joesemper.dronesettings.data.datasource.room.prepopulated.entity.Command

data class TerminalUiState(
    val log: List<TerminalMassage> = emptyList(),
    val commands: List<Command> = emptyList(),
    val isConnected: Boolean = false
)

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


enum class TerminalMassageCategory() {
    USER, SYSTEM, DEVICE, ERROR
}