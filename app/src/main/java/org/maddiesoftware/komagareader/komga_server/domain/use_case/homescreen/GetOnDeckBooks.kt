package org.maddiesoftware.komagareader.komga_server.domain.use_case.homescreen

import android.util.Log
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.maddiesoftware.komagareader.komga_server.domain.model.Book
import org.maddiesoftware.komagareader.komga_server.domain.repository.ApiRepository

class GetOnDeckBooks (private val apiRepository: ApiRepository) {
    operator fun invoke(pageSize: Int, libraryId: String?): Flow<PagingData<Book>> {
        Log.d("komga12345","Invoke GetSeriesById")
        return apiRepository.getOnDeckBooks(pageSize = pageSize,libraryId =libraryId)
    }

}
