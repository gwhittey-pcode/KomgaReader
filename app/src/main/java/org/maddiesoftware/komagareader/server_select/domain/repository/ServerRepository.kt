package org.maddiesoftware.komagareader.server_select.domain.repository

import kotlinx.coroutines.flow.Flow
import org.maddiesoftware.komagareader.core.domain.model.Server

interface ServerRepository {
    suspend fun insertServer(server:Server)

    fun getServers(): Flow<List<Server>>

    suspend fun getServerById(id: Int):Server?

    suspend fun deleteServer(server: Server)

}