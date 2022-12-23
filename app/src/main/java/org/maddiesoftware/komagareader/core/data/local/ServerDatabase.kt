package org.maddiesoftware.komagareader.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import org.maddiesoftware.komagareader.core.domain.model.Server

@Database(entities = [Server::class], version =1, exportSchema = false)
abstract class ServerDatabase: RoomDatabase() {
    abstract fun serverDao():ServerDao
}