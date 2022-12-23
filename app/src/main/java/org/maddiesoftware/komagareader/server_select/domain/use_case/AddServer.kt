package org.maddiesoftware.komagareader.server_select.domain.use_case

import org.maddiesoftware.komagareader.core.domain.model.InvalidServerException
import org.maddiesoftware.komagareader.core.domain.model.Server
import org.maddiesoftware.komagareader.server_select.domain.repository.ServerRepository

class AddServer(
    private val repository: ServerRepository
) {
    @Throws(InvalidServerException::class)
    suspend operator fun invoke(server: Server){
        if(server.serverName.isBlank()){
            throw InvalidServerException("The Server Name is empty ")
        }
        if(server.userName.isBlank()){
            throw InvalidServerException("The UserName is empty ")
        }
        if(server.password.isBlank()){
            throw InvalidServerException("The password is empty ")
        }
        if(server.url.isBlank()){
            throw InvalidServerException("The Server Url is empty ")
        }
        repository.insertServer(server)
    }

}
