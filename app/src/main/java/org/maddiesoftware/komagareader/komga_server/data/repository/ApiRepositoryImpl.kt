package org.maddiesoftware.komagareader.komga_server.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.maddiesoftware.komagareader.core.util.Resource
import org.maddiesoftware.komagareader.komga_server.data.remote.api.KomgaServerApi
import org.maddiesoftware.komagareader.komga_server.data.repository.paging.AllCollectionRemotePagingSource
import org.maddiesoftware.komagareader.komga_server.data.repository.paging.AllReadListsRemotePagingSource
import org.maddiesoftware.komagareader.komga_server.data.repository.paging.AllSeriesRemotePagingSource
import org.maddiesoftware.komagareader.komga_server.data.repository.paging.BookFromReadListRemotePagingSource
import org.maddiesoftware.komagareader.komga_server.data.repository.paging.BookFromSeriesRemotePagingSource
import org.maddiesoftware.komagareader.komga_server.data.repository.paging.KeepReadingSource
import org.maddiesoftware.komagareader.komga_server.data.repository.paging.NewSeriesPagingSource
import org.maddiesoftware.komagareader.komga_server.data.repository.paging.OnDeckPagingSource
import org.maddiesoftware.komagareader.komga_server.data.repository.paging.RecentlyAddedBooksPagingSource
import org.maddiesoftware.komagareader.komga_server.data.repository.paging.SeriesFromCollectionRemotePagingSource
import org.maddiesoftware.komagareader.komga_server.data.repository.paging.UpdatedSeriesPagingSource
import org.maddiesoftware.komagareader.komga_server.domain.model.Book
import org.maddiesoftware.komagareader.komga_server.domain.model.CollectionX
import org.maddiesoftware.komagareader.komga_server.domain.model.Library
import org.maddiesoftware.komagareader.komga_server.domain.model.NewReadProgress
import org.maddiesoftware.komagareader.komga_server.domain.model.Page
import org.maddiesoftware.komagareader.komga_server.domain.model.PageSeries
import org.maddiesoftware.komagareader.komga_server.domain.model.ReadList
import org.maddiesoftware.komagareader.komga_server.domain.model.Series
import org.maddiesoftware.komagareader.komga_server.domain.repository.ApiRepository
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiRepositoryImpl @Inject constructor(
    private val api: KomgaServerApi
) : ApiRepository {
    override suspend fun getAllLibraries(): Resource<List<Library>> {
        Log.d("komga1", "getLatest Repository")
        return try {
            val getAllLibraries = api.getLibraries()
            Resource.Success(
                getAllLibraries.map { it.toLibrary() }
            )
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("komga1", "I Errro2")
            Resource.Error(
                message = "Couldn't load PageSeries info"
            )
        } catch (e: HttpException) {
            e.printStackTrace()
            Log.d("komga HTTP Error", " HttpException")
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


    override fun getKeepReading(pageSize: Int, libraryId: String?): Flow<PagingData<Book>> {
        return Pager(
            config = PagingConfig(pageSize = pageSize),
            pagingSourceFactory = { KeepReadingSource(api = api, libraryId = libraryId) }
        ).flow

    }

    override fun getRecentlyAddedBooks(pageSize: Int, libraryId: String?): Flow<PagingData<Book>> {
        return Pager(
            config = PagingConfig(pageSize = pageSize),
            pagingSourceFactory = {
                RecentlyAddedBooksPagingSource(
                    api = api,
                    libraryId = libraryId
                )
            }
        ).flow

    }

    override fun getAllSeries(pageSize: Int, libraryId: String?): Flow<PagingData<Series>> {
        return Pager(
            config = PagingConfig(pageSize = pageSize),
            pagingSourceFactory = {
                AllSeriesRemotePagingSource(
                    api = api,
                    libraryId = libraryId,
                    sort = "booksCount,desc"
                )
            }
        ).flow
    }

    override suspend fun getLatest(): Resource<PageSeries?> {
        Log.d("komga1", "getLatest Repository")
        return try {
            val getLatestResult = api.getLatest()
            Resource.Success(getLatestResult?.toPageSeries())
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("komga1", "I Errro2")
            Resource.Error(
                message = "Couldn't load PageSeries info"
            )
        } catch (e: HttpException) {
            e.printStackTrace()
            Log.d("komga1", " HttpException")
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


    override suspend fun getSeriesById(seriesId: String): Resource<Series?> {
        return try {
            val getSeriesById = api.getSeriesById(seriesId = seriesId)
            Resource.Success(getSeriesById.toSeries())
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("komga1", "I Errro2")
            Resource.Error(
                message = "Couldn't load Series info"
            )
        } catch (e: HttpException) {
            e.printStackTrace()
            Log.d("komga1", " HttpException")
            Resource.Error(
                message = "Couldn't load Series info"
            )
        }
    }

    override fun getBooksFromSeries(pageSize: Int, seriesId: String): Flow<PagingData<Book>> {
        return Pager(
            config = PagingConfig(pageSize = pageSize),
            pagingSourceFactory = {
                BookFromSeriesRemotePagingSource(
                    api = api,
                    seriesId = seriesId,
                    pageSize=pageSize
                )
            }
        ).flow
    }

    override suspend fun getBookById(bookId: String): Resource<Book> {
        return try {
            val getBookById = api.getBookById(bookId = bookId)
            Resource.Success(getBookById.toBook())
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("komga1", "I Errro2")
            Resource.Error(
                message = "Couldn't load Book info"
            )
        } catch (e: HttpException) {
            e.printStackTrace()
            Log.d("komga1", " HttpException")
            Resource.Error(
                message = "Couldn't load Book info"
            )
        }
    }

    override suspend fun getPages(bookId: String): Resource<List<Page>> {
        return try {
            val getPages = api.getPages(bookId = bookId)
            Resource.Success(getPages.map { it.toPage() })
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("komga1", "I Errro2")
            Resource.Error(
                message = "Couldn't load Page info"
            )
        } catch (e: HttpException) {
            e.printStackTrace()
            Log.d("komga1", " HttpException")
            Resource.Error(
                message = "Couldn't load Page info"
            )
        }
    }

    override suspend fun updateReadProgress(
        bookId: String,
        page: Int,
        completed: Boolean
    ): Resource<Response<Unit>> {
        val newReadProgress = NewReadProgress(
            page = page,
            completed = completed
        )
        return try {
            val updateReadProgress = api.updateReadProgress(bookId = bookId, newReadProgress)
            Resource.Success(updateReadProgress)
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("komga1", "I Errro2")
            Resource.Error(
                message = "IOException updateReadProgress"
            )
        } catch (e: HttpException) {
            e.printStackTrace()
            Log.d("komga1", " HttpException")
            Resource.Error(
                message = "HttpException updateReadProgress"
            )
        }
    }

    override fun getAllReadList(pageSize: Int, libraryId: String?): Flow<PagingData<ReadList>> {
        return Pager(
            config = PagingConfig(pageSize = pageSize),
            pagingSourceFactory = {
                AllReadListsRemotePagingSource(
                    api = api,
                    libraryId = libraryId
                )
            }
        ).flow
    }

    override suspend fun getReadListById(readListId: String): Resource<ReadList> {
        return try {
            val getReadListById = api.getReadListById(readListId = readListId)
            Resource.Success(getReadListById.toReadList())
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("komga1", "I Errro2")
            Resource.Error(
                message = "Couldn't load Read List info"
            )
        } catch (e: HttpException) {
            e.printStackTrace()
            Log.d("komga1", " HttpException")
            Resource.Error(
                message = "Couldn't Read List Series info"
            )
        }
    }

    override fun getBooksFromReadList(pageSize: Int, readListId: String): Flow<PagingData<Book>> {
        return Pager(
            config = PagingConfig(pageSize = pageSize),
            pagingSourceFactory = {
                BookFromReadListRemotePagingSource(
                    api = api,
                    readListId = readListId,
                    pageSize=pageSize
                )
            }
        ).flow
    }

    override fun getAllCollections(
        pageSize: Int,
        libraryId: String?
    ): Flow<PagingData<CollectionX>> {
        return Pager(
            config = PagingConfig(pageSize = pageSize),
            pagingSourceFactory = {
                AllCollectionRemotePagingSource(
                    api = api,
                    libraryId = libraryId
                )
            }
        ).flow
    }

    override suspend fun getCollectionById(collectionId: String): Resource<CollectionX> {
        return try {
            val getCollectionById = api.getCollectionById(collectionId = collectionId)
            Resource.Success(getCollectionById.toCollection())
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("komga1", "I Errro2")
            Resource.Error(
                message = "Couldn't load Read List info"
            )
        } catch (e: HttpException) {
            e.printStackTrace()
            Log.d("komga1", " HttpException")
            Resource.Error(
                message = "Couldn't Read List Series info"
            )
        }
    }

    override fun getSeriesFromCollection(
        pageSize: Int,
        collectionId: String,
        libraryId: String?
    ): Flow<PagingData<Series>> {

        return Pager(
            config = PagingConfig(pageSize = pageSize),
            pagingSourceFactory = {
                SeriesFromCollectionRemotePagingSource(
                    api = api,
                    libraryId = libraryId,
                    collectionId = collectionId
                )
            }
        ).flow
    }

    override suspend fun getNextBookInReadList(bookId: String, readListId: String): Resource<Book> {
        return try {
            val getNextBookInReadList = api.getNextBookInReadList(bookId = bookId, readListId = readListId)
            Resource.Success(getNextBookInReadList.toBook())
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("komga1", "I Errro2")
            Resource.Error(
                message = "Couldn't load Read List info"
            )
        } catch (e: HttpException) {
            e.printStackTrace()
            Log.d("komga1", " HttpException")
            Resource.Error(
                message = "Couldn't Read List Series info"
            )
        }
    }

    override suspend fun getPreviousBookInReadList(
        bookId: String,
        readListId: String
    ): Resource<Book> {
        return try {
            val getPreviousBookInReadList = api.getPreviousBookInReadList(bookId = bookId, readListId = readListId)
            Resource.Success(getPreviousBookInReadList.toBook())
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("komga1", "I Errro2")
            Resource.Error(
                message = "Couldn't load getPreviousBookInReadList  info"
            )
        } catch (e: HttpException) {
            e.printStackTrace()
            Log.d("BookInfo", " ${e.message}")
            if(e.message == "HTTP 404") {
                Resource.Error(message = "404 Error")
            }else{
                Resource.Error(
                    message = "Couldn't load getPreviousBookInReadList  info"
                )
            }

        }
    }

    override suspend fun getNextBookInSeries(bookId: String): Resource<Book> {
        return try {
            val getNextBookInSeries = api.getNextBookInSeries(bookId = bookId)
            Resource.Success(getNextBookInSeries.toBook())
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("komga1", "I Errro2")
            Resource.Error(
                message = "Couldn't load getPreviousBookInReadList  info"
            )
        } catch (e: HttpException) {
            e.printStackTrace()
            Log.d("BookInfo", " ${e.message}")
            if(e.message == "HTTP 404") {
                Resource.Error(message = "404 Error")
            }else{
                Resource.Error(
                    message = "Couldn't load getPreviousBookInReadList  info"
                )
            }

        }
    }

    override suspend fun getPreviousBookInSeries(bookId: String): Resource<Book> {
        return try {
            val getPreviousBookInSeries = api.getPreviousBookInSeries(bookId = bookId)
            Resource.Success(getPreviousBookInSeries.toBook())
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("komga1", "I Errro2")
            Resource.Error(
                message = "Couldn't load getPreviousBookInReadList  info"
            )
        } catch (e: HttpException) {
            e.printStackTrace()
            Log.d("BookInfo", " ${e.message}")
            if(e.message == "HTTP 404") {
                Resource.Error(message = "404 Error")
            }else{
                Resource.Error(
                    message = "Couldn't load getPreviousBookInReadList  info"
                )
            }

        }
    }
}
