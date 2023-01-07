package org.maddiesoftware.komagareader.server_display.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.maddiesoftware.komagareader.server_display.domain.model.Series
import org.maddiesoftware.komagareader.server_display.domain.repository.ApiRepository

class GetUpdatedSeries (private val apiRepository: ApiRepository) {
    operator fun invoke(pageSize: Int, libraryId: String?): Flow<PagingData<Series>> {
        return apiRepository.getUpdatedSeries(pageSize = pageSize, libraryId =libraryId)
    }
}