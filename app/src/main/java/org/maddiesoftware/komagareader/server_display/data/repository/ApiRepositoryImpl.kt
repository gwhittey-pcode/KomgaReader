package org.maddiesoftware.komagareader.server_display.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.maddiesoftware.komagareader.core.util.Resource
import org.maddiesoftware.komagareader.server_display.data.remote.api.KomgaServerApi
import org.maddiesoftware.komagareader.server_display.data.repository.paging.AllSeriesRemotePagingSource

import org.maddiesoftware.komagareader.server_display.domain.model.Library
import org.maddiesoftware.komagareader.server_display.domain.model.PageBook
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
                getAllLibraries.map { it.toLibrary()} ?: emptyList()
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

    override suspend fun getOnDeckBooks(): Resource<PageBook?> {
        return try {
            val getOnDeckBooksResults = api.getOnDeckBooks()
            Resource.Success(getOnDeckBooksResults.toPageBook())
        } catch(e: IOException) {
            e.printStackTrace()
            Log.d("komga1","I Errro2")
            Resource.Error(
                message = "Couldn't load PageBook info"
            )
        } catch(e: HttpException) {
            e.printStackTrace()
            Log.d("komga1"," HttpException")
            Resource.Error(
                message = "Couldn't load PageBook info"
            )
        }
    }



    override suspend fun getKeepReading(): Resource<PageBook?> {
        return try {
            val getKeepReading = api.getAllBooks(
                mediaStatus = null,
                readStatus = listOf("IN_PROGRESS"),
                tag = null,
                unpaged = null,
                page = null,
                size = 100,
                sort = listOf("readProgress.lastModified,desc"),
                author = null
            )
            Resource.Success(getKeepReading.toPageBook())
        } catch(e: IOException) {
            e.printStackTrace()
            Log.d("komga1","I Errro2")
            Resource.Error(
                message = "Couldn't load PageBook info"
            )
        } catch(e: HttpException) {
            e.printStackTrace()
            Log.d("komga1"," HttpException")
            Resource.Error(
                message = "Couldn't load PageBook info"
            )
        }
    }

    override suspend fun getRecentlyAddedBooks(): Resource<PageBook?> {
        return try {
            val recentlyAddedBooksResult = api.getAllBooks(
                mediaStatus = null,
                readStatus = null,
                tag = null,
                unpaged = null,
                page = null,
                size = null,
                sort = listOf("createdDate,desc"),
                author = null
            )
            Resource.Success(recentlyAddedBooksResult.toPageBook())
        } catch(e: IOException) {
            e.printStackTrace()
            Log.d("komga1","I Errro2")
            Resource.Error(
                message = "Couldn't load PageBook info"
            )
        } catch(e: HttpException) {
            e.printStackTrace()
            Log.d("komga1"," HttpException")
            Resource.Error(
                message = "Couldn't load PageBook info"
            )
        }
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
            val getLatestResult = api.getLatest(
                libraryId = null,
                page = null,
                size = null,
                unpaged = null
            )
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

    override suspend fun getNewSeries(): Resource<PageSeries?> {
        Log.d("komga1","getLatest Repository")
        return try {
            val getNewSeries = api.getNew(
                libraryId = null,
                page = null,
                size = null,
                unpaged = null
            )
            Resource.Success(getNewSeries?.toPageSeries())
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


    override suspend fun getUpdatedSeries(): Resource<PageSeries?> {
        Log.d("komga1","getLatest Repository")
        return try {
            val getUpdatedSeries = api.getUpdatedSeries(
                libraryId = null,
                page = null,
                size = null,
                unpaged = null
            )
            Resource.Success(getUpdatedSeries?.toPageSeries())
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
}