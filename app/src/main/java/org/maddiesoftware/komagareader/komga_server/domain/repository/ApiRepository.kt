package org.maddiesoftware.komagareader.komga_server.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.maddiesoftware.komagareader.core.util.Resource
import org.maddiesoftware.komagareader.komga_server.domain.model.*
import retrofit2.Response

interface ApiRepository {
    suspend fun getLatest(): Resource<PageSeries?>

    fun getNewSeries(pageSize: Int, libraryId: String?): Flow<PagingData<Series>>

    suspend fun getAllLibraries(): Resource<List<Library>>

    fun getAllSeries(pageSize: Int, libraryId: String?): Flow<PagingData<Series>>

    fun getKeepReading(pageSize: Int, libraryId: String?): Flow<PagingData<Book>>

    fun getOnDeckBooks(pageSize: Int, libraryId: String?):Flow<PagingData<Book>>

    fun getRecentlyAddedBooks(pageSize: Int, libraryId: String?):  Flow<PagingData<Book>>

    fun getUpdatedSeries(pageSize: Int, libraryId: String?):  Flow<PagingData<Series>>

    suspend fun getSeriesById(seriesId: String): Resource<Series?>

    fun getBooksFromSeries(pageSize: Int, seriesId: String): Flow<PagingData<Book>>

    suspend fun getBookById(bookId: String): Resource<Book>

    suspend fun getPages(bookId: String): Resource<List<Page>>

    suspend fun updateReadProgress(bookId: String,page: Int,completed:Boolean): Resource<Response<Unit>>

    fun getAllReadList(pageSize: Int, libraryId: String?): Flow<PagingData<ReadList>>

}