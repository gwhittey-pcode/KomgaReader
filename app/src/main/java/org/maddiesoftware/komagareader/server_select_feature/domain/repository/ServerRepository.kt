package org.maddiesoftware.komagareader.server_select_feature.domain.repository

import org.maddiesoftware.komagareader.core.domain.model.Server

interface ServerRepository {
    suspend fun insertServer(server:Server)

    suspend fun getServers():List<Server>

}