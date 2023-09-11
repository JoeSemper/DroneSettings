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
import com.joesemper.dronesettings.ui.settings.SettingsScreen
import com.joesemper.dronesettings.ui.settings.mapping.SignalMappingSettingsScreen
import com.joesemper.dronesettings.ui.settings.sensors.SensorsSettingsScreen
import com.joesemper.dronesettings.ui.settings.signal.SignalSettingsScreen
import com.joesemper.dronesettings.ui.settings.timeline.TimeLineSettingsScreen

const val HOME_ROUTE = "home"
const val SETTINGS_ROUTE = "settings"
const val TIMELINE_ROUTE = "timeline"
const val SENSORS_ROUTE = "sensors"
const val MAPPING_ROUTE = "mapping"
const val SIGNAL_ROUTE = "signal"

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
                route = SETTINGS_ROUTE,
            ) {
                SettingsScreen(navController)
            }

            composable(
                route = "$TIMELINE_ROUTE/{settingsPresetId}",
                arguments = listOf(navArgument("settingsPresetId") { type = NavType.IntType })
            ) {
                TimeLineSettingsScreen(navController)
            }

            composable(
                route = SENSORS_ROUTE
            ) {
                SensorsSettingsScreen(navController)
            }

            composable(
                route = MAPPING_ROUTE
            ) {
                SignalMappingSettingsScreen(navController)
            }

            composable(
                route = SIGNAL_ROUTE
            ) {
                SignalSettingsScreen(navController)
            }
        }
    }

}