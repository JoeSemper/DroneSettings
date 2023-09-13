package com.joesemper.dronesettings.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.joesemper.dronesettings.ui.home.HomeScreen
import com.joesemper.dronesettings.ui.settings.mapping.SignalMappingSettingsScreen
import com.joesemper.dronesettings.ui.settings.sensors.SensorsSettingsScreen
import com.joesemper.dronesettings.ui.settings.signal.SignalSettingsScreen
import com.joesemper.dronesettings.ui.settings.timeline.TimeLineSettingsScreen

const val HOME_ROUTE = "home"
const val TIMELINE_ROUTE = "timeline"
const val SENSORS_ROUTE = "sensors"
const val MAPPING_ROUTE = "mapping"
const val SIGNAL_ROUTE = "signal"

const val SETTINGS_SET_ID_ARG = "settingsSetId"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = HOME_ROUTE,
) {

    Scaffold { innerPadding ->
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
                route = "$TIMELINE_ROUTE/{$SETTINGS_SET_ID_ARG}",
                arguments = listOf(navArgument(SETTINGS_SET_ID_ARG) { type = NavType.IntType })
            ) {
                TimeLineSettingsScreen(navController)
            }

            composable(
                route = "$SENSORS_ROUTE/{$SETTINGS_SET_ID_ARG}",
                arguments = listOf(navArgument(SETTINGS_SET_ID_ARG) { type = NavType.IntType })
            ) {
                SensorsSettingsScreen(navController)
            }

            composable(
                route = "$MAPPING_ROUTE/{$SETTINGS_SET_ID_ARG}",
                arguments = listOf(navArgument(SETTINGS_SET_ID_ARG) { type = NavType.IntType })
            ) {
                SignalMappingSettingsScreen(navController)
            }

            composable(
                route = "$SIGNAL_ROUTE/{$SETTINGS_SET_ID_ARG}",
                arguments = listOf(navArgument(SETTINGS_SET_ID_ARG) { type = NavType.IntType })
            ) {
                SignalSettingsScreen(navController)
            }
        }
    }

}