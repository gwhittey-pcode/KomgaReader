package org.maddiesoftware.komagareader.core.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "servers_table",indices = [Index(value = ["serverName"], unique = true)])
data class Server(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "serverId") val serverId: Int? = null,
    @ColumnInfo(name = "serverName") val serverName: String,
    @ColumnInfo(name = "userName") val userName: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "url") val url: String
)

class InvalidServerException(message: String): Exception(message)