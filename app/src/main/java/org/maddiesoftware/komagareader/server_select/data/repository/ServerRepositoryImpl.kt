package org.maddiesoftware.komagareader.server_select.data.repository

import kotlinx.coroutines.flow.Flow
import org.maddiesoftware.komagareader.server_select.data.local.ServerDao
import org.maddiesoftware.komagareader.server_select.domain.model.Server
import org.maddiesoftware.komagareader.server_select.domain.repository.ServerRepository
import javax.inject.Inject

class ServerRepositoryImpl @Inject constructor(
    private val serverDao: ServerDao
):ServerRepository {

    override suspend fun insertServer(server: Server) {
        serverDao.insertServer(server)
    }

    override suspend fun getServerById(id: Int): Server {
        return serverDao.getServerById(id)
    }

    override suspend fun deleteServer(server: Server) {
        serverDao.deleteServer(server)
    }

    override fun getServers(): Flow<List<Server>> {
        return serverDao.getAllServers()
    }


}