package org.maddiesoftware.komagareader.core.domain.model

data class Server(
    val serverId: Int,
    val serverName: String,
    val userName: String,
    val password: String,
    val url: String
)
