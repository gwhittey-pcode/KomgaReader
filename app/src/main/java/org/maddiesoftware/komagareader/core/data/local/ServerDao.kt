package org.maddiesoftware.komagareader.core.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.maddiesoftware.komagareader.core.data.local.entities.ServerEntity
import org.maddiesoftware.komagareader.core.domain.model.Server

@Dao
interface ServerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertServer(serverEntity: ServerEntity)

    @Query("SELECT * FROM servers_table ORDER BY serverId")
    fun getAllServers(): List<ServerEntity>

    @Query("SELECT * FROM servers_table WHERE serverId = :id")
    suspend fun getServerById(id:String):ServerEntity
}