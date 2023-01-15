package org.maddiesoftware.komagareader.komga_server.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.maddiesoftware.komagareader.komga_server.data.repository.ApiRepositoryImpl
import org.maddiesoftware.komagareader.komga_server.domain.repository.ApiRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ApiRepoModule {

    @Binds
    @Singleton
    abstract fun bindApiRepository(
        apiRepositoryImpl: ApiRepositoryImpl
    ): ApiRepository
}