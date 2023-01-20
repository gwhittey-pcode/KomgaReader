package org.maddiesoftware.komagareader.komga_server.domain.use_case.collections

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.maddiesoftware.komagareader.komga_server.domain.model.Series
import org.maddiesoftware.komagareader.komga_server.domain.repository.ApiRepository

class GetSeriesFromCollection(private val apiRepository: ApiRepository) {

    operator fun invoke(
        pageSize: Int,
        libraryId: String?,
        collectionId: String
    ): Flow<PagingData<Series>> {

        return apiRepository.getSeriesFromCollection(
            pageSize = pageSize,
            libraryId = libraryId,
            collectionId = collectionId
        )
    }
}