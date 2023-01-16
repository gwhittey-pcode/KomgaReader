package org.maddiesoftware.komagareader.komga_server.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import org.maddiesoftware.komagareader.komga_server.data.remote.api.KomgaServerApi
import org.maddiesoftware.komagareader.komga_server.domain.model.ReadList

class AllReadListsRemotePagingSource(
    private val api: KomgaServerApi,
    private val libraryId: String?,
): PagingSource<Int, ReadList>() {
    override fun getRefreshKey(state: PagingState<Int, ReadList>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ReadList> {
        val currentPage = params.key ?: 0

        val response  = api.getAllReadLists(page = currentPage, libraryId = this.libraryId)

        val endOfPaginationReached = response.content.isEmpty()

        return try {
            LoadResult.Page(
                data = response.content.map { it.toReadList() } ,
                prevKey = if(currentPage == 0) null else currentPage - 1,
                nextKey = if(endOfPaginationReached) null else currentPage + 1
            )
        } catch(exp: Exception){
            LoadResult.Error(exp)
        }
    }
}