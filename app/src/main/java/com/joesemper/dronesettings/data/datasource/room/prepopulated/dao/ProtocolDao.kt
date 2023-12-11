package com.joesemper.dronesettings.data.datasource.room.prepopulated.dao

import androidx.room.Dao
import androidx.room.Query
import com.joesemper.dronesettings.data.datasource.room.prepopulated.entity.Command
import com.joesemper.dronesettings.data.datasource.room.prepopulated.entity.Variable

@Dao
interface ProtocolDao {

    @Query("SELECT * FROM Commands")
    suspend fun getAllCommands(): List<Command>

    @Query("SELECT * FROM Variables")
    suspend fun getAllVariables(): List<Variable>

}