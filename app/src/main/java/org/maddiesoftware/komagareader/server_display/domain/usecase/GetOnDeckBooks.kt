package org.maddiesoftware.komagareader.server_display.domain.usecase

import android.util.Log
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.maddiesoftware.komagareader.server_display.domain.model.Book
import org.maddiesoftware.komagareader.server_display.domain.repository.ApiRepository

class GetOnDeckBooks (private val apiRepository: ApiRepository) {
    operator fun invoke(pageSize: Int, libraryId: String?): Flow<PagingData<Book>> {
        Log.d("komga12345","Invoke GetSeriesById")
        return apiRepository.getOnDeckBooks(pageSize = pageSize,libraryId =libraryId)
    }

}
