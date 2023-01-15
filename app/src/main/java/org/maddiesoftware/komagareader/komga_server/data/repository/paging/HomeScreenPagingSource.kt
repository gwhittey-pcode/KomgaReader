package org.maddiesoftware.komagareader.komga_server.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import org.maddiesoftware.komagareader.komga_server.data.remote.api.KomgaServerApi
import org.maddiesoftware.komagareader.komga_server.domain.model.Book
import org.maddiesoftware.komagareader.komga_server.domain.model.Series

class KeepReadingSource(private val api: KomgaServerApi, private val libraryId: String?) :
    PagingSource<Int, Book>() {
    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        val currentPage = params.key ?: 0
        val response = api.getAllBooks(
            readStatus = listOf("IN_PROGRESS"),
            page = currentPage,
            sort = listOf("readProgress.lastModified,desc"),
            libraryId = libraryId
        )
        val endOfPaginationReached = response.content?.isEmpty()
        return try {
            LoadResult.Page(
                data = response.content?.map { it.toBook() } ?: emptyList(),
                prevKey = if (currentPage == 0) null else currentPage - 1,
                nextKey = if (endOfPaginationReached == true) null else currentPage + 1
            )
        } catch (exp: Exception) {
            LoadResult.Error(exp)
        }
    }
}

class OnDeckPagingSource(private val api: KomgaServerApi, private val libraryId: String?) :
    PagingSource<Int, Book>() {
    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        val currentPage = params.key ?: 0
        val response = api.getOnDeckBooks(
            page = currentPage,
            libraryId = libraryId
        )
        val endOfPaginationReached = response.content?.isEmpty()
        return try {
            LoadResult.Page(
                data = response.content?.map { it.toBook() } ?: emptyList(),
                prevKey = if (currentPage == 0) null else currentPage - 1,
                nextKey = if (endOfPaginationReached == true) null else currentPage + 1
            )
        } catch (exp: Exception) {
            LoadResult.Error(exp)
        }
    }
}//

class RecentlyAddedBooksPagingSource(private val api: KomgaServerApi, private val libraryId: String?) :
    PagingSource<Int, Book>() {
    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        return state.anchorPosition
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        val currentPage = params.key ?: 0
        val response = api.getAllBooks(
            sort = listOf("createdDate,desc"),
            page = currentPage,
            libraryId = libraryId
        )
        val endOfPaginationReached = response.content?.isEmpty()
        return try {
            LoadResult.Page(
                data = response.content?.map { it.toBook() } ?: emptyList(),
                prevKey = if (currentPage == 0) null else currentPage - 1,
                nextKey = if (endOfPaginationReached == true) null else currentPage + 1
            )
        } catch (exp: Exception) {
            LoadResult.Error(exp)
        }
    }
}

class NewSeriesPagingSource(private val api:KomgaServerApi, private val libraryId: String?):PagingSource<Int, Series>() {
    override fun getRefreshKey(state: PagingState<Int, Series>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Series> {
        val currentPage = params.key ?: 0
        val response  = api.getNew(page = currentPage, libraryId = this.libraryId)
        val endOfPaginationReached = response?.content?.isEmpty()
        return try {
            LoadResult.Page(
                data = response?.content?.map { it.toSeries() } ?: emptyList() ,
                prevKey = if(currentPage == 0) null else currentPage - 1,
                nextKey = if(endOfPaginationReached == true) null else currentPage + 1
            )
        } catch(exp: Exception){
            LoadResult.Error(exp)
        }

    }
}

class UpdatedSeriesPagingSource(private val api:KomgaServerApi, private val libraryId: String?):PagingSource<Int, Series>() {
    override fun getRefreshKey(state: PagingState<Int, Series>): Int? {
        return state.anchorPosition
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Series> {
        val currentPage = params.key ?: 0
        val response  = api.getUpdatedSeries(page = currentPage, libraryId = this.libraryId)
        val endOfPaginationReached = response?.content?.isEmpty()
        return try {
            LoadResult.Page(
                data = response?.content?.map { it.toSeries() } ?: emptyList() ,
                prevKey = if(currentPage == 0) null else currentPage - 1,
                nextKey = if(endOfPaginationReached == true) null else currentPage + 1
            )
        } catch(exp: Exception){
            LoadResult.Error(exp)
        }

    }
}