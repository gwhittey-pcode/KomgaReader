package org.maddiesoftware.komagareader.server_select.presentation

import org.maddiesoftware.komagareader.server_select.domain.model.Server

sealed class ServersEvent {
    data class DeleteServer(val server: Server): ServersEvent()
    object RestoreServer: ServersEvent()
}
