package com.joesemper.dronesettings.navigation.terminal

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.joesemper.dronesettings.navigation.terminal.ChatDestinations.TERMINAL_ROUTE
import com.joesemper.dronesettings.ui.terminal.TerminalScreen

const val TERMINAL_GRAPH = "terminal"

object ChatDestinations {
    const val TERMINAL_ROUTE = "root"
}

fun NavGraphBuilder.addTerminalGraph() {
    composable(
        route = "$TERMINAL_GRAPH/$TERMINAL_ROUTE"
    ) {
        TerminalScreen()
    }
}