package org.maddiesoftware.komagareader.server_display.domain.use_case

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.maddiesoftware.komagareader.server_display.domain.model.Series
import org.maddiesoftware.komagareader.server_display.domain.repository.ApiRepository

class GetNewSeries (private val apiRepository: ApiRepository) {
    operator fun invoke(pageSize: Int, libraryId: String?): Flow<PagingData<Series>> {
        return apiRepository.getNewSeries(pageSize = pageSize, libraryId =libraryId)
    }

}
