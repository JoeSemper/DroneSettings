package com.joesemper.dronesettings.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.joesemper.dronesettings.ui.theme.DroneSettingsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DroneSettingsTheme {
                AppNavHost()
            }
        }
    }
}

