package org.maddiesoftware.komagareader.server_display.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import org.maddiesoftware.komagareader.server_display.data.remote.api.KomgaServerApi
import org.maddiesoftware.komagareader.server_display.data.remote.dto.PageSeriesDto
import org.maddiesoftware.komagareader.server_display.data.remote.dto.ResponseItems
import org.maddiesoftware.komagareader.server_display.data.remote.dto.SeriesDto
import org.maddiesoftware.komagareader.server_display.domain.model.PageSeries
import org.maddiesoftware.komagareader.server_display.domain.model.Series

class AllSeriesRemotePagingSource(private val api:KomgaServerApi, private val libraryId: String?):PagingSource<Int, Series>() {
    override fun getRefreshKey(state: PagingState<Int,  Series>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int,Series> {
        val currentPage = params.key ?: 0

        val response  = api.getAllSeries(
            page = currentPage,
            search = null,
            libraryId = this.libraryId,
            collectionId = null,
            status = null,
            readStatus = null,
            publisher = null,
            language = null,
            genre = null,
            tag = null,
            ageRating = null,
            releaseYear = null,
            unpaged = null,
            size = 20,
            sort = null,
            author = null
        )

        val endOfPaginationReached = response.content.isEmpty()

        return try {
            LoadResult.Page(
                data = response.content.map { it.toSeries() } ,
                prevKey = if(currentPage == 0) null else currentPage - 1,
                nextKey = if(endOfPaginationReached) null else currentPage + 1
            )
        } catch(exp: Exception){
            LoadResult.Error(exp)
        }

    }
}