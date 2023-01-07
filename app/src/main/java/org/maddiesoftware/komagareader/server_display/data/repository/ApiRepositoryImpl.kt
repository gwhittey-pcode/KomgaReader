package org.maddiesoftware.komagareader.server_display.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.maddiesoftware.komagareader.core.util.Resource
import org.maddiesoftware.komagareader.server_display.data.remote.api.KomgaServerApi
import org.maddiesoftware.komagareader.server_display.data.repository.paging.*
import org.maddiesoftware.komagareader.server_display.domain.model.Book
import org.maddiesoftware.komagareader.server_display.domain.model.Library
import org.maddiesoftware.komagareader.server_display.domain.model.PageSeries
import org.maddiesoftware.komagareader.server_display.domain.model.Series
import org.maddiesoftware.komagareader.server_display.domain.repository.ApiRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiRepositoryImpl @Inject constructor(
    private val api: KomgaServerApi
): ApiRepository  {
    override suspend fun getAllLibraries(): Resource<List<Library>> {
        Log.d("komga1","getLatest Repository")
        return try {
            val getAllLibraries = api.getLibraries()
            Resource.Success(
                getAllLibraries.map { it.toLibrary()}
            )
        } catch(e: IOException) {
            e.printStackTrace()
            Log.d("komga1","I Errro2")
            Resource.Error(
                message = "Couldn't load PageSeries info"
            )
        } catch(e: HttpException) {
            e.printStackTrace()
            Log.d("komga1"," HttpException")
            Resource.Error(
                message = "Couldn't load PageSeries info"
            )
        }
    }

    override fun getOnDeckBooks(pageSize: Int, libraryId: String?): Flow<PagingData<Book>> {
        return Pager(
            config = PagingConfig(pageSize = pageSize),
            pagingSourceFactory = { OnDeckPagingSource(api = api, libraryId = libraryId) }
        ).flow

    }




    override  fun getKeepReading(pageSize: Int, libraryId: String?): Flow<PagingData<Book>> {
        return Pager(
            config = PagingConfig(pageSize = pageSize),
            pagingSourceFactory = { KeepReadingSource(api = api, libraryId = libraryId) }
        ).flow

    }

    override  fun getRecentlyAddedBooks(pageSize: Int, libraryId: String?): Flow<PagingData<Book>> {
        return Pager(
            config = PagingConfig(pageSize = pageSize),
            pagingSourceFactory = { RecentlyAddedBooksPagingSource(api = api, libraryId = libraryId) }
        ).flow

    }

    override fun getAllSeries(pageSize: Int, libraryId: String?): Flow<PagingData<Series>> {
        return Pager(
            config = PagingConfig(pageSize = pageSize),
            pagingSourceFactory = { AllSeriesRemotePagingSource(api = api, libraryId = libraryId) }
        ).flow
    }

    override suspend fun getLatest(): Resource<PageSeries?> {
        Log.d("komga1","getLatest Repository")
        return try {
            val getLatestResult = api.getLatest()
            Resource.Success(getLatestResult?.toPageSeries())
        } catch(e: IOException) {
            e.printStackTrace()
            Log.d("komga1","I Errro2")
            Resource.Error(
                message = "Couldn't load PageSeries info"
            )
        } catch(e: HttpException) {
            e.printStackTrace()
            Log.d("komga1"," HttpException")
            Resource.Error(
                message = "Couldn't load PageSeries info"
            )
        }
    }

    override fun getNewSeries(pageSize: Int, libraryId: String?): Flow<PagingData<Series>> {
        return Pager(
            config = PagingConfig(pageSize = pageSize),
            pagingSourceFactory = { NewSeriesPagingSource(api = api, libraryId = libraryId) }
        ).flow
    }


    override fun getUpdatedSeries(pageSize: Int, libraryId: String?): Flow<PagingData<Series>> {
        return Pager(
            config = PagingConfig(pageSize = pageSize),
            pagingSourceFactory = { UpdatedSeriesPagingSource(api = api, libraryId = libraryId) }
        ).flow
    }

    override fun getBooksFromSeries(pageSize: Int, seriesId: String): Flow<PagingData<Book>> {
        return Pager(
            config = PagingConfig(pageSize = pageSize),
            pagingSourceFactory = { SeriesByIdPagingSource(api = api, seriesId = seriesId) }
        ).flow
    }

    override suspend fun getSeriesById(seriesId: String): Resource<Series?> {
        return try {
            val getSeriesById = api.getSeriesById(seriesId=seriesId)
            Resource.Success(getSeriesById.toSeries())
        } catch(e: IOException) {
            e.printStackTrace()
            Log.d("komga1","I Errro2")
            Resource.Error(
                message = "Couldn't load Series info"
            )
        } catch(e: HttpException) {
            e.printStackTrace()
            Log.d("komga1"," HttpException")
            Resource.Error(
                message = "Couldn't load Series info"
            )
        }
    }
}