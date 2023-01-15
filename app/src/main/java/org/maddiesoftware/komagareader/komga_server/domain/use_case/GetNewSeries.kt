package org.maddiesoftware.komagareader.komga_server.domain.use_case

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.maddiesoftware.komagareader.komga_server.domain.model.Series
import org.maddiesoftware.komagareader.komga_server.domain.repository.ApiRepository

class GetNewSeries (private val apiRepository: ApiRepository) {
    operator fun invoke(pageSize: Int, libraryId: String?): Flow<PagingData<Series>> {
        return apiRepository.getNewSeries(pageSize = pageSize, libraryId =libraryId)
    }

}
