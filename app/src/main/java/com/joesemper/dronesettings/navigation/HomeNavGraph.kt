package com.joesemper.dronesettings.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.joesemper.dronesettings.navigation.HomeDestinations.HOME_ITEM_ROUTE
import com.joesemper.dronesettings.navigation.HomeDestinations.HOME_ROUTE
import com.joesemper.dronesettings.ui.PRESET_DATA_ID_ARG
import com.joesemper.dronesettings.ui.home.HomeScreen
import com.joesemper.dronesettings.ui.preset.PresetScreen

const val HOME_GRAPH = "home"

object HomeDestinations {
    const val HOME_ROUTE = "root"
    const val HOME_ITEM_ROUTE = "item"
    const val HOME_ITEM_ID_KEY = "itemId"
}

fun NavGraphBuilder.addHomeGraph(
    onHomeItemSelected: (Int, NavBackStackEntry) -> Unit,
    upPress: () -> Unit,
    modifier: Modifier = Modifier
) {
    composable("$HOME_GRAPH/$HOME_ROUTE") { from ->
        HomeScreen(onItemClick = { id -> onHomeItemSelected(id, from) })
    }

    composable(
        route = "$HOME_ITEM_ROUTE/{$PRESET_DATA_ID_ARG}",
        arguments = listOf(navArgument(PRESET_DATA_ID_ARG) { type = NavType.IntType })
    ) {
        PresetScreen(upPress)
    }

}