package com.joesemper.dronesettings.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.joesemper.dronesettings.R
import com.joesemper.dronesettings.navigation.ChatDestinations
import com.joesemper.dronesettings.navigation.HOME_GRAPH
import com.joesemper.dronesettings.navigation.HomeDestinations
import com.joesemper.dronesettings.navigation.TERMINAL_GRAPH
import com.joesemper.dronesettings.navigation.navGraph

//const val HOME_ROUTE = "home"
//const val TIMELINE_ROUTE = "timeline"
//const val SENSORS_ROUTE = "sensors"
//const val MAPPING_ROUTE = "mapping"
//const val SIGNAL_ROUTE = "signal"
//const val PRESET_ROUTE = "preset"
//const val DRONE_ROUTE = "drone"
//const val TERMINAL_ROUTE = "console"
//const val PRESET_BUILDER = "builder"

const val PRESET_DATA_ID_ARG = "dataId"

sealed class Screen(
    val route: String,
    @StringRes val titleResId: Int,
    @DrawableRes val iconResId: Int
) {
    object Presets : Screen("$HOME_GRAPH/${HomeDestinations.HOME_ROUTE}", R.string.presets, R.drawable.baseline_list_24)
    object Console : Screen("$TERMINAL_GRAPH/${ChatDestinations.TERMINAL_ROUTE}", R.string.terminal, R.drawable.baseline_terminal_24)
}

val bottomBarItems = listOf(Screen.Presets, Screen.Console)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = HOME_GRAPH,
) {
    val appState = rememberAppState()

    Scaffold(
        bottomBar = {
            if (appState.shouldShowBottomBar) {
                BottomBar(
                    tabs = bottomBarItems,
                    currentRoute = appState.currentRoute!!,
                    navigateToRoute = appState::navigateToBottomBarRoute
                )
            }
        }

    ) { innerPadding ->

        NavHost(
            navController = appState.navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            navGraph(
                onHomeItemSelected = appState::navigateToHomeItemDetail,
                upPress = appState::upPress
            )
        }

//        NavHost(
//            modifier = modifier.padding(innerPadding),
//            navController = navController,
//            startDestination = startDestination
//        ) {
//            composable(
//                route = HOME_ROUTE
//            ) {
////                HomeScreen(navController)
//            }
//
//            composable(
//                route = "$TIMELINE_ROUTE/{$PRESET_DATA_ID_ARG}",
//                arguments = listOf(navArgument(PRESET_DATA_ID_ARG) { type = NavType.IntType })
//            ) {
//                TimeLineSettingsScreen(navController)
//            }
//
//            composable(
//                route = "$SENSORS_ROUTE/{$PRESET_DATA_ID_ARG}",
//                arguments = listOf(navArgument(PRESET_DATA_ID_ARG) { type = NavType.IntType })
//            ) {
//                SensorsSettingsScreen(navController)
//            }
//
//            composable(
//                route = "$MAPPING_ROUTE/{$PRESET_DATA_ID_ARG}",
//                arguments = listOf(navArgument(PRESET_DATA_ID_ARG) { type = NavType.IntType })
//            ) {
//                SignalMappingSettingsScreen(navController)
//            }
//
//            composable(
//                route = "$SIGNAL_ROUTE/{$PRESET_DATA_ID_ARG}",
//                arguments = listOf(navArgument(PRESET_DATA_ID_ARG) { type = NavType.IntType })
//            ) {
//                SignalSettingsScreen(navController)
//            }
//
//            composable(
//                route = "$PRESET_ROUTE/{$PRESET_DATA_ID_ARG}",
//                arguments = listOf(navArgument(PRESET_DATA_ID_ARG) { type = NavType.IntType })
//            ) {
////                PresetScreen(navController)
//            }
//
//            composable(
//                route = TERMINAL_ROUTE
//            ) {
////                TerminalScreen(navController)
//            }
//        }
    }

}