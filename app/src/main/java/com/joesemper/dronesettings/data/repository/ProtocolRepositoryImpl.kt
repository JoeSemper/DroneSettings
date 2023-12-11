package com.joesemper.dronesettings.data.repository

import com.joesemper.dronesettings.data.datasource.room.prepopulated.dao.ProtocolDao
import com.joesemper.dronesettings.data.datasource.room.prepopulated.entity.Command
import com.joesemper.dronesettings.data.datasource.room.prepopulated.entity.Variable
import com.joesemper.dronesettings.domain.repository.ProtocolRepository

class ProtocolRepositoryImpl(private val dao: ProtocolDao): ProtocolRepository {

    override suspend fun getAllCommands(): List<Command> = dao.getAllCommands()

    override suspend fun getAllVariables(): List<Variable>  = dao.getAllVariables()

}