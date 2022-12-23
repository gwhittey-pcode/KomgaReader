package org.maddiesoftware.komagareader.server_home

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.maddiesoftware.komagareader.server_select.domain.use_case.ServerUseCases
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class ServerHomeViewModel @Inject constructor(
    private val serverUseCases: ServerUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    init {
        savedStateHandle.get<Int>("serverId")?.let { serverId ->
            if(serverId != -1){
                viewModelScope.launch {
                    var server = serverUseCases.getServer(serverId)
                    if (server != null) {
                        Log.d("Komag12345",server.serverName)
                    }
                }
            }

        }
    }

}