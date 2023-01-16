package org.maddiesoftware.komagareader.komga_server.domain.use_case.series

import android.util.Log
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.maddiesoftware.komagareader.komga_server.domain.model.Book
import org.maddiesoftware.komagareader.komga_server.domain.repository.ApiRepository

class GetBooksFromSeries (private val apiRepository: ApiRepository) {
    operator fun invoke(pageSize: Int, seriesId: String?): Flow<PagingData<Book>> {
        Log.d("komaga12345","Invoke GetSeriesById")
        return apiRepository.getBooksFromSeries(pageSize = pageSize, seriesId = seriesId.toString())
    }
}