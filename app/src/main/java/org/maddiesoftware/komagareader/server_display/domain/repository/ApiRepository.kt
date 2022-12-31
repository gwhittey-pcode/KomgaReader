package org.maddiesoftware.komagareader.server_display.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.maddiesoftware.komagareader.core.util.Resource
import org.maddiesoftware.komagareader.server_display.domain.model.Library
import org.maddiesoftware.komagareader.server_display.domain.model.PageBook
import org.maddiesoftware.komagareader.server_display.domain.model.PageSeries
import org.maddiesoftware.komagareader.server_display.domain.model.Series

interface ApiRepository {
    suspend fun getLatest(): Resource<PageSeries?>

    suspend fun getNewSeries(): Resource<PageSeries?>

    suspend fun getAllLibraries(): Resource<List<Library>>

    fun getAllSeries(pageSize: Int, libraryId: String?): Flow<PagingData<Series>>

    suspend fun getKeepReading(): Resource<PageBook?>

    suspend fun getOnDeckBooks(): Resource<PageBook?>

    suspend fun getRecentlyAddedBooks(): Resource<PageBook?>

    suspend fun getUpdatedSeries(): Resource<PageSeries?>


}