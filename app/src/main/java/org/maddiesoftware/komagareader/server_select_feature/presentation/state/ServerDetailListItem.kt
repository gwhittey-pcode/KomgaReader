package org.maddiesoftware.komagareader.server_select_feature.presentation.state

data class ServerDetailListItem(
    val serverId: Int,
    val serverName: String,
    val userName: String,
    val password: String,
    val url: String
) {
}