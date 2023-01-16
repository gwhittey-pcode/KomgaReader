package org.maddiesoftware.komagareader.core.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.maddiesoftware.komagareader.core.data.datastore.ServersDataStoreRepository
import org.maddiesoftware.komagareader.core.data.local.database.ApplicationDatabase
import org.maddiesoftware.komagareader.core.data.repository.ServersDataStoreRepositoryImpl
import org.maddiesoftware.komagareader.komga_server.data.remote.ApiBuilder
import org.maddiesoftware.komagareader.komga_server.data.remote.api.KomgaServerApi
import org.maddiesoftware.komagareader.komga_server.domain.books.BookReaderUseCases
import org.maddiesoftware.komagareader.komga_server.domain.books.UpdateReadProgress
import org.maddiesoftware.komagareader.komga_server.domain.repository.ApiRepository
import org.maddiesoftware.komagareader.komga_server.domain.use_case.*
import org.maddiesoftware.komagareader.komga_server.domain.use_case.homescreen.*
import org.maddiesoftware.komagareader.komga_server.domain.use_case.series.AllSeriesUseCases
import org.maddiesoftware.komagareader.komga_server.domain.use_case.series.GetAllSeries
import org.maddiesoftware.komagareader.komga_server.domain.use_case.series.GetBooksFromSeries
import org.maddiesoftware.komagareader.server_select.data.local.ServerDao
import org.maddiesoftware.komagareader.server_select.domain.use_case.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideServerDatabase(@ApplicationContext context: Context): ApplicationDatabase {
        return Room.databaseBuilder(
            context,
            ApplicationDatabase::class.java,
            "server_db"
        ).build()
    }
    @Provides
    @Singleton
    fun provideServerDao(applicationDatabase: ApplicationDatabase): ServerDao {
        return applicationDatabase.serverDao()
    }

    @Singleton
    @Provides
    fun provideServersDataStoreRepository(
        @ApplicationContext app: Context
    ): ServersDataStoreRepository = ServersDataStoreRepositoryImpl(app)



    @Singleton
    @Provides
    fun providesApi(apiBuilder: ApiBuilder): KomgaServerApi {
        return apiBuilder.builder(KomgaServerApi::class.java)
    }

    @Singleton
    @Provides
    fun providesAllSeriesUseCases(apiRepository: ApiRepository): AllSeriesUseCases {
        return AllSeriesUseCases(
            getAllSeries = GetAllSeries(apiRepository = apiRepository),
            getBooksFromSeries = GetBooksFromSeries(apiRepository=apiRepository),
        )
    }

    @Singleton
    @Provides
    fun providesHomeScreenUseCases(apiRepository: ApiRepository): HomeScreenUseCases {
        return HomeScreenUseCases(
            getKeepReading = GetKeepReading(apiRepository = apiRepository),
            getOnDeckBooks = GetOnDeckBooks(apiRepository = apiRepository),
            getRecentlyAddedBooks = GetRecentlyAddedBooks(apiRepository = apiRepository),
            getNewSeries = GetNewSeries(apiRepository = apiRepository),
            getUpdatedSeries = GetUpdatedSeries(apiRepository = apiRepository),
        )
    }

    @Singleton
    @Provides
    fun providesServerAddUseCase(): ServerAddUseCase {
        return ServerAddUseCase(
            validateServerName = ValidateServerName(),
            validateUserName = ValidateUserName(),
            validatePassword = ValidatePassword(),
            validateUrl = ValidateUrl(),
        )
    }

    @Singleton
    @Provides
    fun providesBookReaderUseCases(apiRepository: ApiRepository): BookReaderUseCases {
        return BookReaderUseCases(
            updateReadProgress = UpdateReadProgress(apiRepository = apiRepository)
        )

    }
}