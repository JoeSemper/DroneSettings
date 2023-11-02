package com.joesemper.dronesettings.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.joesemper.dronesettings.R
import com.joesemper.dronesettings.ui.drone.TerminalScreen
import com.joesemper.dronesettings.ui.home.HomeScreen
import com.joesemper.dronesettings.ui.preset.PresetScreen
import com.joesemper.dronesettings.ui.settings.screens.mapping.SignalMappingSettingsScreen
import com.joesemper.dronesettings.ui.settings.screens.sensors.SensorsSettingsScreen
import com.joesemper.dronesettings.ui.settings.screens.signal.SignalSettingsScreen
import com.joesemper.dronesettings.ui.settings.screens.timeline.TimeLineSettingsScreen

const val HOME_ROUTE = "home"
const val TIMELINE_ROUTE = "timeline"
const val SENSORS_ROUTE = "sensors"
const val MAPPING_ROUTE = "mapping"
const val SIGNAL_ROUTE = "signal"
const val PRESET_ROUTE = "preset"
const val DRONE_ROUTE = "drone"
const val TERMINAL_ROUTE = "console"
const val PRESET_BUILDER = "builder"

const val PRESET_DATA_ID_ARG = "dataId"

sealed class Screen(
    val route: String,
    @StringRes val titleResId: Int,
    @DrawableRes val iconResId: Int
) {
    object Presets : Screen(HOME_ROUTE, R.string.presets, R.drawable.baseline_list_24)
    object Console : Screen(TERMINAL_ROUTE, R.string.terminal, R.drawable.baseline_terminal_24)
}

val bottomBarItems = listOf(Screen.Presets, Screen.Console)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = HOME_ROUTE,
) {

    Scaffold(
        bottomBar = {
            BottomAppBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                bottomBarItems.forEach { screen ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        label = {
                            Text(text = stringResource(id = screen.titleResId))
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = screen.iconResId),
                                contentDescription = null
                            )
                        })
                }
            }
        }

    ) { innerPadding ->
        NavHost(
            modifier = modifier.padding(innerPadding),
            navController = navController,
            startDestination = startDestination
        ) {
            composable(
                route = HOME_ROUTE
            ) {
                HomeScreen(navController)
            }

            composable(
                route = "$TIMELINE_ROUTE/{$PRESET_DATA_ID_ARG}",
                arguments = listOf(navArgument(PRESET_DATA_ID_ARG) { type = NavType.IntType })
            ) {
                TimeLineSettingsScreen(navController)
            }

            composable(
                route = "$SENSORS_ROUTE/{$PRESET_DATA_ID_ARG}",
                arguments = listOf(navArgument(PRESET_DATA_ID_ARG) { type = NavType.IntType })
            ) {
                SensorsSettingsScreen(navController)
            }

            composable(
                route = "$MAPPING_ROUTE/{$PRESET_DATA_ID_ARG}",
                arguments = listOf(navArgument(PRESET_DATA_ID_ARG) { type = NavType.IntType })
            ) {
                SignalMappingSettingsScreen(navController)
            }

            composable(
                route = "$SIGNAL_ROUTE/{$PRESET_DATA_ID_ARG}",
                arguments = listOf(navArgument(PRESET_DATA_ID_ARG) { type = NavType.IntType })
            ) {
                SignalSettingsScreen(navController)
            }

            composable(
                route = "$PRESET_ROUTE/{$PRESET_DATA_ID_ARG}",
                arguments = listOf(navArgument(PRESET_DATA_ID_ARG) { type = NavType.IntType })
            ) {
                PresetScreen(navController)
            }

            composable(
                route = TERMINAL_ROUTE
            ) {
                TerminalScreen(navController)
            }
        }
    }

}