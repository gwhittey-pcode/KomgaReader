package org.maddiesoftware.komagareader.komga_server.domain.use_case.homescreen

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.maddiesoftware.komagareader.komga_server.domain.model.Series
import org.maddiesoftware.komagareader.komga_server.domain.repository.ApiRepository

class GetUpdatedSeries (private val apiRepository: ApiRepository) {
    operator fun invoke(pageSize: Int, libraryId: String?): Flow<PagingData<Series>> {
        return apiRepository.getUpdatedSeries(pageSize = pageSize, libraryId =libraryId)
    }
}