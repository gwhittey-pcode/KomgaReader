package org.maddiesoftware.komagareader.core.data.mapper

import org.maddiesoftware.komagareader.core.data.local.entities.ServerEntity
import org.maddiesoftware.komagareader.core.domain.model.Server
import org.maddiesoftware.komagareader.server_select_feature.presentation.state.ServerDetailListItem
import org.maddiesoftware.komagareader.server_select_feature.presentation.state.ServerListItem

fun Server.toServerDetailList():ServerDetailListItem{
    return ServerDetailListItem(
        serverId = serverId,
        serverName = serverName,
        userName = userName,
        password = password,
        url = url
    )
}

fun Server.toServerListItem():ServerListItem{
    return ServerListItem(
        serverId = serverId,
        serverName = serverName,
        userName = userName,
        password = password,
        url = url
    )
}

fun Server.toServerEntity(): ServerEntity{
    return ServerEntity(
        serverId = serverId,
        serverName = serverName,
        userName = userName,
        password = password,
        url = url
    )
}

fun ServerEntity.toServer(): Server{
    return Server(
        serverId = serverId,
        serverName = serverName,
        userName = userName,
        password = password,
        url = url
    )
}