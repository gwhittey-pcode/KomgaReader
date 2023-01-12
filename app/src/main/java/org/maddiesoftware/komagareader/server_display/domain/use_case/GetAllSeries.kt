package org.maddiesoftware.komagareader.server_display.domain.use_case

import android.util.Log
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.maddiesoftware.komagareader.server_display.domain.model.Series
import org.maddiesoftware.komagareader.server_display.domain.repository.ApiRepository

class GetAllSeries(private val apiRepository: ApiRepository) {
    operator fun invoke(pageSize: Int, libraryId: String?): Flow<PagingData<Series>> {
        Log.d("komaga12345","Invoke GetAllSeries")
        return apiRepository.getAllSeries(pageSize = pageSize, libraryId =libraryId)
    }
}