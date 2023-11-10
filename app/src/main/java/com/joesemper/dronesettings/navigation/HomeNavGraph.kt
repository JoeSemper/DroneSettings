package com.joesemper.dronesettings.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.joesemper.dronesettings.navigation.HomeDestinations.HOME_NEW_PRESET_MAPPING_ROUTE
import com.joesemper.dronesettings.navigation.HomeDestinations.HOME_NEW_PRESET_SENSORS_ROUTE
import com.joesemper.dronesettings.navigation.HomeDestinations.HOME_NEW_PRESET_SIGNAL_ROUTE
import com.joesemper.dronesettings.navigation.HomeDestinations.HOME_NEW_PRESET_TIMELINE_ROUTE
import com.joesemper.dronesettings.navigation.HomeDestinations.HOME_PRESET_ROUTE
import com.joesemper.dronesettings.navigation.HomeDestinations.HOME_ROUTE
import com.joesemper.dronesettings.ui.home.HomeScreen
import com.joesemper.dronesettings.ui.preset.PresetScreen
import com.joesemper.dronesettings.ui.settings.screens.mapping.SignalMappingSettingsScreen
import com.joesemper.dronesettings.ui.settings.screens.sensors.SensorsSettingsScreen
import com.joesemper.dronesettings.ui.settings.screens.signal.SignalSettingsScreen
import com.joesemper.dronesettings.ui.settings.screens.timeline.TimeLineSettingsScreen

const val HOME_GRAPH = "home"

object HomeDestinations {
    const val HOME_ROUTE = "root"
    const val HOME_PRESET_ROUTE = "preset"
    const val HOME_NEW_PRESET_TIMELINE_ROUTE = "timeline"
    const val HOME_NEW_PRESET_SENSORS_ROUTE = "sensors"
    const val HOME_NEW_PRESET_MAPPING_ROUTE = "mapping"
    const val HOME_NEW_PRESET_SIGNAL_ROUTE = "signal"
    const val HOME_PRESET_ID_KEY = "presetId"
}

fun NavGraphBuilder.addHomeGraph(
    homeState: HomeState,
) {
    composable("$HOME_GRAPH/$HOME_ROUTE") { from ->
        HomeScreen(
            navigateToPreset = { id -> homeState.navigateToPreset(id, from) },
            navigateToNewPreset = { id -> homeState.navigateToTimelineSettings(id, from) }
        )
    }

    composable(
        route = "$HOME_PRESET_ROUTE/{$PRESET_DATA_ID_ARG}",
        arguments = listOf(navArgument(PRESET_DATA_ID_ARG) { type = NavType.IntType })
    ) {
        PresetScreen(upPress = { homeState.upPress() })
    }

    composable(
        route = "$HOME_NEW_PRESET_TIMELINE_ROUTE/{$PRESET_DATA_ID_ARG}",
        arguments = listOf(navArgument(PRESET_DATA_ID_ARG) { type = NavType.IntType })
    ) { from ->
        TimeLineSettingsScreen(
            navigateNext = { id ->
                homeState.navigateToSensorsSettings(id, from)
            },
            onClose = {
                homeState.navigateHome()
            }
        )
    }

    composable(
        route = "$HOME_NEW_PRESET_SENSORS_ROUTE/{$PRESET_DATA_ID_ARG}",
        arguments = listOf(navArgument(PRESET_DATA_ID_ARG) { type = NavType.IntType })
    ) { from ->
        SensorsSettingsScreen(
            navigateNext = { id ->
                homeState.navigateToMappingSettings(id, from)
            },
            navigateUp = {
                homeState.upPress()
            },
            onClose = {
                homeState.navigateHome()
            }
        )
    }

    composable(
        route = "$HOME_NEW_PRESET_MAPPING_ROUTE/{$PRESET_DATA_ID_ARG}",
        arguments = listOf(navArgument(PRESET_DATA_ID_ARG) { type = NavType.IntType })
    ) { from ->
        SignalMappingSettingsScreen(
            navigateNext = { id ->
                homeState.navigateToSignalSettings(id, from)
            },
            navigateUp = {
                homeState.upPress()
            },
            onClose = {
                homeState.navigateHome()
            }
        )
    }

    composable(
        route = "$HOME_NEW_PRESET_SIGNAL_ROUTE/{$PRESET_DATA_ID_ARG}",
        arguments = listOf(navArgument(PRESET_DATA_ID_ARG) { type = NavType.IntType })
    ) { from ->
        SignalSettingsScreen(
            navigateNext = { id ->
                homeState.navigateToPreset(id, from)
            },
            navigateUp = {
                homeState.upPress()
            },
            onClose = {
                homeState.navigateHome()
            }
        )
    }

}