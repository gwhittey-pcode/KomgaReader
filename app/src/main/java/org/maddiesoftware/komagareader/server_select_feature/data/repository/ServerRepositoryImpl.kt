package org.maddiesoftware.komagareader.server_select_feature.data.repository

import org.maddiesoftware.komagareader.core.data.local.ServerDao
import org.maddiesoftware.komagareader.core.data.mapper.toServer
import org.maddiesoftware.komagareader.core.data.mapper.toServerEntity
import org.maddiesoftware.komagareader.core.domain.model.Server
import org.maddiesoftware.komagareader.server_select_feature.domain.repository.ServerRepository
import javax.inject.Inject

class ServerRepositoryImpl @Inject constructor(
    private val serverDao: ServerDao
):ServerRepository {
    override suspend fun insertServer(server: Server) {
        serverDao.insertServer(server.toServerEntity())
    }

    override suspend fun getServers(): List<Server> {
        return serverDao.getAllServers().map {
            it.toServer()
        }
    }

}