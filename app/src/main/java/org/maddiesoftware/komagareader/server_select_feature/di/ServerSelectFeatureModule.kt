package org.maddiesoftware.komagareader.server_select_feature.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.maddiesoftware.komagareader.core.data.local.ServerDao
import org.maddiesoftware.komagareader.server_select_feature.data.repository.ServerRepositoryImpl
import org.maddiesoftware.komagareader.server_select_feature.domain.repository.ServerRepository
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
}