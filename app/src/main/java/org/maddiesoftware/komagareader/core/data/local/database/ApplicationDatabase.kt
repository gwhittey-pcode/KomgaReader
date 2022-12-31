package org.maddiesoftware.komagareader.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import org.maddiesoftware.komagareader.server_select.data.local.ServerDao
import org.maddiesoftware.komagareader.server_select.domain.model.Server

@Database(entities = [Server::class], version =1, exportSchema = false)
abstract class ApplicationDatabase: RoomDatabase() {
    abstract fun serverDao(): ServerDao
}