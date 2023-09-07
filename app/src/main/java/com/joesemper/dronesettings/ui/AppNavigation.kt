package com.joesemper.dronesettings.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.joesemper.dronesettings.ui.home.HomeScreen
import com.joesemper.dronesettings.ui.settings.SettingsScreen
import com.joesemper.dronesettings.ui.settings.timeline.TimeLineSettingsScreen

const val HOME_ROUTE = "home"
const val SETTINGS_ROUTE = "settings"
const val TIMELINE_ROUTE = "timeline"

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
                route = SETTINGS_ROUTE
            ) {
                SettingsScreen(navController)
            }

            composable(
                route = TIMELINE_ROUTE
            ) {
                TimeLineSettingsScreen(navController)
            }

        }
    }

}