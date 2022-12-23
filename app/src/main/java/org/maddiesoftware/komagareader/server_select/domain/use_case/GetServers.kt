package org.maddiesoftware.komagareader.server_select.domain.use_case


import org.maddiesoftware.komagareader.core.domain.model.Server
import org.maddiesoftware.komagareader.server_select.domain.repository.ServerRepository
import kotlinx.coroutines.flow.Flow

class GetServers(
    private val repository: ServerRepository
) {
    operator fun invoke(): Flow<List<Server>> {
        return repository.getServers()
    }
}