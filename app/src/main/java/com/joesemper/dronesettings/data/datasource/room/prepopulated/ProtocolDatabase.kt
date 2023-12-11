package com.joesemper.dronesettings.data.datasource.room.prepopulated

import androidx.room.Database
import androidx.room.RoomDatabase
import com.joesemper.dronesettings.data.datasource.room.prepopulated.dao.ProtocolDao
import com.joesemper.dronesettings.data.datasource.room.prepopulated.entity.Command
import com.joesemper.dronesettings.data.datasource.room.prepopulated.entity.Variable


@Database(
    entities = [
        Command::class,
        Variable::class,
    ],
    version = 1
)
abstract class ProtocolDatabase() : RoomDatabase() {
    abstract fun protocolDao(): ProtocolDao
}
