package org.maddiesoftware.komagareader.komga_server.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import org.maddiesoftware.komagareader.komga_server.data.remote.api.KomgaServerApi
import org.maddiesoftware.komagareader.komga_server.domain.model.Book

class BookFromReadListRemotePagingSource(private val api: KomgaServerApi, private val readListId: String?):
    PagingSource<Int, Book>() {
    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        val currentPage = params.key ?: 0
        val response  = api.getBooksFromReadList(readListId = readListId.toString(), page = currentPage,)

        val endOfPaginationReached = response.content.isEmpty()

        return try {
            LoadResult.Page(
                data = response.content.map { it.toBook() } ,
                prevKey = if(currentPage == 0) null else currentPage - 1,
                nextKey = if(endOfPaginationReached) null else currentPage + 1
            )
        } catch(exp: Exception){
            LoadResult.Error(exp)
        }
    }
}