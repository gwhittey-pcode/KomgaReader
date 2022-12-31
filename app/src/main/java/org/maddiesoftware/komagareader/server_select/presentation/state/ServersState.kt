package org.maddiesoftware.komagareader.server_select.presentation.state

import org.maddiesoftware.komagareader.server_select.domain.model.Server

data class ServersState(
    val servers: List<Server> = emptyList()
)
