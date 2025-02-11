package com.joesemper.dronesettings.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.joesemper.dronesettings.navigation.home.HOME_GRAPH
import com.joesemper.dronesettings.navigation.home.HomeDestinations.HOME_ROUTE
import com.joesemper.dronesettings.navigation.home.HomeState
import com.joesemper.dronesettings.navigation.home.addHomeGraph
import com.joesemper.dronesettings.navigation.terminal.addTerminalGraph

fun NavGraphBuilder.buildNavGraph(
    upPress: () -> Unit,
    homeState: HomeState
) {
    navigation(
        route = HOME_GRAPH,
        startDestination = "$HOME_GRAPH/$HOME_ROUTE"
    ) {
        addHomeGraph(
            upPress = upPress,
            homeState = homeState
        )
//        addTerminalGraph()
    }
}