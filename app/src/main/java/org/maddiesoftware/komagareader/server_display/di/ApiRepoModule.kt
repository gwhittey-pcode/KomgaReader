package org.maddiesoftware.komagareader.server_display.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.maddiesoftware.komagareader.server_display.data.repository.ApiRepositoryImpl
import org.maddiesoftware.komagareader.server_display.domain.repository.ApiRepository
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