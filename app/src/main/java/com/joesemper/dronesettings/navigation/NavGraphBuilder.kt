package com.joesemper.dronesettings.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.joesemper.dronesettings.navigation.HomeDestinations.HOME_ROUTE

fun NavGraphBuilder.navGraph(
    onHomeItemSelected: (Int, NavBackStackEntry) -> Unit,
    upPress: () -> Unit
) {
    navigation(
        route = HOME_GRAPH,
        startDestination = "$HOME_GRAPH/$HOME_ROUTE"
    ) {
        addHomeGraph(onHomeItemSelected, upPress)
        addTerminalGraph()
    }
}