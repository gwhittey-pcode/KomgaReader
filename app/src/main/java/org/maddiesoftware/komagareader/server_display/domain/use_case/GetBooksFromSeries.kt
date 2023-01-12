package org.maddiesoftware.komagareader.server_display.domain.use_case

import android.util.Log
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.maddiesoftware.komagareader.server_display.domain.model.Book
import org.maddiesoftware.komagareader.server_display.domain.repository.ApiRepository

class GetBooksFromSeries (private val apiRepository: ApiRepository) {
    operator fun invoke(pageSize: Int, seriesId: String?): Flow<PagingData<Book>> {
        Log.d("komaga12345","Invoke GetSeriesById")
        return apiRepository.getBooksFromSeries(pageSize = pageSize, seriesId = seriesId.toString())
    }
}