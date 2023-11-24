package com.joesemper.dronesettings.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.joesemper.dronesettings.navigation.AppNavHost
import com.joesemper.dronesettings.ui.theme.DroneSettingsTheme
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityScope
import org.koin.core.scope.Scope

class MainActivity : ComponentActivity(), AndroidScopeComponent {

    override val scope: Scope by activityScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        scope.declare(this@MainActivity)

        setContent {
            DroneSettingsTheme {
                AppNavHost()
            }
        }
    }
}


