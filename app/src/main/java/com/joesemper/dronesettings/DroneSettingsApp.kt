package com.joesemper.dronesettings

import android.app.Application
import com.joesemper.dronesettings.di.databaseModule
import com.joesemper.dronesettings.di.useCaseModule
import com.joesemper.dronesettings.di.viewModelModule
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class DroneSettingsApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@DroneSettingsApp)
            modules(
                viewModelModule,
                databaseModule,
                useCaseModule
            )
        }
    }
}