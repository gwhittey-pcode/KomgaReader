package org.maddiesoftware.komagareader.komga_server.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import org.maddiesoftware.komagareader.komga_server.data.remote.api.KomgaServerApi
import org.maddiesoftware.komagareader.komga_server.domain.model.CollectionX

class AllCollectionRemotePagingSource(
    private val api: KomgaServerApi,
    private val libraryId: String?,
): PagingSource<Int, CollectionX>() {
    override fun getRefreshKey(state: PagingState<Int, CollectionX>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: PagingSource.LoadParams<Int>): PagingSource.LoadResult<Int, CollectionX> {
        val currentPage = params.key ?: 0

        val response = api.getAllCollections(page = currentPage, libraryId = this.libraryId)

        val endOfPaginationReached = response.content.isEmpty()

        return try {
            PagingSource.LoadResult.Page(
                data = response.content.map { it.toCollection() },
                prevKey = if (currentPage == 0) null else currentPage - 1,
                nextKey = if (endOfPaginationReached) null else currentPage + 1
            )
        } catch (exp: Exception) {
            PagingSource.LoadResult.Error(exp)
        }
    }
}