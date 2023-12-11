package com.joesemper.dronesettings.di

import androidx.room.Room
import com.joesemper.dronesettings.data.datasource.room.main.DroneSettingsDatabase
import com.joesemper.dronesettings.data.datasource.room.main.dao.SettingsDao
import com.joesemper.dronesettings.data.datasource.room.prepopulated.ProtocolDatabase
import com.joesemper.dronesettings.data.datasource.room.prepopulated.dao.ProtocolDao
import com.joesemper.dronesettings.data.repository.ProtocolRepositoryImpl
import com.joesemper.dronesettings.data.repository.SettingsRepositoryImpl
import com.joesemper.dronesettings.domain.repository.ProtocolRepository
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

    single<ProtocolDatabase> {
        Room.databaseBuilder(
            androidContext(),
            ProtocolDatabase::class.java,
            "ProtocolDatabase"
        )
            .createFromAsset("database/protocol.db")
            .build()
    }

    single<ProtocolDao> {
        val protocolDatabase = get<ProtocolDatabase>()
        protocolDatabase.protocolDao()
    }

    single<ProtocolRepository> { ProtocolRepositoryImpl(get()) }

}