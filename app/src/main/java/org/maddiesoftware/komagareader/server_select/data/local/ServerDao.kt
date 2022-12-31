package org.maddiesoftware.komagareader.server_select.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.maddiesoftware.komagareader.server_select.domain.model.Server

@Dao
interface ServerDao {

    @Query("SELECT * FROM servers_table")
    fun getAllServers(): Flow<List<Server>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertServer(serverEntity: Server)

    @Query("SELECT * FROM servers_table WHERE serverId = :id")
    suspend fun getServerById(id: Int): Server

    @Delete
    suspend fun deleteServer(serverEntity: Server)

}