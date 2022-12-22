package org.maddiesoftware.komagareader.server_select_feature.presentation.state

data class ServerListItem(
    val serverId: Int,
    val serverName: String,
    val userName: String,
    val password: String,
    val url: String

)