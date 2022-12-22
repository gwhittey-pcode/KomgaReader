package org.maddiesoftware.komagareader.core.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "servers_table")
data class ServerEntity(
    @PrimaryKey(autoGenerate = true)
    val serverId: Int,
    val serverName: String,
    val userName: String,
    val password: String,
    val url: String

)
