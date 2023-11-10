package com.joesemper.dronesettings.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.joesemper.dronesettings.navigation.HomeDestinations.HOME_ROUTE

fun NavGraphBuilder.buildNavGraph(
    homeState: HomeState
) {
    navigation(
        route = HOME_GRAPH,
        startDestination = "$HOME_GRAPH/$HOME_ROUTE"
    ) {
        addHomeGraph(homeState)
        addTerminalGraph()
    }
}