package com.joesemper.dronesettings.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
) = remember(navController) { AppState(navController) }

@Stable
open class AppState(
    val navController: NavHostController,
) {

    private val bottomBarRoutes = bottomBarItems.map { it.route }

    val shouldShowBottomBar: Boolean
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination?.route in bottomBarRoutes

    val currentRoute: String?
        get() = navController.currentDestination?.route

    fun upPress() {
        navController.navigateUp()
    }

    fun navigateToBottomBarRoute(route: String) {
        if (route != currentRoute) {
            navController.navigate(route) {
                launchSingleTop = true
                restoreState = true
                popUpTo(findStartDestination(navController.graph).id) {
                    saveState = true
                }
            }
        }
    }
}

@Composable
fun rememberHomeState(
    navController: NavHostController,
) = remember(navController) { HomeState(navController) }

class HomeState(navController: NavHostController): AppState(navController) {

    fun navigateHome() {
        navController.navigate(NavigationItem.Presets.route)
    }

    fun navigateToPreset(itemId: Int, from: NavBackStackEntry) {
        if (from.lifecycleIsResumed()) {
            navController.navigate("${HomeDestinations.HOME_PRESET_ROUTE}/$itemId")
        }
    }

    fun navigateToTimelineSettings(itemId: Int, from: NavBackStackEntry) {
        if (from.lifecycleIsResumed()) {
            navController.navigate("${HomeDestinations.HOME_NEW_PRESET_TIMELINE_ROUTE}/$itemId")
        }
    }

    fun navigateToSensorsSettings(itemId: Int, from: NavBackStackEntry) {
        if (from.lifecycleIsResumed()) {
            navController.navigate("${HomeDestinations.HOME_NEW_PRESET_SENSORS_ROUTE}/$itemId")
        }
    }

    fun navigateToMappingSettings(itemId: Int, from: NavBackStackEntry) {
        if (from.lifecycleIsResumed()) {
            navController.navigate("${HomeDestinations.HOME_NEW_PRESET_MAPPING_ROUTE}/$itemId")
        }
    }

    fun navigateToSignalSettings(itemId: Int, from: NavBackStackEntry) {
        if (from.lifecycleIsResumed()) {
            navController.navigate("${HomeDestinations.HOME_NEW_PRESET_SIGNAL_ROUTE}/$itemId")
        }
    }

}


private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED

private val NavGraph.startDestination: NavDestination?
    get() = findNode(startDestinationId)

private tailrec fun findStartDestination(graph: NavDestination): NavDestination {
    return if (graph is NavGraph) findStartDestination(graph.startDestination!!) else graph
}