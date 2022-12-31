package org.maddiesoftware.komagareader.server_select.domain.use_case

import org.maddiesoftware.komagareader.server_select.domain.model.Server
import org.maddiesoftware.komagareader.server_select.domain.repository.ServerRepository

class GetServer(
    val repository: ServerRepository
) {
    suspend operator fun invoke(serverId:Int): Server?{
        return repository.getServerById(serverId)
    }
}
