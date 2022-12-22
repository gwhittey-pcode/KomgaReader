package org.maddiesoftware.komagareader.server_select_feature.presentation


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.maddiesoftware.komagareader.core.domain.model.Server
import org.maddiesoftware.komagareader.core.data.mapper.toServerDetailList
import org.maddiesoftware.komagareader.core.data.mapper.toServerListItem
import org.maddiesoftware.komagareader.server_select_feature.domain.repository.ServerRepository
import org.maddiesoftware.komagareader.server_select_feature.presentation.state.ServerDetailListItem
import org.maddiesoftware.komagareader.server_select_feature.presentation.state.ServerListItem
import javax.inject.Inject

@HiltViewModel
class ServerListViewModel @Inject constructor(
   private  val serverRepository: ServerRepository
): ViewModel() {

    private lateinit var servers:List<Server>

    var serverList by mutableStateOf<List<ServerListItem>>(emptyList())
        private set

    var isServerDialogShown by mutableStateOf(false)

    var clickedServerItem by mutableStateOf<ServerDetailListItem?>(null)
        private set

    init {
        viewModelScope.launch {
            servers = serverRepository.getServers()
            setupServerList()
        }
    }

    fun onServerClick(serverId:Int){
        initServerForDialog(serverId)
        isServerDialogShown = true
    }

    private fun initServerForDialog(serverId:Int){
        clickedServerItem =
            servers.firstOrNull{ it.serverId == serverId }?.toServerDetailList()
    }
    fun onDismissServerDialog(){
        isServerDialogShown = false
        clickedServerItem = null
    }
    private fun setupServerList(){
        serverList = servers.map { server ->
            server.toServerListItem()
        }
    }
}