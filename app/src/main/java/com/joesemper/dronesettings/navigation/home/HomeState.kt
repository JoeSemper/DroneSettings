package com.joesemper.dronesettings.navigation.home

import androidx.navigation.NavBackStackEntry

interface HomeState {
    fun navigateHome(from: NavBackStackEntry)
    fun navigateToPreset(itemId: Int, from: NavBackStackEntry)
    fun navigateToTimelineSettings(itemId: Int, from: NavBackStackEntry)
    fun navigateToSensorsSettings(itemId: Int, from: NavBackStackEntry)
    fun navigateToMappingSettings(itemId: Int, from: NavBackStackEntry)
    fun navigateToSignalSettings(itemId: Int, from: NavBackStackEntry)
}