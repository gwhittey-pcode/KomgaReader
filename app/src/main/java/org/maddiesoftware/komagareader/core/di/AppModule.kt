package org.maddiesoftware.komagareader.core.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.maddiesoftware.komagareader.core.data.local.ServerDao
import org.maddiesoftware.komagareader.core.data.local.ServerDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideServerDatabase(@ApplicationContext context: Context):ServerDatabase{
        return Room.databaseBuilder(
            context,
            ServerDatabase::class.java,
            "server_db"
        ).build()
    }
    @Provides
    @Singleton
    fun provideServerDao(serverDatabase: ServerDatabase):ServerDao{
        return serverDatabase.serverDao()
    }
}