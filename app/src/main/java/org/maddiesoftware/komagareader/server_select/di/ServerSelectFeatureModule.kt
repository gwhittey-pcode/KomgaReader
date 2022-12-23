package org.maddiesoftware.komagareader.server_select.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.maddiesoftware.komagareader.core.data.local.ServerDao
import org.maddiesoftware.komagareader.server_select.data.repository.ServerRepositoryImpl
import org.maddiesoftware.komagareader.server_select.domain.repository.ServerRepository
import org.maddiesoftware.komagareader.server_select.domain.use_case.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServerSelectFeatureModule {

    @Provides
    @Singleton
    fun provideServerRepository(
        serverDao: ServerDao
    ):ServerRepository{
        return ServerRepositoryImpl(serverDao)
    }

    @Provides
    @Singleton
    fun provideServerUseCases(repository: ServerRepository): ServerUseCases{
        return ServerUseCases(
            getServers = GetServers(repository),
            deleteServer = DeleteServer(repository),
            addServer = AddServer(repository),
            getServer = GetServer(repository)
        )
    }
}