package com.joesemper.dronesettings.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import com.joesemper.dronesettings.R

const val PRESET_DATA_ID_ARG = "dataId"

@Composable
fun AppNavHost(
    startDestination: String = HOME_GRAPH,
) {
    val appState = rememberAppState()
    val homeState = rememberHomeState(navController = appState.navController)

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = appState.shouldShowBottomBar,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it }),
            ) {
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
            buildNavGraph(
                homeState = homeState
            )
        }
    }
}


sealed class NavigationItem(
    val route: String,
    @StringRes val titleRes: Int,
    @DrawableRes val iconRes: Int
) {
    object Presets : NavigationItem(
        route = "$HOME_GRAPH/${HomeDestinations.HOME_ROUTE}",
        titleRes = R.string.presets,
        iconRes = R.drawable.baseline_list_24
    )

    object Console : NavigationItem(
        route = "$TERMINAL_GRAPH/${ChatDestinations.TERMINAL_ROUTE}",
        titleRes = R.string.terminal,
        iconRes = R.drawable.baseline_terminal_24
    )
}

val bottomBarItems = listOf(NavigationItem.Presets, NavigationItem.Console)


@Composable
fun BottomBar(
    tabs: List<NavigationItem>,
    currentRoute: String,
    navigateToRoute: (String) -> Unit
) {
    BottomAppBar {
        tabs.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = { navigateToRoute(screen.route) },
                label = {
                    Text(text = stringResource(id = screen.titleRes))
                },
                icon = {
                    Icon(
                        painter = painterResource(id = screen.iconRes),
                        contentDescription = null
                    )
                }
            )
        }
    }
}