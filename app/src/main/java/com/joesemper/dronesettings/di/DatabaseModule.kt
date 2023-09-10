package com.joesemper.dronesettings.di

import androidx.room.Room
import com.joesemper.dronesettings.data.datasource.room.DroneSettingsDatabase
import com.joesemper.dronesettings.data.datasource.room.dao.SettingsDao
import com.joesemper.dronesettings.data.repository.SettingsRepositoryImpl
import com.joesemper.dronesettings.domain.repository.SettingsRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single<DroneSettingsDatabase> {
        Room.databaseBuilder(
            androidContext(),
            DroneSettingsDatabase::class.java,
            "DroneSettingsDatabase"
        )
            .build()
    }

    single<SettingsDao> {
        val database = get<DroneSettingsDatabase>()
        database.settingsDao()
    }

    single<SettingsRepository> { SettingsRepositoryImpl(get()) }
}