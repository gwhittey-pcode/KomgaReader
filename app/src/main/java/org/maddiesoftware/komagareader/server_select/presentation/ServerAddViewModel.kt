package org.maddiesoftware.komagareader.server_select.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.maddiesoftware.komagareader.server_select.domain.model.Server
import org.maddiesoftware.komagareader.server_select.domain.repository.ServerRepository
import javax.inject.Inject

@HiltViewModel
class ServerAddViewModel @Inject constructor(
    private  val serverRepository: ServerRepository
):ViewModel() {
    init {
        viewModelScope.launch {}
    }
      fun onAdd(
        serverId: Int? = null,
        serverName: String,
        username:String,
        password: String,
        url:String
    ){
          viewModelScope.launch(Dispatchers.IO) {
              serverRepository.insertServer(
                  Server(
                      serverId = serverId,
                      serverName = serverName,
                      userName = username,
                      password = password,
                      url = url

                  )
              )
          }
    }
}