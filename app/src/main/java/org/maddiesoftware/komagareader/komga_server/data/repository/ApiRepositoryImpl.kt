package org.maddiesoftware.komagareader.komga_server.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.maddiesoftware.komagareader.core.util.Resource
import org.maddiesoftware.komagareader.komga_server.data.remote.api.KomgaServerApi
import org.maddiesoftware.komagareader.komga_server.data.repository.paging.*
import org.maddiesoftware.komagareader.komga_server.domain.model.*
import org.maddiesoftware.komagareader.komga_server.domain.repository.ApiRepository
import retrofit2.HttpException
import retrofit2.Response
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

    override suspend fun getBookById(bookId: String): Resource<Book> {
        return try {
            val getBookById = api.getBookById(bookId=bookId)
            Resource.Success(getBookById.toBook())
        } catch(e: IOException) {
            e.printStackTrace()
            Log.d("komga1","I Errro2")
            Resource.Error(
                message = "Couldn't load Book info"
            )
        } catch(e: HttpException) {
            e.printStackTrace()
            Log.d("komga1"," HttpException")
            Resource.Error(
                message = "Couldn't load Book info"
            )
        }
    }

    override suspend fun getPages(bookId: String): Resource<List<Page>> {
        return try {
            val getPages = api.getPages(bookId=bookId)
            Resource.Success(getPages.map { it.toPage() })
        } catch(e: IOException) {
            e.printStackTrace()
            Log.d("komga1","I Errro2")
            Resource.Error(
                message = "Couldn't load Page info"
            )
        } catch(e: HttpException) {
            e.printStackTrace()
            Log.d("komga1"," HttpException")
            Resource.Error(
                message = "Couldn't load Page info"
            )
        }
    }

    override suspend fun updateReadProgress(
        bookId: String,
        page: Int,
        completed: Boolean
    ) : Resource<Response<Unit>> {
        val newReadProgress = NewReadProgress(
            page=page,
            completed=completed
        )
        return try {
            val updateReadProgress = api.updateReadProgress(bookId=bookId,newReadProgress )
            Resource.Success(updateReadProgress)
        } catch(e: IOException) {
            e.printStackTrace()
            Log.d("komga1","I Errro2")
            Resource.Error(
                message = "IOException updateReadProgress"
            )
        } catch(e: HttpException) {
            e.printStackTrace()
            Log.d("komga1"," HttpException")
            Resource.Error(
                message = "HttpException updateReadProgress"
            )
        }


//        return try {
//            val updateReadProgress = api.updateReadProgress(
//                bookId = bookId,
//                updatedProgress = UpdateReadProgress.Companion.NewReadProgress
//            )


    }
}