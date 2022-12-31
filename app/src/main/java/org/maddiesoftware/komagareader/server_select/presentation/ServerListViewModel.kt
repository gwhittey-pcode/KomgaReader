package org.maddiesoftware.komagareader.server_select.presentation


import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.maddiesoftware.komagareader.server_select.domain.model.Server
//import org.maddiesoftware.komagareader.server_select_feature.domain.repository.ServerRepository
import org.maddiesoftware.komagareader.server_select.domain.use_case.ServerUseCases
import org.maddiesoftware.komagareader.server_select.presentation.state.ServerDetailListItem
import org.maddiesoftware.komagareader.server_select.presentation.state.ServersState
import javax.inject.Inject

@HiltViewModel
class ServerListViewModel @Inject constructor(
//   private  val serverRepository: ServerRepository,
   private val serverUseCases: ServerUseCases

): ViewModel() {

    private val _state = mutableStateOf(ServersState())
    val state: State<ServersState> = _state

    private var getServerJobs: Job? = null
    private var recentlyDeletedServer: Server? = null

    var isServerDialogShown by mutableStateOf(false)

    var clickedServerItem by mutableStateOf<ServerDetailListItem?>(null)
        private set

    init {
        viewModelScope.launch {
            setupServerList()
//            serverRepository.insertServer(
//                Server(
//                    serverId = null,
//                    "Komga122",
//                    "gerard.whittey@gmail.com",
//                    "187222",
//                    "http://192.168.1.69:8080"
//                )
//            )
        }
    }

    fun onEvent(event: ServersEvent) {
        when (event){
            is ServersEvent.DeleteServer -> {
                viewModelScope.launch {
                    serverUseCases.deleteServer(event.server)
                    recentlyDeletedServer = event.server
                }
            }
            is ServersEvent.RestoreServer -> {
                viewModelScope.launch {
                    serverUseCases.addServer(recentlyDeletedServer ?: return@launch)
                }
            }
        }
    }
//    fun onServerClick(serverId: Int?){
//        initServerForDialog(serverId)
//        isServerDialogShown = true
//    }

//    private fun initServerForDialog(serverId: Int?){
//        clickedServerItem =
//            _servers.firstOrNull{ it.serverId == serverId }?.toServerDetailList()
//    }
    fun onDismissServerDialog(){
        isServerDialogShown = false
        clickedServerItem = null
    }
    private fun setupServerList(){
        getServerJobs?.cancel()
        getServerJobs =  serverUseCases.getServers().onEach{ servers ->
                Log.d("komga1","$servers")
                _state.value = state.value.copy(
                    servers = servers
                )

            }
            .launchIn(viewModelScope)


    }
}