package org.maddiesoftware.komagareader.server_select.domain.use_case

data class ServerUseCases(
    val getServers:GetServers,
    val deleteServer:DeleteServer,
    val addServer:AddServer,
    val getServer:GetServer
)
