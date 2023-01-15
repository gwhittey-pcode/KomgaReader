package org.maddiesoftware.komagareader.komga_server.domain.use_case

import android.util.Log
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.maddiesoftware.komagareader.komga_server.domain.model.Series
import org.maddiesoftware.komagareader.komga_server.domain.repository.ApiRepository

class GetAllSeries(private val apiRepository: ApiRepository) {
    operator fun invoke(pageSize: Int, libraryId: String?): Flow<PagingData<Series>> {
        Log.d("komaga12345","Invoke GetAllSeries")
        return apiRepository.getAllSeries(pageSize = pageSize, libraryId =libraryId)
    }
}