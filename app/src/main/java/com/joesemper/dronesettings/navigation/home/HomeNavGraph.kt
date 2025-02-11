package com.joesemper.dronesettings.navigation.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.joesemper.dronesettings.navigation.home.HomeDestinations.HOME_NEW_PRESET_MAPPING_ROUTE
import com.joesemper.dronesettings.navigation.home.HomeDestinations.HOME_NEW_PRESET_SENSORS_ROUTE
import com.joesemper.dronesettings.navigation.home.HomeDestinations.HOME_NEW_PRESET_SIGNAL_ROUTE
import com.joesemper.dronesettings.navigation.home.HomeDestinations.HOME_NEW_PRESET_SETTINGS_ROUTE
import com.joesemper.dronesettings.navigation.home.HomeDestinations.HOME_PRESET_ID_KEY
import com.joesemper.dronesettings.navigation.home.HomeDestinations.HOME_PRESET_ROUTE
import com.joesemper.dronesettings.navigation.home.HomeDestinations.HOME_ROUTE
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
    const val HOME_NEW_PRESET_SETTINGS_ROUTE = "preset_settings"
    const val HOME_NEW_PRESET_SENSORS_ROUTE = "sensors"
    const val HOME_NEW_PRESET_MAPPING_ROUTE = "mapping"
    const val HOME_NEW_PRESET_SIGNAL_ROUTE = "signal"
    const val HOME_PRESET_ID_KEY = "presetId"
}

fun NavGraphBuilder.addHomeGraph(
    homeState: HomeState,
    upPress: () -> Unit
) {
    composable("$HOME_GRAPH/$HOME_ROUTE") { from ->
        HomeScreen(
            navigateToPreset = { id -> homeState.navigateToPreset(id, from) },
            navigateToNewPreset = { id -> homeState.navigateToTimelineSettings(id, from) }
        )
    }

    composable(
        route = "$HOME_PRESET_ROUTE/{$HOME_PRESET_ID_KEY}",
        arguments = listOf(navArgument(HOME_PRESET_ID_KEY) { type = NavType.IntType })
    ) {
        PresetScreen(upPress = upPress)
    }

    composable(
        route = "$HOME_NEW_PRESET_SETTINGS_ROUTE/{$HOME_PRESET_ID_KEY}",
        arguments = listOf(navArgument(HOME_PRESET_ID_KEY) { type = NavType.IntType })
    ) { from ->
        TimeLineSettingsScreen(
            navigateNext = { id ->
                homeState.navigateToPreset(id, from)
            },
            onClose = {
                homeState.navigateHome(from)
            }
        )
    }

    composable(
        route = "$HOME_NEW_PRESET_SENSORS_ROUTE/{$HOME_PRESET_ID_KEY}",
        arguments = listOf(navArgument(HOME_PRESET_ID_KEY) { type = NavType.IntType })
    ) { from ->
        SensorsSettingsScreen(
            navigateNext = { id ->
                homeState.navigateToMappingSettings(id, from)
            },
            navigateUp = upPress,
            onClose = {
                homeState.navigateHome(from)
            }
        )
    }

    composable(
        route = "$HOME_NEW_PRESET_MAPPING_ROUTE/{$HOME_PRESET_ID_KEY}",
        arguments = listOf(navArgument(HOME_PRESET_ID_KEY) { type = NavType.IntType })
    ) { from ->
        SignalMappingSettingsScreen(
            navigateNext = { id ->
                homeState.navigateToSignalSettings(id, from)
            },
            navigateUp = upPress,
            onClose = {
                homeState.navigateHome(from)
            }
        )
    }

    composable(
        route = "$HOME_NEW_PRESET_SIGNAL_ROUTE/{$HOME_PRESET_ID_KEY}",
        arguments = listOf(navArgument(HOME_PRESET_ID_KEY) { type = NavType.IntType })
    ) { from ->
        SignalSettingsScreen(
            navigateNext = { id ->
                homeState.navigateToPreset(id, from)
            },
            navigateUp = upPress,
            onClose = {
                homeState.navigateHome(from)
            }
        )
    }

}