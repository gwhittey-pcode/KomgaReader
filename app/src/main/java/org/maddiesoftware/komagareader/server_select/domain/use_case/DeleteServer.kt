package org.maddiesoftware.komagareader.server_select.domain.use_case

import org.maddiesoftware.komagareader.server_select.domain.model.Server
import org.maddiesoftware.komagareader.server_select.domain.repository.ServerRepository

class DeleteServer(
    private val repository: ServerRepository
) {
    suspend operator fun invoke(server: Server){
        repository.deleteServer(server)
    }
}
