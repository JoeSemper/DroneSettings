package com.joesemper.dronesettings.domain.repository

import com.joesemper.dronesettings.data.datasource.room.prepopulated.entity.Command
import com.joesemper.dronesettings.data.datasource.room.prepopulated.entity.Variable

interface ProtocolRepository {
    suspend fun getAllCommands(): List<Command>
    suspend fun getAllVariables(): List<Variable>
}